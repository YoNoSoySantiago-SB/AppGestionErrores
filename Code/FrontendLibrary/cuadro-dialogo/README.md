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

npm i cuadro-dialogo

Once installed, in your app.module.ts file you must configure the following modules in the @NgModule:

ControlErroresModule,
HttpClientModule,
BrowserAnimationsModule,
MatDialogModule,
CuadroDialogoModule,

and in the providers section, the following providers:

providers: [
{ provide: ErrorHandler, useClass: ErrorHandlerService },
{provide: HTTP_INTERCEPTORS,useClass: MyInterceptor,multi: true,},
RouterEvents,
],

Finally, in the root app.component.ts of your project, add to the constructor:

private router: Router, private routerEvents: RouterEvents

Once this procedure is done, any non-Http error that occurs in your application will be captured and a matDialog will be presented to report or not report the error, sending the necessary information about said error.

To use it in an http response, do the following:

In your component constructor, enter the following parameters
private matDialog: MatDialog,
private ngzone: NgZone

In the error part of the http response, add the line

crearCuadroError(matDialog,ngzone,Error(err).stack).handleError(err);

Example:

this.serviceHttp.getUsuario().subscribe({
next: (resp) => {
console.log(resp);
},
error: (err) => {
crearCuadroError(this.matDialog,this.ngzone,Error(err).stack).handleError(err);
},
});
