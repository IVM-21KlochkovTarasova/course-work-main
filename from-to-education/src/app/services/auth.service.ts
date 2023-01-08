import { Injectable } from '@angular/core';
import {KeycloakService} from 'keycloak-angular';
import {KeycloakTokenParsed} from 'keycloak-js';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private keycloakService: KeycloakService) { }

  public getLoggedUser(): KeycloakTokenParsed | undefined {
    try {
      const userDetails: KeycloakTokenParsed | undefined = this.keycloakService.getKeycloakInstance().idTokenParsed;
      return userDetails;
      } catch (e) {
      console.log('exception', e);
      return undefined;
    }
  }

  public getUserInfo(): Keycloak.KeycloakPromise<{}, void>{
    return this.keycloakService.getKeycloakInstance().loadUserInfo();
  }

  public isLoggedIn(): Promise<boolean> {
    return this.keycloakService.isLoggedIn();
  }

  public login(): void {
    this.keycloakService.login();
  }

  public logout(): void{
    this.keycloakService.logout(window.location.origin);
  }

  public getRoles(): string[]{
    return this.keycloakService.getUserRoles();
  }


}
