import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  Router,
  RouterStateSnapshot
} from '@angular/router';
import { KeycloakAuthGuard, KeycloakService } from 'keycloak-angular';

@Injectable({
  providedIn: 'root'
})
export class authGuard extends KeycloakAuthGuard {
  constructor(
    protected override readonly router: Router,
    protected readonly keycloak: KeycloakService,
  ) {
    super(router, keycloak);
  }

  public async isAccessAllowed(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ) {
    
    if (!this.authenticated) {
      await this.keycloak.login({
        redirectUri: window.location.origin + state.url
      });
    }

   
    const requiredRoles = route.data['roles'];

    
    if (!Array.isArray(requiredRoles) || requiredRoles.length === 0) {
      return true;
    }

    const hasRole = requiredRoles.some((role) => this.roles.includes(role));
    if (!hasRole) {
     
      this.router.navigate(['/unauthorized']);
      return false;
    }

    return true;

  }
}