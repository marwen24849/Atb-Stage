import { Component } from '@angular/core';
import { CarteService } from '../../service/carte.service';

@Component({
  selector: 'app-recharge',
  templateUrl: './recharge.component.html',
  styleUrl: './recharge.component.css'
})
export class RechargeComponent {
  numero: string = '';
  montant: number = 0;
  message: string = '';
  carte: any={
    'numero':0,
    'solde':0,
    'motDePasse':'',
    'username':''
  };
  constructor(private carteService: CarteService) { }


  createCarte(){
    if (this.carte.numero && this.carte.solde !== null) {
      this.carteService.createCarte(this.carte).subscribe(
        response => {
          this.message = 'Carte créée avec succès';
          this.carte = { numero: '', solde: 0, motDePasse:'', userId:'' };
        },
        error => {
          alert("User n'existe pas")
          this.message = 'Erreur lors de la création de la carte';
        }
      );
    } else {
      this.message = 'Veuillez remplir tous les champs';
    }
  }

  recharger(): void {
    if (this.numero && this.montant !== null) {
      const body:any ={
        'numero': this.numero,
        'montant': this.montant
      };

      this.carteService.rechargerCarte(body).subscribe(
        response => {
          this.message = response;
          this.numero = '';
          this.montant = 0;
        },
        error => {
          alert('Carte rechargée avec succès')
          this.numero = '';
          this.montant = 0;
          
        }
      );
    } else {
      this.message = 'Veuillez remplir tous les champs';
    }
  }
}