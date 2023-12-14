import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DiscussionRequest } from '../models/DiscussionRequest';
import { Observable } from 'rxjs';
import { Discussion } from '../models/Discussion';
import { Message } from '../models/Message';

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  private pathService: string = "http://localhost:9001";

  constructor(private httpClient:HttpClient) {  }

  public createChatRoom(discussionRequest:DiscussionRequest):Observable<Discussion>{
    return this.httpClient.post<Discussion>(`${this.pathService}/chat`, discussionRequest);
  }

  public getChatRoomById(id:string):Observable<Discussion>{
    return this.httpClient.get<Discussion>(`${this.pathService}/chat/${id}`);
  }

  public getChatRoomList():Observable<Discussion[]>{
    return this.httpClient.get<Discussion[]>(`${this.pathService}/chat/support`);
  }

  public getChatRoomListByClientUserId(id:string):Observable<Discussion[]>{
    return this.httpClient.get<Discussion[]>(`${this.pathService}/chat/user/${id}`);
  }

  public getMessageListByDiscussionId(id:string):Observable<Message[]>{
    return this.httpClient.get<Message[]>(`${this.pathService}/message/${id}`);
  }
}
