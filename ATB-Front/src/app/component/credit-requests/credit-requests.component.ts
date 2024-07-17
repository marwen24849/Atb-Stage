import { Component, OnInit } from '@angular/core';
import { CreditRequestService } from '../../service/credit-request.service';

@Component({
  selector: 'app-credit-requests',
  templateUrl: './credit-requests.component.html',
  styleUrls: ['./credit-requests.component.css'] // Correction de 'styleUrl' à 'styleUrls'
})
export class CreditRequestsComponent implements OnInit {
  creditRequests: any[] = [];
  filteredRequests: any[] = [];
  paginatedRequests: any[] = [];
  commentaire: string = '';
  userIdFilter: string = '';
  currentPage = 1;
  itemsPerPage = 5;
  totalPages = 1;
  totalPagesArray: number[] = [];

  constructor(private creditRequestService: CreditRequestService) { }

  ngOnInit(): void {
    this.loadCreditRequests();
  }

  loadCreditRequests(): void {
    this.creditRequestService.getAllCreditRequestsbyStatus('Submitted').subscribe((data: any[]) => {
      this.creditRequests = data;
      this.applyFilters();
    });
  }

  filterByUserId(): void {
    this.applyFilters();
  }

  applyFilters(): void {
    if (this.userIdFilter) {
      this.filteredRequests = this.creditRequests.filter(request => request.customer_Id.includes(this.userIdFilter));
    } else {
      this.filteredRequests = [...this.creditRequests];
    }
    this.setupPagination();
  }

  setupPagination(): void {
    this.totalPages = Math.ceil(this.filteredRequests.length / this.itemsPerPage);
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
    this.paginatedRequests = this.filteredRequests.slice(startIndex, endIndex);
  }

  calculateTotalAmountToRepay(principal: number, annualInterestRate: number, termInMonths: number): number {
    const monthlyInterestRate = annualInterestRate / 100 / 12;
    const onePlusInterest = 1 + monthlyInterestRate;
    const totalAmountToRepay = principal * Math.pow(onePlusInterest, termInMonths);
    return parseFloat(totalAmountToRepay.toFixed(2));
  }

  selectedRequest: any = [];

  showDetails(request: any): void {
    this.selectedRequest = request;
  }

  approveRequest(id: any): void {
    this.creditRequestService.approveCreditRequest(id).subscribe(
      res => {
        window.location.reload();
      },
      err => {
        console.log(err);
      }
    );
  }

  rejectRequest(id: any): void {
    this.creditRequestService.rejectCreditRequest(id, this.commentaire).subscribe(
      res => {
        window.location.reload();
      },
      err => {
        console.log(err);
      }
    );
  }
}
