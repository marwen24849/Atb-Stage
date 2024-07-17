import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './monotoring/dashboard/dashboard.component';
import { authGuard } from './auth.guard';
import { UnauthorizedComponent } from './component/unauthorized/unauthorized.component';
import { CreditRequestsComponent } from './component/credit-requests/credit-requests.component';
import { AddCreditRequestComponent } from './component/add-credit-request/add-credit-request.component';
import { CreditHistoryComponent } from './component/credit-history/credit-history.component';
import { PaymentsComponent } from './component/payments/payments.component';
import { AdminDashboardComponent } from './component/admin-dashboard/admin-dashboard.component';
import { TepAppComponent } from './tep-app/tep-app.component';
import { RechargeComponent } from './component/recharge/recharge.component';
import { authentificationGuard } from './authentification.guard';
import { CardLoginComponent } from './component/card-login/card-login.component';
import { CarteProfileComponent } from './component/carte-profile/carte-profile.component';
import { SideBarComponent } from './component/side-bar/side-bar.component';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'traceHttp', component: DashboardComponent, canActivate: [authGuard], data: { roles: ['admin'] } },
  { path: 'Credis', component: CreditRequestsComponent, canActivate: [authGuard], data: { roles: ['user', 'admin'] } },
  { path: 'Create-Credit', component: AddCreditRequestComponent, canActivate: [authGuard], data: { roles: ['user', 'admin'] } },
  { path: 'Credis-Historique', component: CreditHistoryComponent, canActivate: [authGuard], data: { roles: ['user', 'admin'] } },
  { path: 'Payment', component: PaymentsComponent, canActivate: [authGuard], data: { roles: ['admin'] } },
  { path: 'home', component: TepAppComponent },
  { path: 'Gestion-Carte', component: RechargeComponent, canActivate: [authGuard], data: { roles: ['admin'] } },
  { path: 'dashAdmin', component: AdminDashboardComponent, canActivate: [authGuard], data: { roles: ['admin'] } },
  { path: 'profileCarte/:numero', component: CarteProfileComponent, canActivate: [authentificationGuard], data: { roles: ['user'] } },
  { path: 'login-Carte', component: CardLoginComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
