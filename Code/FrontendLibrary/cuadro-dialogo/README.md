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
    nameKey('Clave del proyecto en JIRA');
    nameKeyParent('clave del epic en JIRA');
    userInfoKeys('savedEmail',undefined,true)

Ejemplo:

    export class AppComponent implements OnInit {
      constructor(private router: Router, private routerEvents: RouterEvents) {}

      ngOnInit() {
       nameApp('Aplicación prueba');
       nameKey('TEC');
       nameKeyParent('TEC-19');
       userInfoKeys('savedEmail',undefined,true)

      }
      title = 'library';
      }

5.Una vez que se complete este procedimiento, cualquier error presentado en angular será capturado y quedará en la autonomia del usuario reportar el problema a Jira

6.Los errores HTTP detectados serán unicamente con status mayor a 500 (de servidor) y 0 (servidor no activo o no encontrado) y debe estar conectado a una API que contengo la libreria de backend
