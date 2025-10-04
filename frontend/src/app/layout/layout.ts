import { Component, OnInit  } from '@angular/core';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink],
  templateUrl: './layout.html',
  styleUrls: ['./layout.css']
})
export class LayoutComponent {
    constructor(private http: HttpClient, private router: Router) {}
    loginButtonText: string = 'Login';  // keep if you still want to show text dynamically
    isLoggedIn = false;

    ngOnInit() {
        try {
            if (typeof window !== 'undefined' && typeof localStorage !== 'undefined') {
                const token = localStorage.getItem('token');
                if (token && token.length > 0) {
                    this.loginButtonText = 'Profile';
                    this.isLoggedIn = true;
                }
            }
        } catch (error) {
            console.error(error);
        }

    }

    logout() {
        try{
            const token = localStorage.getItem('token');

            if (!token) {
                return; // no token → just return
            }

            this.http.post('http://localhost:8080/api/auth/logout', {}, {
                headers: { Authorization: `Bearer ${token}` },
                responseType: 'text'
            }).subscribe({
                next: (res: any) => {
                    // Clear token
                    localStorage.removeItem('token');
                    this.isLoggedIn = false;
                    this.loginButtonText = 'Login';
                    alert(res);

                    // Redirect to home
                    this.router.navigate(['/']);
                },
                error: (err) => {
                    alert('Logout failed ');
                    console.error(err);
                }
            });
        }
        catch(error){
            console.error(error);
        }

    }
}
