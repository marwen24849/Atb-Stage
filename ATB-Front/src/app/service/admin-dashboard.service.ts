import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminDashboardService {

  private baseUrl = 'http://localhost:8887/MS-CREDIT/admin-dashboard';

  constructor(private http: HttpClient) { }

  getStatistics(): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/statistics`);
  }

  getAllCreditRequests(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/credit-requests`);
  }

  getAllCredits(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/credits`);
  }
}
