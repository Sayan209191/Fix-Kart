import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [CommonModule], // ✅ Import CommonModule here
  templateUrl: './signup.html',
  styleUrls: ['./signup.css']
})
export class SignupComponent {
  showOtp = false;

  sendCode() {
    this.showOtp = true;
    alert("Verification code sent to your email!");
  }

  onSignup(event: Event) {
    event.preventDefault();
    alert("Signed up successfully!");
  }
}
