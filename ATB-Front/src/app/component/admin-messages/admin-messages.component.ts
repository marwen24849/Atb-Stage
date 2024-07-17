// src/app/admin-messages/admin-messages.component.ts
import { Component, OnInit } from '@angular/core';
import { MessageService } from '../../service/message.service';

interface Message {
  name: string;
  email: string;
  content: string;
  date: Date;
}

@Component({
  selector: 'app-admin-messages',
  templateUrl: './admin-messages.component.html',
  styleUrls: ['./admin-messages.component.css']
})
export class AdminMessagesComponent implements OnInit {
  messages: any= [];
  searchTerm: string = '';

  constructor(private messageService: MessageService) {}

  ngOnInit(): void {
    this.messageService.getMessages().subscribe((messages) => {
      this.messages = messages;
    });
  }

  search(): void {
    if (this.searchTerm) {
      this.messages = this.messages.filter((message: { name: string; email: string; content: string; }) => 
        message.name.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        message.email.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        message.content.toLowerCase().includes(this.searchTerm.toLowerCase())
      );
    } else {
      this.messageService.getMessages().subscribe((messages) => {
        this.messages = messages;
      });
    }
  }
}
