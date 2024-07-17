import { Component, OnInit } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { AuthService } from './service/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit{
  title = 'Pfe-Star-Front';
  constructor( public kc : KeycloakService, private authService: AuthService) {}

  ngOnInit(): void {
    
    if(this.kc.isLoggedIn()){
      this.kc.loadUserProfile().then(profile =>{
        sessionStorage.setItem('userId', this.kc.getKeycloakInstance().tokenParsed?.sub || '0')
        const username = profile.username || 'default_username';
        sessionStorage.setItem('username', username);
    }
        );
    }
  }

  onlogOut(){
    sessionStorage.removeItem('username');
    sessionStorage.removeItem('userId');
    this.kc.logout(window.location.origin);
    
  }

  async login(){
    await this.kc.login({
      redirectUri: window.location.origin
    });
  }

  hasRole(role: string){
    return this.authService.hasRole(role);
  }
}
 

