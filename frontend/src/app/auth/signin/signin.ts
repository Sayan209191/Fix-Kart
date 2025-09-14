import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-signin',
  standalone: true,
  imports: [CommonModule],   // 👈 important for *ngIf, *ngFor, etc.
  templateUrl: './signin.html',
  styleUrls: ['./signin.css']
})
export class SigninComponent {
  onSignin(event: Event) {
    event.preventDefault();
    alert("Logged in successfully!");
  }
}
