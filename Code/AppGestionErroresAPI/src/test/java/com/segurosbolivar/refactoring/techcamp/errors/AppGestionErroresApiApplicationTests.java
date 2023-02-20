package com.segurosbolivar.refactoring.techcamp.errors;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.segurosbolivar.refactoring.techcamp.errors.customexceptions.BadRequestDataException;
import com.segurosbolivar.refactoring.techcamp.errors.model.AccionUsuario;
import com.segurosbolivar.refactoring.techcamp.errors.model.AplicacionError;
import com.segurosbolivar.refactoring.techcamp.errors.model.NivelError;
import com.segurosbolivar.refactoring.techcamp.errors.model.OrigenError;
import com.segurosbolivar.refactoring.techcamp.errors.model.TipoAccion;
import com.segurosbolivar.refactoring.techcamp.errors.model.TrazabilidadCodigo;
import com.segurosbolivar.refactoring.techcamp.errors.repository.AccionUsuarioRepository;
import com.segurosbolivar.refactoring.techcamp.errors.repository.AplicacionErrorRepository;
import com.segurosbolivar.refactoring.techcamp.errors.repository.NivelErrorRepository;
import com.segurosbolivar.refactoring.techcamp.errors.repository.OrigenErrorRepository;
import com.segurosbolivar.refactoring.techcamp.errors.repository.TipoAccionRepository;
import com.segurosbolivar.refactoring.techcamp.errors.repository.TrazabilidadCodigoRepository;
import com.segurosbolivar.refactoring.techcamp.errors.service.implementation.AplicacionErrorServiceImp;
import com.segurosbolivar.refactoring.techcamp.errors.service.interfaces.AplicacionErrorServiceI;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class AppGestionErroresApiApplicationTests {
	
	@Mock
	private AplicacionErrorRepository aplicacionErrorRespository;
	@Mock
	private OrigenErrorRepository origenErrorRepository;
	@Mock
	private TrazabilidadCodigoRepository trazabilidadCodigoRepository;
	@Mock
	private NivelErrorRepository nivelErrorRepository;
	@Mock
	private TipoAccionRepository tipoAccionRepository;
	@Mock
	private AccionUsuarioRepository accionUsuarioRepository;
	
	
	private AplicacionErrorServiceI aplicacionErrorService;
	
	public AppGestionErroresApiApplicationTests() {
		aplicacionErrorService = new AplicacionErrorServiceImp(aplicacionErrorRespository, origenErrorRepository, trazabilidadCodigoRepository, nivelErrorRepository, tipoAccionRepository, accionUsuarioRepository);
	}
	
	private List<AccionUsuario> setUpUserEventList(){
		List<AccionUsuario> userEvents = new ArrayList<>();
		
		return userEvents;
	}
	
	@BeforeAll
	void loadMocks() {
		AplicacionError ae = new AplicacionError();
		when(aplicacionErrorRespository.findById(Long.valueOf(1))).thenReturn(Optional.of(ae));
		when(aplicacionErrorRespository.findById(Long.valueOf(-1))).thenReturn(Optional.empty());
		
		TipoAccion ta = new TipoAccion();
		when(tipoAccionRepository.findByNombreAccion(TipoAccion.TIPO_ACCION_BOTON)).thenReturn(ta);
		when(tipoAccionRepository.findByNombreAccion(TipoAccion.TIPO_ACCION_EXCEPCION)).thenReturn(ta);
		when(tipoAccionRepository.findByNombreAccion(TipoAccion.TIPO_ACCION_INPUT)).thenReturn(ta);
		when(tipoAccionRepository.findByNombreAccion(TipoAccion.TIPO_ACCION_NAVEGACION)).thenReturn(ta);
		when(tipoAccionRepository.findByNombreAccion(TipoAccion.TIPO_ACCION_REQUEST)).thenReturn(ta);
		
		NivelError ne = new NivelError();
		when(nivelErrorRepository.findByNombreNivel(NivelError.NIVEL_ERROR_EXCEPTION)).thenReturn(ne);
		when(nivelErrorRepository.findByNombreNivel(NivelError.NIVEL_ERROR_INFO)).thenReturn(ne);
		
		OrigenError or = new OrigenError();
		when(origenErrorRepository.findByNombreOrigen(OrigenError.ORIGEN_ERROR_BACKEND)).thenReturn(or);
		when(origenErrorRepository.findByNombreOrigen(OrigenError.ORIGEN_ERROR_FRONTEND)).thenReturn(or);
		
		when(aplicacionErrorRespository.save(null)).thenThrow(SQLException.class);
		when(trazabilidadCodigoRepository.save(null)).thenThrow(SQLException.class);
		when(accionUsuarioRepository.saveAll(null)).thenThrow(SQLException.class);
	}
	
	/**
	 * If i call the categorizeUserEvents from AplicacionErrorServiceI
	 * With the parameter as null
	 * It would throw a BadRequestDataException
	 */
	@Test
	void testCategorizeUserEventsForNullList() {
		
		assertThrows(BadRequestDataException.class, ()->{
			aplicacionErrorService.categorizeUserEvents(null);
		});
	}
	
	/**
	 * If i call the categorizeUserEvents from AplicacionErrorServiceI
	 * With the parameter with NivelError and TipoAccion that no exist
	 * It would throw a BadRequestDataException
	 */
	@Test
	void testCategorizeUserEventsThatNotExist() {
		List<AccionUsuario> userEventsType = setUpUserEventList();
		
		AccionUsuario accionWithBadType = new AccionUsuario();
		TipoAccion actionType = new TipoAccion();
		actionType.setNombreAccion("Not Exist");
		accionWithBadType.setTipoAccion(actionType);
		
		userEventsType.add(accionWithBadType);
		
		assertThrows(BadRequestDataException.class, ()->{
			aplicacionErrorService.categorizeUserEvents(userEventsType);
		});
		
		List<AccionUsuario> userEventNivel = setUpUserEventList();
		
		AccionUsuario accionWithBadNivel = new AccionUsuario();
		NivelError nivel = new NivelError();
		nivel.setNombreNivel("Not Exist");
		accionWithBadNivel.setNivelError(nivel);
		
		userEventNivel.add(accionWithBadNivel);
		
		assertThrows(BadRequestDataException.class, ()->{
			aplicacionErrorService.categorizeUserEvents(userEventNivel);
		});
	}
	
	@Test
	void testSaveTrazabilitiyAndUsereventsWithNullParameters() {
		TrazabilidadCodigo traza = new TrazabilidadCodigo();
		List<AccionUsuario> userEvents = setUpUserEventList();
		
		assertThrows(BadRequestDataException.class, ()->{
			aplicacionErrorService.saveTrazabilitiyandUserevents(null, traza, userEvents);
		});
		
		assertThrows(BadRequestDataException.class, ()->{
			aplicacionErrorService.saveTrazabilitiyandUserevents(Long.valueOf(1), null, userEvents);
		});
		
		assertThrows(BadRequestDataException.class, ()->{
			aplicacionErrorService.saveTrazabilitiyandUserevents(Long.valueOf(1), traza, null);
		});
	}
	
	@Test
	void testSaveTrazabilitiyAndUsereventsWithNoExistAplicacionError() {
		assertThrows(BadRequestDataException.class, ()->{
			List<AccionUsuario> userEvents = setUpUserEventList();
			aplicacionErrorService.saveTrazabilitiyandUserevents(Long.valueOf(-1), null, userEvents);
		});
	}
	
	@Test
	void testPersistAplicacionErrorFrontEndWithNullParameters() {
		AplicacionError appError = new AplicacionError();
		TrazabilidadCodigo traza = new TrazabilidadCodigo();
		List<AccionUsuario> userEvents = setUpUserEventList();
		
		assertThrows(BadRequestDataException.class,()->{
			aplicacionErrorService.persistAplicacionErrorFrontEnd(null, traza, userEvents);
		});
		assertThrows(BadRequestDataException.class,()->{
			aplicacionErrorService.persistAplicacionErrorFrontEnd(appError, null, userEvents);
		});
		assertThrows(BadRequestDataException.class,()->{
			aplicacionErrorService.persistAplicacionErrorFrontEnd(appError, traza, null);
		});
	}
	
	@Test
	void testPersistAplicacionErrorBackend() {
		Exception ex = new RuntimeException();
		String applicationName = "APP NAME";
		assertThrows(BadRequestDataException.class,()->{
			aplicacionErrorService.persistAplicacionErrorBackend(null, applicationName);
		});
		assertThrows(BadRequestDataException.class,()->{
			aplicacionErrorService.persistAplicacionErrorBackend(ex, "");
		});
		assertThrows(BadRequestDataException.class,()->{
			aplicacionErrorService.persistAplicacionErrorBackend(ex, null);
		});
	}
}
