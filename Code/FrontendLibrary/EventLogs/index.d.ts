declare module 'event-logs' {
    export function saveRequestHTTP(request:string): void;
    export function saveNavigation(navigation:string): void;
    export function saveError(id:number): void;
    export async function persistAplicacionErrorFrontEnd(aplicacionError:Object,trazabilidadError:Object,accionesUsuario:Object[]):Promise<void>;
    export async function saveTrazabilitiyandUserevents(idaplicacionError:number,trazabilidadError:Object,accionesUsuario: Object[]):Promise<void>;
  }