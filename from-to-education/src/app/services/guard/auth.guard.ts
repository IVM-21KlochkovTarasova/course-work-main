import { Injectable } from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router} from '@angular/router';
import { Observable } from 'rxjs';
import {KeycloakAuthGuard, KeycloakService} from 'keycloak-angular';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard extends KeycloakAuthGuard {



  constructor(
    protected readonly router: Router,
    protected readonly keycloak: KeycloakService
) {
    super(router, keycloak);
  }

  async isAccessAllowed(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Promise<boolean> {
    return new Promise((resolve, reject) => {
      let permission;
      if (!this.authenticated) {
        this.keycloakAngular.login().catch((e) => console.error(e));
        return reject(false);
      }

      const requiredRoles: string[] = route.data.roles;
      if (!requiredRoles || requiredRoles.length === 0) {
        permission = true;
      } else {
        if (!this.roles || this.roles.length === 0) {
          permission = false;
        }
        permission = false;
        requiredRoles.map(role => {
          console.log(role);
          if (this.roles.indexOf(role) > -1) {
            permission = true;
          } else {
            permission = permission || false;
          }
        });
        console.log(permission);
      }
      if (!permission){
        this.router.navigate(['/']);
      }
      resolve(permission);
    });
  }

  logout() {
    console.log('**  LOGOUT');
    this.keycloakAngular.logout();
  }

  public checkAuth() {
    console.log(this.authenticated);
    return this.authenticated;
  }
}
