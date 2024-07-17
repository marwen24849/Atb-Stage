import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {
  private baseUrl = 'http://localhost:8887/MS-CREDIT/payments';

  constructor(private http: HttpClient) { }

  getAllPayments(): Observable<any[]> {
    return this.http.get<any[]>(this.baseUrl);
  }
  
  createPayment(creditId: number, amount: number, cardNumber: string, cardPassword: string): Observable<any> {
    const params = new HttpParams()
      .set('creditId', creditId.toString())
      .set('amount', amount.toString())
      .set('cardNumber', cardNumber.toString())
      .set('cardPassword', cardPassword.toString());

    return this.http.post<any>(this.baseUrl, null, { params });
  }

  getUsername(id: string):Observable<any>{
    return this.http.get(`${this.baseUrl}/${id}`);
  }

  
}