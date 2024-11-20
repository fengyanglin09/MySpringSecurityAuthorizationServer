import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly baseUrl: string = `${environment.baseUrl}/api`;

  constructor(private http: HttpClient) { }

  getUserInfo(): Observable<any> {
    return this.http.get(`${this.baseUrl}/user`);
  }

}
