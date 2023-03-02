## Eventos del Usuario
Para utilizar esta libreria donde se guardaran los ultimos x eventos generados por el usuario, donde x podria modificarse segun lo requiera el desarrollador, debera seguir los siguientes pasos:

1. Importe el modulo de HTTP_INTERCEPTORS en el modulo principal:

<p align="center"><em>import {HttpClientModule,HTTP_INTERCEPTORS} from '@angular/common/http';</em></p>
2. Importe los modulos de InterceptorHttp y RouterEvents de la libreria cuadro-dialogo:

<p align="center"><em>import { InterceptorHttp, RouterEvents } from 'cuadro-dialogo';</em></p>
3. Agregue en los providers del modulo principal lo siguiente:

<p align="center"> <em>providers: [ 
    {provide: HTTP_INTERCEPTORS,
    useClass: InterceptorHttp,
    multi: true
  },
    RouterEvents
]</em></p>
4. En el componente principal importe los modulos de Router y RouterEvents:

<p align="center"><em>import { Router } from '@angular/router';</em></p>
<p align="center"><em>import { RouterEvents } from 'cuadro-dialogo';</em></p>
5. Inyecte en el constructor del componente principal ambos modulos mencionados anteriormente:

<p align="center"><em>constructor(private router: Router, private routerEvents: RouterEvents) { }</em></p>
6. Dirigase al node_modules del proyecto, entre a la carpeta event-logs, y abra el archivo event-logs.js. Modifique la variable amount segun requiera que sea la cantidad de eventos generados por el usuario a guardar.

<p align="center"><em>let amount = x;</em></p>