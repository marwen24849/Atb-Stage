import { Component, OnInit } from '@angular/core';
import { CreditRequestService } from '../../service/credit-request.service';
import { PaymentService } from '../../service/payment.service';

@Component({
  selector: 'app-credit-history',
  templateUrl: './credit-history.component.html',
  styleUrls: ['./credit-history.component.css'] // Correction de 'styleUrl' à 'styleUrls'
})
export class CreditHistoryComponent implements OnInit {
  creditRequests: any[] = [];
  creditRequestsFiltered: any[] = [];
  paginatedRequests: any[] = [];
  userId: string | null = sessionStorage.getItem('userId');
  selectedCredit: any | null = null;
  selectedRequest: any;

  currentPage = 1;
  itemsPerPage = 5; 
  totalPages = 1;
  totalPagesArray: number[] = [];

  cardNumber: string='';
  password: string='';
  paymentAmount: number=0;

  constructor(private paymentService: PaymentService,private creditRequestService: CreditRequestService) {}

  ngOnInit(): void {
    if (this.userId) {
      this.creditRequestService.getCreditRequestsByCustomerId(this.userId).subscribe(data => {
        this.creditRequests = data;
        this.creditRequestsFiltered = [...this.creditRequests];
        this.setupPagination();
      });
    }
  }

  filterByStatus(status: string): void {
    if (status === '') {
      this.creditRequestsFiltered = [...this.creditRequests];
    } else {
      this.creditRequestsFiltered = this.creditRequests.filter(request => request.status === status);
    }
    this.setupPagination();
  }

  setupPagination(): void {
    this.totalPages = Math.ceil(this.creditRequestsFiltered.length / this.itemsPerPage);
    this.totalPagesArray = Array(this.totalPages).fill(0).map((x, i) => i + 1);
    this.changePage(1);
  }

  changePage(page: number): void {
    if (page < 1 || page > this.totalPages) {
      return;
    }
    this.currentPage = page;
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    this.paginatedRequests = this.creditRequestsFiltered.slice(startIndex, endIndex);
  }

  modal(request: any): void {
    this.selectedRequest = request;
    this.creditRequestService.getCreditByCreditRequestId(request.id).subscribe(data => {
      this.selectedCredit = data;
    });
  }

  makePayment() {
    
    this.paymentService.createPayment(this.selectedCredit.id, this.paymentAmount, this.cardNumber, this.password).subscribe(
      response => {
        alert('Payment successful');
        window.location.reload();
      },
      error => {
        if (error && error.error && error.error.message) {
          alert('Payment failed: ' + error.error.message);
        } else {
          alert('Payment failed: ' + error);
        }
        window.location.reload();
      }
    );
  }    
}
