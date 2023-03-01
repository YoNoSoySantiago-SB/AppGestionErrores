# CuadroDialogo

This library was generated with [Angular CLI](https://github.com/angular/angular-cli) version 14.2.0.

## Code scaffolding

Run `ng generate component component-name --project cuadroDialogo` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module --project cuadroDialogo`.

> Note: Don't forget to add `--project cuadroDialogo` or else it will be added to the default project in your `angular.json` file.

## Build

Run `ng build cuadroDialogo` to build the project. The build artifacts will be stored in the `dist/` directory.

## Publishing

After building your library with `ng build cuadroDialogo`, go to the dist folder `cd dist/cuadro-dialogo` and run `npm publish`.

## Running unit tests

Run `ng test cuadroDialogo` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI Overview and Command Reference](https://angular.io/cli) page.

## Installing and use in your app

ve a la consola e instala esta libreria

npm i cuadro-dialogo

Adicionalmente, debes instalar estas librerias :

npm i @angular/material
npm i @angular/cdk
npm i event-logs
npm i control-errores

En el archivo angular.json debes agregar a los estilos esta linea:

"node_modules/@angular/material/prebuilt-themes/indigo-pink.css"

Una vez instalada las librerias debes ir al app.module.ts de tu aplicacion, en el @NgModule agregar al imports estos modulos:

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

Finalmente en el archivo app.component.ts de tu aplicacion , en el constructor agregar estos parametros :

private router: Router, private routerEvents: RouterEvents

E implementar en la clase OnInit

dentro del metodo ngOnInit agregar:

nameApp('Nombre de la aplicación');

ejemplo:

export class AppComponent implements OnInit {
constructor(private router: Router, private routerEvents: RouterEvents) {}

ngOnInit() {
nameApp('Aplicación prueba');
}
title = 'library';
}

Una vez que se complete este procedimiento, cualquier error que no sea de Http que ocurra en tu aplicación será capturado y se presentará un matDialog para informar o no informar del error, enviando la información necesaria sobre dicho error.

Para usarlo en una respuesta http, haz lo siguiente:

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
//This line
crearCuadroError(this.matDialog,this.ngzone,Error(err).stack).handleError(err);
},
});
