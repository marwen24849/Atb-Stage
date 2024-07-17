import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CreditRequestService } from '../../service/credit-request.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-credit-request',
  templateUrl: './add-credit-request.component.html',
  styleUrl: './add-credit-request.component.css'
})
export class AddCreditRequestComponent {

  requestedAmount: number=0;
  interestRate: number=5;
  term: number=0;
  customer_Id: string=sessionStorage.getItem('userId')|| '';

  constructor(private creditRequestService: CreditRequestService, private router: Router) {}

  calculateTotalAmountToRepay(principal: number, annualInterestRate: number, termInMonths: number): number {
    const monthlyInterestRate = annualInterestRate / 100 / 12;
    const onePlusInterest = 1 + monthlyInterestRate;
    const totalAmountToRepay = principal * Math.pow(onePlusInterest, termInMonths);
    return parseFloat(totalAmountToRepay.toFixed(2));
  }

  onSubmit() {
    const totalAmountToRepay = this.calculateTotalAmountToRepay(this.requestedAmount, this.interestRate, this.term);
    const confirmation = confirm(`Le montant total à rembourser est ${totalAmountToRepay}. Voulez-vous continuer ?`);
    
    if (confirmation) {
      const newCreditRequest: any = {
        requestDate: new Date(),
        requestedAmount: this.requestedAmount,
        interestRate: this.interestRate,
        term: this.term,
        customer_Id: this.customer_Id,
        adminComment: ''
      };

      this.creditRequestService.createCreditRequest(newCreditRequest).subscribe(response => {
        console.log('Credit request created successfully', response);
        this.router.navigate(['/Credis-Historique'])
      }, error => {
        console.error('Error creating credit request', error);
      });
    }
  }

}
