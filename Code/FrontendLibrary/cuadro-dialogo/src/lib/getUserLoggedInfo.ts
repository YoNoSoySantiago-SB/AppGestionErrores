import { UserInfo } from "./interfaces"

let user_emai_key: string
let user_fullname_key: string | undefined

let location_session_storage: boolean = false
let location_local_storate: boolean = false

export function userInfoKeys(emai_key: string, fullname_key: string | undefined = undefined, useLocalStorage: boolean = false) {
  user_emai_key = emai_key
  user_fullname_key = fullname_key

  location_session_storage = !useLocalStorage;
  location_local_storate = useLocalStorage;
}

export function getUserEmailKey(): string {
  return user_emai_key;
}

export function getUserFullnameKey(): string | undefined {
  return user_fullname_key;
}

export function isLocationSessionStorage(): boolean {
  return location_session_storage;
}

export function isLocationLocalStorage(): boolean {
  return location_local_storate;
}

export function getUserInfo() {
  let userInfo: UserInfo = {
    fullname: 'DESCONOCIDO',
    email: 'DESCONOCIDO'
  }

  if (!user_fullname_key && !user_emai_key) {
    return userInfo;
  }

  let user_email = 'DESCONOCIDO'
  let user_fullname = 'DESCONOCIDO'

  if (sessionStorage) {
    user_email = window.sessionStorage.getItem(user_emai_key) ?? 'DESCONOCIDO'
    user_fullname = user_fullname_key ? window.sessionStorage.getItem(user_fullname_key) ?? 'DESCONOCIDO' : 'DESCONOCIDO'
  }

  if (location_local_storate) {
    user_email = window.localStorage.getItem(user_emai_key) ?? 'DESCONOCIDO'
    user_fullname = user_fullname_key ? window.localStorage.getItem(user_fullname_key) ?? 'DESCONOCIDO' : 'DESCONOCIDO'
  }

  userInfo = {
    email: user_email,
    fullname: user_fullname
  }

  return userInfo;

}
