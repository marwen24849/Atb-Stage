import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';



@Injectable({
  providedIn: 'root'
})
export class CreditRequestService {
  private baseUrl = 'http://localhost:8887/MS-CREDIT/credit-requests';
  private baseUrlCredit = 'http://localhost:8887/MS-CREDIT/credits';

  constructor(private http: HttpClient) { }

  getAllCreditRequests(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}`);
  }
  getAllCreditRequestsbyStatus(status: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/Status/${status}`);
  }

  getCreditRequestById(id: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/${id}`);
  }

  createCreditRequest(creditRequest: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}`, creditRequest);
  }

  approveCreditRequest(id: number): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/${id}/approve`, null);
  }

  rejectCreditRequest(id: number, adminComment: string): Observable<any> {
    const params = new HttpParams().set('adminComment', adminComment);
    return this.http.put<any>(`${this.baseUrl}/${id}/reject`, null, { params });
  }

  getCreditRequestsByCustomerId(customerId: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/customer/${customerId}`);
  }
  getCreditByCreditRequestId(requestId: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrlCredit}/credit-by-request/${requestId}`);
  }
}
