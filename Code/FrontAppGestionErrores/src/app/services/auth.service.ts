import { AuthConfig, OAuthService } from 'angular-oauth2-oidc';
import { Injectable } from '@angular/core';

import { environment } from 'src/environment/environment';
import { Router } from '@angular/router';


const oAuthConfig: AuthConfig = {
  issuer: environment.GOOGLE_ISSUER,
  strictDiscoveryDocumentValidation: false,
  redirectUri: window.location.origin+'/auth/google',
  clientId: environment.GOOGLE_CLIENT_ID,
  responseType: 'code',
  scope: 'openid profile email',
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private readonly oAuthService: OAuthService, private router: Router) {

  }

  isLoggedIn(): boolean {
    return this.oAuthService.hasValidAccessToken()
  }

  checkLogIn() {
    if (this.oAuthService.hasValidAccessToken()) {
      this.oAuthService.loadUserProfile().then((userProfile) => {
        console.log(JSON.stringify(userProfile))
        window.sessionStorage.setItem('userProfile', JSON.stringify(userProfile))
      })
    }
  }

  signIn() {
    console.log("1")
    this.oAuthService.configure(oAuthConfig)
    this.oAuthService.logoutUrl = "https://www.google.com/accounts/Logout"
    this.oAuthService.loadDiscoveryDocument().then(()=>{
      this.oAuthService.tryLoginCodeFlow().then(() => {
        console.log("2")
        if (!this.oAuthService.hasValidAccessToken()) {
          console.log("3")
          this.oAuthService.initLoginFlow()
        } else {
          console.log("4")
          this.oAuthService.loadUserProfile().then((userProfile) => {
            console.log(JSON.stringify(userProfile))
            window.localStorage.setItem('userProfile', JSON.stringify(userProfile))
          })
        }
      })
    })
  }

  signOut() {
    const idTokenHint  = this.oAuthService.getIdToken();
    this.oAuthService.logOut({idTokenHint })
  }
}
