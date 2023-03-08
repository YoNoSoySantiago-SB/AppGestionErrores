package com.segurosbolivar.refactoring.techcamp.errors.service.implementation;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.segurosbolivar.refactoring.techcamp.errors.service.interfaces.JiraApiServicel;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JiraApiService implements JiraApiServicel {
    private final String JIRA_BASE_URL = "https://marloncamp.atlassian.net/rest/api/3";

    public void getIssue(String issueKey, String email, String apiToken) throws Exception {
        String url = JIRA_BASE_URL + "/issue/" + issueKey;

        HttpResponse<JsonNode> response = Unirest.get(url)
                .basicAuth(email, apiToken)
                .header("Accept", "application/json")
                .asJson();

        System.out.println(response.getBody());
    }

    public String createIssue(String summary, String description, String projectName) throws Exception {
        String url = JIRA_BASE_URL + "/issue/";
        String email = "marlon.anacona@correounivalle.edu.co";
        String apiToken = "ATATT3xFfGF0M7Ecx5i7EYnN1f23LQCGgmNJn9NuGKgeg45mMTy6_tugSLJUzFkgsGMMvuoR5mx081s2uaVJPwXBVA_1LikB165iHln_f4W5i4YfSkdpwaQHMBmAe6SuuILZVXzAkChBdzfI9aOgMIhJybILVi2bFunJ6mSzcarAATG21amxXxI=BB072F54";

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
        fields.put("parent", new JSONObject().put("key", "TEC-10"));
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
          return  "Acción completada, Se ha registrado en Jira con codigo: "+respuesta;
      }else{
          return  "No se ha logrado registrar en el jira";
      }
    }
}