import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [CommonModule], // ✅ Import CommonModule here
  templateUrl: './signup.html',
  styleUrls: ['./signup.css']
})
export class SignupComponent {
  constructor(private http: HttpClient, private router: Router) {}
  showOtp = false;

  sendCode() {
    this.showOtp = true;
    alert("Verification code sent to your email!");
  }
//   Request Body for signup
//   {
//     "mobileNumber" : "9475317316",
//     "password" : "satya",
//     "confirmPassword" : "satya",
//     "roleId": 2
//   }
  onSignup(event: Event) {
    event.preventDefault();
    // get form values directly
    const form = event.target as HTMLFormElement;
    const mobile = (form.querySelector('#email') as HTMLInputElement).value;
    const password = (form.querySelector('#password') as HTMLInputElement).value;
    const confirmPassword = (form.querySelector('#confirm-password') as HTMLInputElement).value;
    const roleId = 2;

    // Create the request body
    const requestBody = {
      mobileNumber: mobile,
      password: password,
      confirmPassword: confirmPassword,
      roleId: roleId
    };

    // Send the signup request
    this.http.post('/api/signup', requestBody).subscribe(response => {
      console.log('Signup successful:', response);
      this.router.navigate(['/login']);
    }, error => {
      console.error('Signup failed:', error);
    });
  }

}
