import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Discussion } from 'src/app/core/models/Discussion';
import { DiscussionRequest } from 'src/app/core/models/DiscussionRequest';
import { ChatService } from 'src/app/core/services/chat.service';
import { SessionService } from 'src/app/core/services/session.service';

@Component({
  selector: 'app-create-discussion',
  templateUrl: './create-discussion.component.html',
  styleUrls: ['./create-discussion.component.css']
})
export class CreateDiscussionComponent {

  public onError:boolean = false;
  public form:FormGroup = this.fb.group({
    subjectPart1: [null, Validators.min(3)],
  });

  private discussionRequest:DiscussionRequest={
    subject:'',
    clientUserId:0,
    rentalId:0
  };

  constructor(private chatService:ChatService,
              private router:Router,
              private fb:FormBuilder,
              private sessionService:SessionService
        ){}
  
  public submit(): void {
    console.log(this.sessionService.sessionInformation?.id);
    console.log(this.form.controls['subjectPart1'].value);
    this.discussionRequest.subject = this.form.controls['subjectPart1'].value as string;
    
    this.discussionRequest.clientUserId = this.sessionService.sessionInformation!.id
    this.chatService.createChatRoom(this.discussionRequest).subscribe({
      next: (res: Discussion) => {
        console.log("chatroom created");
        this.router.navigate(['/chat', res.id]);
      },
      error: error => this.onError = true
    });
  }
}
