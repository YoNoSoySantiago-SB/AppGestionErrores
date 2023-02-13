declare module 'event-logs' {
    export function saveRequestHTTP(request:string): void;
    export function saveNavigation(navigation:string): void;
    export function saveError(id:number): void;
  }