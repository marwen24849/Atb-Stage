import { Component } from '@angular/core';
import { MessageService } from '../service/message.service';

@Component({
  selector: 'app-tep-app',
  templateUrl: './tep-app.component.html',
  styleUrl: './tep-app.component.css'
})
export class TepAppComponent {

  constructor(private serviceMessage: MessageService){}
  name: string='';
  email: string='';
  content: string='';

  envoyer(){
    const body ={
      'fullName': this.name,
      'email': this.email,
      'content': this.content
    }
    this.serviceMessage.sendMessage(body).subscribe(
      res=>{
        alert("Messsage Envoyer");
      }
    );
  }
}
