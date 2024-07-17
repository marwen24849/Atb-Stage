import { Injectable } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly validCardNumber = '123456789';
  private readonly validPassword = 'password';
  private authenticated = false;
  constructor(private kc: KeycloakService){}

  authenticate(auth : boolean): boolean {
    if (auth) {
      this.authenticated = true;
      return true;
    } else {
      this.authenticated = false;
      return false;
    }
  }

  isAuthenticated(): boolean {
    return this.authenticated;
  }

  hasRole(role:string):boolean{
    if(this.kc.isLoggedIn())
      return this.kc.getUserRoles().includes(role);
    return false;
  }
}
