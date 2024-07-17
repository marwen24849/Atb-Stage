import { Component } from '@angular/core';
import { AuthService } from '../../service/auth.service';
import { Router } from '@angular/router';
import { CarteService } from '../../service/carte.service';

@Component({
  selector: 'app-card-login',
  templateUrl: './card-login.component.html',
  styleUrl: './card-login.component.css'
})
export class CardLoginComponent {

  cardNumber: string = '';
  password: string = '';

  constructor(private authService: AuthService,private carteService: CarteService, private router: Router) {}

  onSubmit(): void {
    this.carteService.login(this.cardNumber, this.password).subscribe(
      res=>{
        this.authService.authenticate(res);
        if (res) {
          this.router.navigate(['/profileCarte/',this.cardNumber]);
        }else{
          alert('mot de passe incorrect.');
        }
      },
      err=>{
        alert('Numéro de carte incorrect.');
      }
    );

   
  }



}
