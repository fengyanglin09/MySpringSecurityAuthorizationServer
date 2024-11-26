import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {catchError, Observable, of, take} from "rxjs";
import {environment} from "../../environments/environment";
import {error} from "@angular/compiler-cli/src/transformers/util";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly baseUrl: string = `${environment.baseUrl}`;

  constructor(private http: HttpClient) { }


  authenticate(){
    window.location.href = `${this.baseUrl}/welcome-page`
  }

  isAuthenticated():Observable<string> {
    return this.http.get(`${this.baseUrl}/auth-status/session-status`, {responseType:"text"})
      .pipe(take(1))
  }

  getUserInfo(): Observable<any> {
    return this.http.get(`${this.baseUrl}/api/user`)
  }

}
