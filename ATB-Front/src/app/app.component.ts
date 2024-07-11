import { Component, OnInit } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit{
  title = 'Pfe-Star-Front';
  constructor( public kc : KeycloakService) {}

  ngOnInit(): void {
    
    if(this.kc.isLoggedIn()){
      this.kc.loadUserProfile().then(profile =>{
        const username = profile.username || 'default_username';
        sessionStorage.setItem('username', username);
    }
        
        );
    }
  }

  onlogOut(){
    this.kc.logout(window.location.origin);
  }

  async login(){
    await this.kc.login({
      redirectUri: window.location.origin
    });
  }

}
 

