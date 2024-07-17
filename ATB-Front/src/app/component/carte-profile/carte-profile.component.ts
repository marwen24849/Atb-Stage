import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CarteService } from '../../service/carte.service';
import { PaymentService } from '../../service/payment.service';

@Component({
  selector: 'app-carte-profile',
  templateUrl: './carte-profile.component.html',
  styleUrl: './carte-profile.component.css'
})
export class CarteProfileComponent {
  card: any;
  userName: string = '';

  constructor(private route: ActivatedRoute, private cardService: CarteService, private payService: PaymentService) {}

  ngOnInit(): void {
    const cardNumber = this.route.snapshot.paramMap.get('numero');
    if (cardNumber) {
      this.cardService.getCarteByNumero(cardNumber).subscribe(data => {
        this.card = data;
        if(this.card){
          this.payService.getUsername(this.card.userId).subscribe(
            res=>{
              this.userName=res.username;
            }
          );
        }
      });
    }
  }
}
