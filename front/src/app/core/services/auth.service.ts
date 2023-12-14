import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RegisterRequest } from '../models/RegisterRequest';
import { LoginRequest } from '../models/LoginRequest';
import { ClientUser } from '../models/ClientUser';
import { Observable } from 'rxjs';
import { AuthSuccess } from '../models/AuthSuccess';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private pathService: string = "http://localhost:9001/api";

  constructor(private httpClient: HttpClient) { }

  public register(registerRequest: RegisterRequest): Observable<void> {
    return this.httpClient.post<void>(`${this.pathService}/auth/register`, registerRequest);
  }

  public login(loginRequest: LoginRequest, loginType:string): Observable<AuthSuccess> {
    return this.httpClient.post<AuthSuccess>(`${this.pathService}/${loginType}/login`, loginRequest);
  }

  public me(): Observable<ClientUser> {
    return this.httpClient.get<ClientUser>(`${this.pathService}/auth/me`);
  }
}
