import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Discussion } from 'src/app/core/models/Discussion';
import { ChatService } from 'src/app/core/services/chat.service';
import { SessionService } from 'src/app/core/services/session.service';

@Component({
  selector: 'app-discussion',
  templateUrl: './discussion.component.html',
  styleUrls: ['./discussion.component.css']
})
export class DiscussionComponent implements OnInit {

  public chatRooms$:Observable<Discussion[]>=this.chatService.getChatRoomList();
  public userAuthority:string = this.sessionService.sessionInformation!.authorities[0].authority;

  constructor(
    private sessionService:SessionService,
    private router:Router,
    private chatService:ChatService,
  ){}

  ngOnInit(): void {
      if(this.userAuthority==="USER"){
        this.chatRooms$ = this.chatService.getChatRoomListByClientUserId(this.sessionService.sessionInformation!.id.toString())
      }
  }

  public loadDiscussion(id:number){
    this.router.navigate(["/chat", id]);
  }
}
