import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signin',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './signin.html',
  styleUrls: ['./signin.css']
})
export class SigninComponent {
  constructor(private http: HttpClient, private router: Router) {}

  onSignin(event: Event) {
    event.preventDefault();

    // get form values directly
    const form = event.target as HTMLFormElement;
    const mobile = (form.querySelector('#email') as HTMLInputElement).value;
    const password = (form.querySelector('#password') as HTMLInputElement).value;

    const body = {
      mobileNumber: mobile,
      password: password
    };

    this.http.post('http://localhost:8080/api/auth/signin', body).subscribe({
      next: (res: any) => {
        console.log('Login Success:', res);

        //  backend returns JWT token
        if (res.token) {
          localStorage.setItem('token', res.token);
        }

        alert('Login successful!');
        this.router.navigateByUrl('/');
      },
      error: (err) => {
        console.error('Login failed:', err);
        alert('Login failed! Check console for details.');
      }
    });
  }
}
