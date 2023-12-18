import { Component, OnDestroy, OnInit } from '@angular/core';
import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs'
import * as $ from 'jquery'
import { ActivatedRoute } from '@angular/router';
import { WsMessage } from 'src/app/core/models/WsMessage';
import { ChatService } from 'src/app/core/services/chat.service';
import { Observable, take } from 'rxjs';
import { Message } from 'src/app/core/models/Message';
import { SessionService } from 'src/app/core/services/session.service';
import { Discussion } from 'src/app/core/models/Discussion';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit, OnDestroy {

  private chatRoomId:string = this.route.snapshot.params["id"];
  public chatRoom$:Observable<Discussion> = this.chatService.getChatRoomById(this.chatRoomId)
  private serverUrl:string = `http://localhost:9001/socket`
  
  private stompClient:Stomp.Client | undefined;
  private wsMessage:WsMessage = {
    discussionId:0,
    clientUserId:0,
    supportUserId:0,
    message:""
  };
  private ws!:WebSocket;
  public messages:Message[]=[];

  constructor(private route:ActivatedRoute,
              private chatService:ChatService,
              private sessionService:SessionService){
    this.initializeWebSocketConnection();
  }

  ngOnInit(): void {
    this.chatService.getMessageListByDiscussionId(`${this.chatRoomId}`).pipe(take(1)).subscribe((x:Message[])=> this.messages=x)
  }

  public back() {
    window.history.back();
  }
  

  public initializeWebSocketConnection(){
    this.ws = new SockJS(this.serverUrl);
    this.stompClient = Stomp.over(this.ws);
    let that = this;
    let headers = {
      Authorization : this.sessionService.sessionInformation!.type + this.sessionService.sessionInformation!.token
    }
    this.stompClient.connect(headers, () => {
      that.stompClient!.subscribe(`/ws/send/${this.chatRoomId}`, (msg:any) => {
        console.log("stompClient connected")
        if(msg.body){
          const message:Message = JSON.parse(msg.body);
          this.messages.push(message);
        }
        console.log(msg.body);
      });
    });
    
  }

  sendMessage(message:string){
    if(this.sessionService.sessionInformation!.authorities[0].authority === "SUPPORT"){
      this.wsMessage.supportUserId = this.sessionService.sessionInformation!.id;
    }
    if(this.sessionService.sessionInformation!.authorities[0].authority === "USER"){
      this.wsMessage.clientUserId = this.sessionService.sessionInformation!.id;
    }
    this.wsMessage.discussionId = +this.chatRoomId;
    this.wsMessage.message = message;
    console.log("sended");

    this.stompClient!.send(`/send/${this.chatRoomId}`, {},  JSON.stringify(this.wsMessage));
    $("#input").val("");
  }

  ngOnDestroy(): void {
    this.stompClient?.disconnect(() => {
      this.stompClient?.ws.close();
      this.stompClient = undefined;
    });
    this.ws.close();
  }
  public isOwnMessage(message:Message):string{
    if((message.clientUserId === this.sessionService.sessionInformation!.id && this.sessionService.sessionInformation!.authorities[0].authority === "USER") || (message.supportUserId === this.sessionService.sessionInformation!.id && this.sessionService.sessionInformation!.authorities[0].authority === "SUPPORT")){
      return "right";
    }
    return "left";
  }
}
