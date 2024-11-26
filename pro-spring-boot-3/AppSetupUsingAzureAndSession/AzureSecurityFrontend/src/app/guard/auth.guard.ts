import { CanActivateFn } from '@angular/router';
import {inject} from "@angular/core";
import {AuthService} from "../service/auth.service";
import {catchError, map, of} from "rxjs";

export const authGuard: CanActivateFn = (route, state) => {

  let authService = inject(AuthService);

  //returns an Observable
  return authService.isAuthenticated()
    .pipe(map(value => value==='Authenticated'))
    .pipe(map(isAuthenticated => {
      if(isAuthenticated){
        return true;
      }
      else{
         authService.authenticate();
         return false;
      }
    }))
    .pipe(catchError((err)=>{
      return of(err.messages)
    }))
    ;



};
