package com.segurosbolivar.refactoring.techcamp.errors;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.segurosbolivar.refactoring.techcamp.errors.customexceptions.BadRequestDataException;
import com.segurosbolivar.refactoring.techcamp.errors.customexceptions.NoResultException;
import com.segurosbolivar.refactoring.techcamp.errors.dtos.AccionUsuarioDTO;
import com.segurosbolivar.refactoring.techcamp.errors.dtos.AplicacionErrorDTO;
import com.segurosbolivar.refactoring.techcamp.errors.dtos.AplicacionErrorResponseDTO;
import com.segurosbolivar.refactoring.techcamp.errors.dtos.ExceptionDto;
import com.segurosbolivar.refactoring.techcamp.errors.dtos.TrazabilidadCodigoDTO;
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
import com.segurosbolivar.refactoring.techcamp.errors.service.implementation.AccionUsuarioServiceImp;
import com.segurosbolivar.refactoring.techcamp.errors.service.implementation.AplicacionErrorServiceImp;
import com.segurosbolivar.refactoring.techcamp.errors.service.implementation.EmailSenderServiceImp;
import com.segurosbolivar.refactoring.techcamp.errors.service.implementation.TrazabilidadCodigoServiceImp;
import com.segurosbolivar.refactoring.techcamp.errors.service.interfaces.AccionUsuarioServiceI;
import com.segurosbolivar.refactoring.techcamp.errors.service.interfaces.AplicacionErrorServiceI;
import com.segurosbolivar.refactoring.techcamp.errors.service.interfaces.EmailSenderServiceI;
import com.segurosbolivar.refactoring.techcamp.errors.service.interfaces.TrazabilidadCodigoServiceI;

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
	
	@InjectMocks
	private AplicacionErrorServiceI aplicacionErrorService;
	
	@InjectMocks
	private AccionUsuarioServiceI accionUsuarioService;
	
	@InjectMocks
	private TrazabilidadCodigoServiceI trazabilidadCodigoService;
	
	@InjectMocks
	private EmailSenderServiceI emailSenderService;
	

	public AppGestionErroresApiApplicationTests() {
		emailSenderService= new EmailSenderServiceImp(null);
		aplicacionErrorService = new AplicacionErrorServiceImp(aplicacionErrorRespository, origenErrorRepository, trazabilidadCodigoRepository, nivelErrorRepository, tipoAccionRepository, accionUsuarioRepository,emailSenderService);
		accionUsuarioService= new AccionUsuarioServiceImp(accionUsuarioRepository);
		trazabilidadCodigoService= new TrazabilidadCodigoServiceImp(trazabilidadCodigoRepository);
	}
	
	private List<AccionUsuario> setUpUserEventList() {
	    AccionUsuario[] array = {
	        new AccionUsuario(new TipoAccion(TipoAccion.TIPO_ACCION_BOTON), new NivelError(NivelError.NIVEL_ERROR_INFO)),
	        new AccionUsuario(new TipoAccion(TipoAccion.TIPO_ACCION_EXCEPCION), new NivelError(NivelError.NIVEL_ERROR_EXCEPTION)),
	        new AccionUsuario(new TipoAccion(TipoAccion.TIPO_ACCION_INPUT), new NivelError(NivelError.NIVEL_ERROR_INFO)),
	        new AccionUsuario(new TipoAccion(TipoAccion.TIPO_ACCION_NAVEGACION), new NivelError(NivelError.NIVEL_ERROR_INFO)),
	        new AccionUsuario(new TipoAccion(TipoAccion.TIPO_ACCION_REQUEST), new NivelError(NivelError.NIVEL_ERROR_INFO))
	    };
	    List<AccionUsuario> userActions = new ArrayList<>();
	    
	    for(AccionUsuario userAction : array) {
	    	userActions.add(userAction);
	    }
	    return userActions;
	}
	private List<AccionUsuarioDTO> setUpUserEventListDTOS() {
		AccionUsuarioDTO[] array = {
				new AccionUsuarioDTO(NivelError.NIVEL_ERROR_INFO,TipoAccion.TIPO_ACCION_BOTON),
				new AccionUsuarioDTO(NivelError.NIVEL_ERROR_EXCEPTION,TipoAccion.TIPO_ACCION_EXCEPCION),
				new AccionUsuarioDTO(NivelError.NIVEL_ERROR_INFO,TipoAccion.TIPO_ACCION_INPUT),
				new AccionUsuarioDTO(NivelError.NIVEL_ERROR_INFO,TipoAccion.TIPO_ACCION_NAVEGACION),
				new AccionUsuarioDTO(NivelError.NIVEL_ERROR_INFO,TipoAccion.TIPO_ACCION_REQUEST)
	    };
	    List<AccionUsuarioDTO> userActions = new ArrayList<>();
	    
	    for(AccionUsuarioDTO userAction : array) {
	    	userActions.add(userAction);
	    }
	    return userActions;
	}
	
	@BeforeAll
	void loadMocks() {
		AplicacionError ae = new AplicacionError();
		when(aplicacionErrorRespository.findById(Long.valueOf(1))).thenReturn(Optional.of(ae));
		when(aplicacionErrorRespository.findById(Long.valueOf(-1))).thenReturn(Optional.empty());
		when(aplicacionErrorRespository.save(Mockito.any(AplicacionError.class))).thenAnswer(invocation -> invocation.getArgument(0));
		
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
		
		when(aplicacionErrorRespository.save(null)).thenThrow(new RuntimeException());
		when(trazabilidadCodigoRepository.save(null)).thenThrow(new RuntimeException());
		when(accionUsuarioRepository.saveAll(null)).thenThrow(new RuntimeException());
	}

	@Test
	void testCategorizeUserEventsThatTypeNotExist() {
		List<AccionUsuario> userEventsType = setUpUserEventList();
		
		AccionUsuario accionWithBadType = new AccionUsuario();
		NivelError nivel = new NivelError();
		nivel.setNombreNivel("Info");
		TipoAccion actionType = new TipoAccion();
		actionType.setNombreAccion("Not Exist");
		accionWithBadType.setTipoAccion(actionType);
		accionWithBadType.setNivelError(nivel);
		
		userEventsType.add(accionWithBadType);
		
		assertThrows(BadRequestDataException.class, ()->{
			aplicacionErrorService.categorizeUserEvents(userEventsType);
		});
	}
	
	@Test
	void testCategorizeUserEventsThatNivelNotExist() {
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
	void testCategorizeUserEventsForNullList() {
		assertThrows(BadRequestDataException.class, ()->{
			aplicacionErrorService.categorizeUserEvents(null);
		});
	}
	
	
	
	@Test
	void testCategorizeUserEvents() {
		List<AccionUsuario> userEventsType = setUpUserEventList();
		
		AccionUsuario accion = new AccionUsuario();
		TipoAccion actionType = new TipoAccion();
		actionType.setNombreAccion("Boton");
		NivelError nivel = new NivelError();
		nivel.setNombreNivel("Info");
		accion.setNivelError(nivel);
		
		accion.setTipoAccion(actionType);
		
		userEventsType.add(accion);
		
		assertDoesNotThrow(()->{
			aplicacionErrorService.categorizeUserEvents(userEventsType);
		});
	}
	
	@Test
	void testSaveTrazabilitiyAndUsereventsWithNullAppId() {
		TrazabilidadCodigoDTO traza = new TrazabilidadCodigoDTO();
		List<AccionUsuarioDTO> userEvents = setUpUserEventListDTOS();
		AplicacionErrorDTO appError = new AplicacionErrorDTO();
		
		assertThrows(BadRequestDataException.class, ()->{
			aplicacionErrorService.saveTrazabilitiyandUserevents(null,appError, traza, userEvents);
		});
	}
	
	@Test
	void testSaveTrazabilitiyAndUsereventsWithNullTraza() {
		List<AccionUsuarioDTO> userEvents = setUpUserEventListDTOS();
		AplicacionErrorDTO appError = new AplicacionErrorDTO();
		assertThrows(BadRequestDataException.class, ()->{
			aplicacionErrorService.saveTrazabilitiyandUserevents(Long.valueOf(1),appError, null, userEvents);
		});
	}
	
	@Test
	void testSaveTrazabilitiyAndUsereventsWithNullUserEvents() {
		TrazabilidadCodigoDTO traza = new TrazabilidadCodigoDTO();
		AplicacionErrorDTO appError = new AplicacionErrorDTO();
		assertThrows(BadRequestDataException.class, ()->{
			aplicacionErrorService.saveTrazabilitiyandUserevents(Long.valueOf(1),appError, traza, null);
		});
	}
	
	@Test
	void testSaveTrazabilitiyAndUsereventsWithNoExistAplicacionError() {
		TrazabilidadCodigoDTO traza = new TrazabilidadCodigoDTO();
		List<AccionUsuarioDTO> userEvents = setUpUserEventListDTOS();
		AplicacionErrorDTO appError = new AplicacionErrorDTO();
		assertThrows(BadRequestDataException.class, ()->{
			aplicacionErrorService.saveTrazabilitiyandUserevents(Long.valueOf(-1),appError, traza, userEvents);
		});
	}
	
	@Test
	void testSaveTrazabilitiyAndUserevents() {
		List<AccionUsuarioDTO> userEvents = setUpUserEventListDTOS();
		TrazabilidadCodigoDTO traza = new TrazabilidadCodigoDTO();
		AplicacionErrorDTO appError = new AplicacionErrorDTO();
		assertThrows(BadRequestDataException.class, ()->{
			aplicacionErrorService.saveTrazabilitiyandUserevents(Long.valueOf(1),appError, traza, userEvents);
		});
	}
	
	@Test
	void testPersistAplicacionErrorFrontEndWithNullAppError() {
		TrazabilidadCodigoDTO traza = new TrazabilidadCodigoDTO();
		List<AccionUsuarioDTO> userEvents = setUpUserEventListDTOS();
		assertThrows(BadRequestDataException.class,()->{
			aplicacionErrorService.persistAplicacionErrorFrontEnd(null, traza, userEvents);
		});
	}
	
	@Test
	void testPersistAplicacionErrorFrontEndWithNullTraza() {
		AplicacionErrorDTO appError = new AplicacionErrorDTO();
		List<AccionUsuarioDTO> userEvents = setUpUserEventListDTOS();
		assertThrows(BadRequestDataException.class,()->{
			aplicacionErrorService.persistAplicacionErrorFrontEnd(appError, null, userEvents);
		});
	}
	
	@Test
	void testPersistAplicacionErrorFrontEndWithNullUserEvents() {
		AplicacionErrorDTO appError = new AplicacionErrorDTO();
		TrazabilidadCodigoDTO traza = new TrazabilidadCodigoDTO();
		assertThrows(BadRequestDataException.class,()->{
			aplicacionErrorService.persistAplicacionErrorFrontEnd(appError, traza, null);
		});
	}
	
	
	@Test
	void testPersistAplicacionErrorBackendWithNullException() {
		ExceptionDto exceptionDto = new ExceptionDto();
		exceptionDto.setApplicationName("APP NAME");
		assertThrows(BadRequestDataException.class,()->{
			aplicacionErrorService.persistAplicacionErrorBackend(exceptionDto);
		});
	}
	
	@Test
	void testPersistAplicacionErrorBackendWithNullAplicationName() {
		ExceptionDto exceptionDto = new ExceptionDto();
		Exception ex = new RuntimeException();
		exceptionDto.setException(ex);
		assertThrows(BadRequestDataException.class,()->{
			aplicacionErrorService.persistAplicacionErrorBackend(exceptionDto);
		});
	}
	
	@Test
	void testPersistAplicacionErrorBackendWithEmptyAplicationName() {
		ExceptionDto exceptionDto = new ExceptionDto();
		Exception ex = new RuntimeException();
		exceptionDto.setException(ex);
		exceptionDto.setApplicationName("");
		assertThrows(BadRequestDataException.class,()->{
			aplicacionErrorService.persistAplicacionErrorBackend(exceptionDto);
		});
	}
	
	@Test
	void testPersistAplicacionErrorBackend() {
		ExceptionDto exceptionDto = new ExceptionDto();
		Exception ex = new RuntimeException();
		exceptionDto.setException(ex);
		exceptionDto.setApplicationName("APP NAME");
		assertDoesNotThrow(()->{
			aplicacionErrorService.persistAplicacionErrorBackend(exceptionDto);
		});
	}	
	
	@Test
	void testFindByIdAplicacionErrorNull() {
		assertThrows(BadRequestDataException.class, ()->{
			aplicacionErrorService.findById(null);
		});
	}
	
	@Test
	void testfindAllActionsUserByAplicacionErrorIdAplicacionErrorNull() {
		assertThrows(BadRequestDataException.class, ()->{
			accionUsuarioService.findAllActionsUserByAplicacionErrorIdAplicacionError(null);
		});
	}
	
	@Test
	void testfindAllTrazabilitiesByAplicacionErrorIdAplicacionErrorNull() {
		assertThrows(BadRequestDataException.class, ()->{
			trazabilidadCodigoService.findAllTrazabilitiesByAplicacionErrorIdAplicacionError(null);
		});
	}
	
	@Test
	void testfindAllTrazabilitiesByAplicacionErrorIdAplicacionErrorNoExist() {
		assertThrows(NoResultException.class, ()->{
			trazabilidadCodigoService.findAllTrazabilitiesByAplicacionErrorIdAplicacionError((long) -1);
		});
	}
	
	@Test
	void testfindAllActionsUserByAplicacionErrorIdAplicacionErrorNoExist() {
		assertThrows(NoResultException.class, ()->{
			accionUsuarioService.findAllActionsUserByAplicacionErrorIdAplicacionError((long) -1);
		});
	}

}
