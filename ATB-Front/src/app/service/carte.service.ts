import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CarteService {
  private apiUrl = 'http://localhost:8887/MS-CREDIT/cartes';

  constructor(private http: HttpClient) {}

  getCartes(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  getCarteByNumero(numero: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/${numero}`);
  }

  createCarte(carte: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, carte);
  }

  rechargerCarte(body: any): Observable<string> {
    return this.http.post<string>(this.apiUrl+'/recharger', body);
  }
  
  payer(numero: string, motDePasse: string, montant: number): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/payer`, { numero, motDePasse, montant });
  }
  login(numero: string, password: string):Observable<boolean>{
    const body ={
      'numero': numero,
      'Password': password
    }
    return this.http.post<boolean>(`${this.apiUrl}/login`, body)
  }
}
