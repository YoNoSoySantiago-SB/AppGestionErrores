export interface aplicacion_error {
  titulo_error: string;
  descripcion_error: string;
  nombre_aplicacion: string;
  correo_usuario?: string;
}
export interface trazabilidad_codigo {
  time: string;
  frame: string;
  category: string;
}
