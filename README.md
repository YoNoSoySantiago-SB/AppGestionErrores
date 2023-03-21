# APP GESTIÓN DE ERRORES

# Instrucciones de Instalación y Uso de la librería Backend

Esta librearía fue diseñada para capturar errores no controlados en proyectos Rest con Spring. Además de su funcionalidad principal, también cuenta con una implementación para manejar errores controlados. Para instalar la librería, sigue los pasos descritos en este archivo. Después de instalar la librería y agregar las dependencias necesarias, debes agregar el código de configuración a tu clase Configuration. Con esto, habrás completado la instalación y configuración de la librería en tu proyecto Rest con Spring.

## Instalación

Para instalar la librería, sigue los siguientes pasos:

1. Descarga el archivo .jar de BackendLibrary desde el siguiente enlace: [BackendLibrary-0.0.1-SNAPSHOT.jar](https://www.notion.so/Code%5CBackendLibrary%5Ctarget%5CBackendLibrary-0.0.1-SNAPSHOT.jar).
2. Crea una carpeta llamada 'lib' en la raíz de tu proyecto Rest.
3. Copia el archivo .jar de BackendLibrary en la carpeta 'lib' creada en el paso anterior.

### Gradle

Si estás utilizando Gradle, agrega la siguiente línea de implementación en tu archivo build.gradle:

```
implementation files('lib/BackendLibrary-0.0.1-SNAPSHOT.jar')

```

### Maven

Si estás utilizando Maven, ejecuta el siguiente comando en la ruta principal de tu proyecto:

```bash
mvn install:install-file \
   -Dfile=/lib/BackendLibrary-0.0.1-SNAPSHOT.jar \
   -DgroupId=com.segurosbolivar.refactoring.techcamp \
   -DartifactId=BackendLibrary \
   -Dversion=0.0.1-SNAPSHOT \
   -Dpackaging=jar \
   -DgeneratePom=true
```

y agrega el siguiente bloque de configuración en tu archivo pom.xml:

```xml
<dependency>
		<groupId>com.segurosbolivar.refactoring.techcamp</groupId>
		<artifactId>BackendLibrary</artifactId>
		<version>0.0.1-SNAPSHOT</version>
</dependency>

```

## Dependencias

Una vez que hayas instalado la librería, debes agregar las siguientes dependencias en tu archivo pom.xml o build.gradle:

### Maven

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
<dependency>
    <groupId>jakarta.persistence</groupId>
    <artifactId>jakarta.persistence-api</artifactId>
</dependency>
```

### Gradle

```groovy
implementation 'org.springframework.boot:spring-boot-starter-web'
implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
runtimeOnly 'org.springframework.boot:spring-boot-devtools'
implementation 'org.postgresql:postgresql'
compileOnly 'org.projectlombok:lombok'
testImplementation 'org.springframework.boot:spring-boot-starter-test'
implementation 'jakarta.persistence:jakarta.persistence-api'
```

## Uso para Excepciones no controladas

Una vez que hayas instalado la librería y agregado las dependencias necesarias, debes agregar el siguiente código a tu clase Configuration:

```java
@Configuration
@Import(GlobalExceptionHandlerConfig.class)
public class ExampleConfigurationClass {

}
```

Con esto, habrás completado la instalación y configuración de BackendLibrary en tu proyecto Rest con Spring para capturar errores no controlados.

## Uso para Excepciones controladas

Para manejar excepciones controladas, debes llamar al método `GlobalExceptionHandler.catchException(e)` dentro del catch donde se controló la excepción. Por ejemplo:

```java
try {
    // código que puede generar una excepción
} catch (MiExcepcion e) {
    GlobalExceptionHandler.catchException(e);
}
```

Con esto, habrás manejado la excepción controlada y la librería se encargará de registrar la excepción y enviar una respuesta al cliente.

# Instrucciones para instalación y uso de la librería Frontend

1. Dirigete hacia la consola y ejecuta el siguiente comando:

```markdown
npm i cuadro-dialogo
```

Adicionalmente, debes instalar estas librerias:

```markdown
npm i @angular/material
npm i @angular/cdk
npm i event-logs
npm i control-errores
```

2. En el archivo angular.json debes agregar a los estilos esta linea:

```
    "node_modules/@angular/material/prebuilt-themes/indigo-pink.css"
```

3. Una vez instalada las librerias debes ir al app.module.ts de tu aplicacion importar y en el @NgModule agregar al imports estos modulos:

```jsx
ControlErroresModule,
HttpClientModule,
BrowserAnimationsModule,
MatDialogModule,
CuadroDialogoModule,
```

Y en la sección de providers del mismo archivo agregar:

```jsx
providers: [
    { provide: ErrorHandler, useClass: ErrorHandlerService },
    {provide: HTTP_INTERCEPTORS,useClass: MyInterceptor,multi: true,},
    RouterEvents,
],
```
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

5.Una vez que se complete este procedimiento, cualquier error presentado en angular será capturado y quedará  en la autonomia del usuario reportar el problema a Jira

6.Los errores HTTP detectados serán unicamente con status mayor a 500 (de servidor) y 0 (servidor no activo o no encontrado) y debe estar conectado a una API que contengo la libreria de backend

```

# Eventos del Usuario
Para utilizar esta libreria donde se guardaran los ultimos x eventos generados por el usuario, donde x podria modificarse segun lo requiera el desarrollador, debera seguir los siguientes pasos:

1. Importe el modulo de HTTP_INTERCEPTORS en el modulo principal:

```tsx
import {HTTP_INTERCEPTORS} from '@angular/common/http';
```

2. Importe los modulos de InterceptorHttp y RouterEvents de la libreria cuadro-dialogo:

```tsx
import { InterceptorHttp, RouterEvents } from 'cuadro-dialogo';
```

3. Agregue en los providers del modulo principal lo siguiente:

```tsx
providers: [ 
    {provide: HTTP_INTERCEPTORS,
    useClass: InterceptorHttp,
    multi: true
  },
    RouterEvents
]
```

4. En el componente principal importe los modulos de Router y RouterEvents:

```mermaid
graph TD
  Mermaid --> Diagram
```

```tsx
import { Router } from '@angular/router';
import { RouterEvents } from 'cuadro-dialogo';
```

5. Inyecte en el constructor del componente principal ambos modulos mencionados anteriormente:

```tsx
constructor(private router: Router, private routerEvents: RouterEvents) { }
```

6. Dirigase al node_modules del proyecto, entre a la carpeta event-logs, y abra el archivo event-logs.js. Modifique la variable amount segun requiera que sea la cantidad de eventos generados por el usuario a guardar.

```jsx
let amount = x;
```

1. Por ultimo, los campos que desee que no se filtren, es decir, que no se persista lo que el usuario digito, debera poner en la etiqueta del input la siguiente propiedad:

```html
data-input-type="false"
```

Por ejemplo:

```html
<div class="form-outline mb-4">
	<label class="form-label" for="form2Example17">Email address</label>
	<input data-input-type="false" type="text" id="email" name="email" class="form-control form-control-lg" [(ngModel)]="account.email" />                  
</div>
```

*Nota: Es muy importante contar con buenas practicas al momento de crear etiquetas en inputs, deberia poder tener la propiedad de id y name ya que cuando se persiste en la base de datos y se muestra en el reporte, para una rapida identificacion, por lo que si la etiqueta no cuenta con estas propiedades en el reporte saldra como Desconocido*
