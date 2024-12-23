import { Routes } from '@angular/router';
import {UserComponent} from "./component/user/user.component";
import {authGuard} from "./guard/auth.guard";

export const routes: Routes = [
  {path: '',
    canActivate: [authGuard],
    component: UserComponent
  }
];
