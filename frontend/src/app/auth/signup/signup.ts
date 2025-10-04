import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [CommonModule, RouterLink], // ✅ Import CommonModule here
  templateUrl: './signup.html',
  styleUrls: ['./signup.css']
})
export class SignupComponent {
  constructor(private http: HttpClient, private router: Router) {}
  showOtp = false;

//   sendCode() {
//     this.showOtp = true;
//     alert("Verification code sent to your email!");
//   }
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
    const mobile = (form.querySelector('#mobile') as HTMLInputElement).value;
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
    this.http.post('http://localhost:8080/api/auth/signup', requestBody).subscribe({
      next: (res: any) => {
        console.log('Signup Success:', res);
        alert('Signup successful!');
        this.router.navigate(['/login']);
      },
      error: (err) => {
        console.error('Signup failed:', err);
        alert('Signup failed!');
      }
    });

  }

}
