let name: string = '';
let namekey!: string;
let err: any;

let keyParent: string = '';

export function nameApp(nameApp: string) {
  name = nameApp;
}

export function getnameApp() {
  return name;
}

export function nameKey(method?: string) {
  if (method) {
    namekey = method;
  }
}

export function getnameKey() {
  return namekey;
}
export function nameKeyParent(method: string) {
  keyParent = method;
}

export function getnameParent() {
  return keyParent;
}

export function errorName(method?: Error) {
  err = method;
}

export function getError() {
  return err;
}
