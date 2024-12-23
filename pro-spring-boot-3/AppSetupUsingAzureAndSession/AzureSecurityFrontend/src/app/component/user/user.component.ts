import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../service/auth.service";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-user',
  standalone: true,
  imports: [
    NgIf
  ],
  templateUrl: './user.component.html',
  styleUrl: './user.component.scss'
})
export class UserComponent implements OnInit {
  user: any;

  constructor(private authService: AuthService) {}

  ngOnInit() {
    this.authService.getUserInfo().subscribe({
      next: (data) => (this.user = data),
      error: (err) => console.error(err),
    });
  }
}
