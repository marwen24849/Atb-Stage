// src/app/message.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

interface Message {
  fullName: string;
  email: string;
  content: string;
}

@Injectable({
  providedIn: 'root'
})
export class MessageService {
  private apiUrl = 'http://localhost:8887/MS-MESSAGE/api/messages'; 

  constructor(private http: HttpClient) {}

  sendMessage(message: Message): Observable<any> {
    return this.http.post(this.apiUrl, message);
  }

  getMessages(): Observable<Message[]> {
    return this.http.get<Message[]>(this.apiUrl);
  }
}
