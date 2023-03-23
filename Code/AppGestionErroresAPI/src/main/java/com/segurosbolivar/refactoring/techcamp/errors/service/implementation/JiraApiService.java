package com.segurosbolivar.refactoring.techcamp.errors.service.implementation;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.segurosbolivar.refactoring.techcamp.errors.model.AplicacionError;
import com.segurosbolivar.refactoring.techcamp.errors.repository.AccionUsuarioRepository;
import com.segurosbolivar.refactoring.techcamp.errors.repository.AplicacionErrorRepository;
import com.segurosbolivar.refactoring.techcamp.errors.repository.NivelErrorRepository;
import com.segurosbolivar.refactoring.techcamp.errors.repository.OrigenErrorRepository;
import com.segurosbolivar.refactoring.techcamp.errors.repository.TipoAccionRepository;
import com.segurosbolivar.refactoring.techcamp.errors.repository.TrazabilidadCodigoRepository;
import com.segurosbolivar.refactoring.techcamp.errors.request.ResponseJira;
import com.segurosbolivar.refactoring.techcamp.errors.service.interfaces.EmailSenderServiceI;
import com.segurosbolivar.refactoring.techcamp.errors.service.interfaces.JiraApiServicel;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class JiraApiService implements JiraApiServicel {

    @Value("${jira.url}")
    private String JIRA_BASE_URL;


    @Value("${jira.username}")
    private String email;

    @Value("${jira.token}")
    private String apiToken;
    
	@Autowired
	private AplicacionErrorRepository aplicacionErrorRespository;
    
	@Autowired
	private EmailSenderServiceI emailSenderService;
	
	public JiraApiService(EmailSenderServiceI ess,AplicacionErrorRepository aer) {
		this.aplicacionErrorRespository = aer;
		this.emailSenderService=ess;
	}

    public void getIssue(String issueKey, String email, String apiToken) throws Exception {
        String url = JIRA_BASE_URL + "/issue/" + issueKey;

        HttpResponse<JsonNode> response = Unirest.get(url)
                .basicAuth(email, apiToken)
                .header("Accept", "application/json")
                .asJson();

        System.out.println(response.getBody());
    }

    public ResponseJira createIssue(String summary, String description, String projectName, String parent,String idError) throws Exception {
        String url = JIRA_BASE_URL + "/issue/";

        // Define el cuerpo de la petición
        JSONObject requestBody = new JSONObject();
        JSONObject fields = new JSONObject();
        JSONObject adfContent = new JSONObject();
        adfContent.put("type", "paragraph");

        JSONArray adfText = new JSONArray();
        JSONObject adfTextObj = new JSONObject();
        adfTextObj.put("type", "text");
        adfTextObj.put("text", description);
        adfText.put(adfTextObj);

        adfContent.put("content", adfText);

        JSONArray adfContentArr = new JSONArray();
        adfContentArr.put(adfContent);

        JSONObject bodyObj = new JSONObject();
        bodyObj.put("version", 1);
        bodyObj.put("type", "doc");
        bodyObj.put("content", adfContentArr);
        fields.put("project", new JSONObject().put("key", projectName));
        fields.put("summary", summary);
        fields.put("issuetype", new JSONObject().put("id", "10005"));
        fields.put("parent", new JSONObject().put("key", parent));
        fields.put("description", bodyObj);
        requestBody.put("fields", fields);


        // Envía la petición HTTP POST a la API de Jira
        HttpResponse<JsonNode> response = Unirest.post(url)
                .basicAuth(email, apiToken)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(requestBody.toString())
                .asJson();

        String respuesta=response.getBody().getObject().get("key").toString();
      if(response.getStatus()==201){
          ResponseJira respuestaAEnviar=new ResponseJira();
          respuestaAEnviar.setKey(respuesta);
          
			Optional<AplicacionError> ae=aplicacionErrorRespository.findById(idError);
			if(!ae.isEmpty()) {
				if(ae.get().getCorreoUsuario()!=null) {
					emailSenderService.sendEmail(ae.get().getCorreoUsuario(), ae.get().getIdAplicacionError());
				}
			}
          return  respuestaAEnviar;
      }else{
          throw new Error( "No se ha logrado registrar en el jira");
      }
    }
}
