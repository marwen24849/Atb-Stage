import { Component, OnInit } from '@angular/core';
import { PaymentService } from '../../service/payment.service';

@Component({
  selector: 'app-payments',
  templateUrl: './payments.component.html',
  styleUrl: './payments.component.css'
})
export class PaymentsComponent implements OnInit {
  payments: any[] = [];
  creditId: number=0;
  amount: number=0;

  constructor(private paymentService: PaymentService) {}

  ngOnInit(): void {
    this.loadPayments();
  }

  loadPayments() {
    this.paymentService.getAllPayments().subscribe(
      data => {
        console.log(data)
        this.payments = data;
      },
      error => {
        console.log('Error fetching payments', error);
      }
    );
  }

 
}