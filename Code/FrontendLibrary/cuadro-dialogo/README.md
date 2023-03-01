## Instalación de la libreria en tu aplicación

1.Para poder utilizar esta Dirigete a la consola de tu aplicación y escribe la siguiente linea de comando:

    npm i cuadro-dialogo

Adicionalmente, debes instalar estas librerias:

    npm i @angular/material
    npm i @angular/cdk
    npm i event-logs
    npm i control-errores

2.En el archivo angular.json debes agregar a los estilos esta linea:

    "node_modules/@angular/material/prebuilt-themes/indigo-pink.css"

3.Una vez instalada las librerias debes ir al app.module.ts de tu aplicacion importar y en el @NgModule agregar al imports estos modulos:

    ControlErroresModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatDialogModule,
    CuadroDialogoModule,

Y en la sección de providers del mismo archivo agregar:

    providers: [
    { provide: ErrorHandler, useClass: ErrorHandlerService },
    {provide: HTTP_INTERCEPTORS,useClass: MyInterceptor,multi: true,},
    RouterEvents,
    ],

4.Finalmente en el archivo app.component.ts de tu aplicacion , en el constructor agregar estos parametros:

    private router: Router, private routerEvents: RouterEvents

E implementar en la clase OnInit

dentro del metodo ngOnInit agregar:

    nameApp('Nombre de la aplicación');

Ejemplo:

    export class AppComponent implements OnInit {
      constructor(private router: Router, private routerEvents: RouterEvents) {}

      ngOnInit() {
      nameApp('Tu nombre de aplicación');
      }
      title = 'library';
      }

5.Una vez que se complete este procedimiento, cualquier error que no sea de Http que ocurra en tu aplicación será capturado y se presentará un matDialog para informar o no informar del error, enviando la información necesaria sobre dicho error.

6.Para usarlo en una respuesta http, haz lo siguiente:

En el constructor de tu componente, ingresa los siguientes parámetros.

    private matDialog: MatDialog,
    private ngzone: NgZone

En la sección de error de la respuesta http, agrega la línea.

    crearCuadroError(matDialog,ngzone,Error(err).stack).handleError(err);

Ejemplo:

    this.serviceHttp.getUsuario().subscribe({
    next: (resp) => {
    console.log(resp);
    },
    error: (err) => {
    //Este linea
    crearCuadroError(this.matDialog,this.ngzone,Error(err).stack).handleError(err);
    },
    });
