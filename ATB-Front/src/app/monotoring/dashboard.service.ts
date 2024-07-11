import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  private baseUrl = 'http://localhost:8887/actuator';
  
  constructor(private http:HttpClient) { }

  getHttpTraces(){
    return this.http.get(`${this.baseUrl}/httpexchanges`);
  }

  getSystemHealth(){
    return this.http.get(`${this.baseUrl}/health`);
  }

  getSystemCPU(){
    return this.http.get(`${this.baseUrl}/metrics/system.cpu.count`);
  }

  getProcessUptime(){
    return this.http.get(`${this.baseUrl}/metrics/process.uptime`);
  } 
}
