interface errorInfo {
  message: string;

  method: string;
}

//Almacena los metodos que se ejecutaron previos al error
interface metodos {
  metodo: string | undefined;
  location: string | undefined;
  lines: string | undefined;
}

export { errorInfo, metodos };
