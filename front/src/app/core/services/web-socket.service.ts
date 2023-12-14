import { Injectable } from '@angular/core';
import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs'
import * as $ from 'jquery'

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {

  private stompClient:Stomp.Client | undefined;

  constructor() { }

  public initializeWebSocketConnection(serverUrl:string){
    const ws = new SockJS(serverUrl);
    this.stompClient = Stomp.over(ws);
    let that = this;
    this.stompClient.connect({}, () => {
      that.stompClient!.subscribe("/chat", (msg:any) => {
        if(msg.body){
          $(".chat").append("<div class='message'> "+msg.body+"</div>");
          console.log(msg.body);
        }
      });
    });
  }
}
