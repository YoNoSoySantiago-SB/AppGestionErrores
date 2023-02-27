import { ErrorHandlerService } from 'cuadroDialogo';
import { createError } from 'control-errores';
import { TestBed } from '@angular/core/testing';
import { MatDialog } from '@angular/material/dialog';
import { HttpErrorResponse } from '@angular/common/http';
import { getCallStackhtpp, setCallStackHttp } from './cuadro-dialogo.service';

describe('ErrorHandlerService', () => {
  let service: ErrorHandlerService;
  let dialogSpy: jasmine.SpyObj<MatDialog>;

  beforeEach(() => {
    const dialogSpyObj = jasmine.createSpyObj('MatDialog', ['open']);

    TestBed.configureTestingModule({
      providers: [
        ErrorHandlerService,
        { provide: MatDialog, useValue: dialogSpyObj },
      ],
    });

    service = TestBed.inject(ErrorHandlerService);
    dialogSpy = TestBed.inject(MatDialog) as jasmine.SpyObj<MatDialog>;
  });

  it('should handle HttpErrorResponse with 409 status code', () => {
    const errorResponse = new HttpErrorResponse({
      status: 409,
      error: 'id-123',
    });

    setCallStackHttp('Error en el servidor');

    service.handleError(errorResponse);

    expect(dialogSpy.open).toHaveBeenCalledWith(jasmine.any(Object), {
      data: {
        icon: 'Error',
        message: errorResponse.message,
        buttonText: 'Reportar',
        ip: '',
        trazabilidad: 'Error en el servidor',
        mensajeobject: '',
        tiempo: jasmine.any(String),
        navegador: jasmine.any(String),
        eventosUsuario: jasmine.any(Object),
        status: 409,
      },
    });
  });
});
