import { APP_INITIALIZER, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DashboardComponent } from './monotoring/dashboard/dashboard.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { KeycloakAngularModule, KeycloakService } from 'keycloak-angular';
import { UnauthorizedComponent } from './component/unauthorized/unauthorized.component';
import { CreditRequestsComponent } from './component/credit-requests/credit-requests.component';
import { AddCreditRequestComponent } from './component/add-credit-request/add-credit-request.component';
import { CreditHistoryComponent } from './component/credit-history/credit-history.component';
import { PaymentsComponent } from './component/payments/payments.component';
import { AdminDashboardComponent } from './component/admin-dashboard/admin-dashboard.component';
import { TepAppComponent } from './tep-app/tep-app.component';
import { RechargeComponent } from './component/recharge/recharge.component';
import { CardLoginComponent } from './component/card-login/card-login.component';
import { CarteProfileComponent } from './component/carte-profile/carte-profile.component';
import { SideBarComponent } from './component/side-bar/side-bar.component';
import { AdminMessagesComponent } from './component/admin-messages/admin-messages.component';


function initializeKeycloak(keycloak: KeycloakService) {
  return () =>
    keycloak.init({
      config: {
        url: 'http://localhost:8081',
        realm: 'ATB',
        clientId: 'atb'
      },
      initOptions: {
        onLoad: 'check-sso',
        silentCheckSsoRedirectUri:
          window.location.origin + '/assets/silent-check-sso.html'
      }
    });
}

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    UnauthorizedComponent,
    CreditRequestsComponent,
    AddCreditRequestComponent,
    CreditHistoryComponent,
    PaymentsComponent,
    AdminDashboardComponent,
    TepAppComponent,
    RechargeComponent,
    CardLoginComponent,
    CarteProfileComponent,
    SideBarComponent,
    AdminMessagesComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    KeycloakAngularModule,
    ReactiveFormsModule
  ],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: initializeKeycloak,
      multi: true,
      deps: [KeycloakService]
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
