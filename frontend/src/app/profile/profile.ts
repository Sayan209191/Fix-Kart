import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';


interface UserProfile {
    userID: number | null;
    firstName: string;
    middleName?: string;
    lastName: string;
    email: string;
    mobile: string;
    altMobile?: string;
    country: string;
    address1: string;
    address2: string;
    state: string;
    city: string;
    zip: string;
    landmark: string;
}

@Component({
  selector: 'app-profile',
  templateUrl: './profile.html',
  styleUrls: ['./profile.css'],
  imports: [CommonModule, FormsModule],
})
export class ProfileComponent implements OnInit {

  user: UserProfile = {
    userID: null,
    firstName: '',
    middleName: '',
    lastName: '',
    email: '',
    mobile: '',
    altMobile: '',
    country: 'India',
    address1: '',
    address2: '',
    state: '',
    city: '',
    zip: '',
    landmark: ''
  };

  isEditing = false;

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.fetchProfile();
  }

  toggleEdit(): void {
    this.isEditing = !this.isEditing;
  }

  fetchProfile(): void {
    if (typeof window !== 'undefined' && localStorage.getItem('token')) {
      const token = localStorage.getItem('token')!;
      const headers = new HttpHeaders({ Authorization: `Bearer ${token}` });

      this.http.get<UserProfile>('http://localhost:8080/api/profile/profile-me', { headers })
        .subscribe({
          next: (res: any) => {
            this.user = {
                userID: res.id || null,
                firstName: res.customerDetails.firstName || '',
                middleName: res.customerDetails.middleName || '',
                lastName: res.customerDetails.lastName || '',
                mobile: res.mobileNumber,
                altMobile: res.customerDetails.alternativeMobileNumber || '',
                email: res.customerDetails.emailId || '',
                address1: res.customerDetails.address.addressLine1 || '',
                address2: res.customerDetails.address.addressLine2 || '',
                city: res.customerDetails.address.city|| '',
                state: res.customerDetails.address.state|| '',
                zip: res.customerDetails.address.pincode|| '',
                landmark: res.customerDetails.address.landmark|| '',
                country: 'India'
      };
          },
          error: err => console.error('Error fetching profile', err)
        });
    }
  }

  saveProfile(): void {
    if (typeof window !== 'undefined' && localStorage.getItem('token')) {
      const token = localStorage.getItem('token')!;
      const headers = new HttpHeaders({ Authorization: `Bearer ${token}` });

      const payload = {
        userID: this.user.userID,
        firstname: this.user.firstName,
        middlename: this.user.middleName,
        lastname: this.user.lastName,
        alternativenumber: this.user.altMobile,
        emailID: this.user.email,
        addressLine1: this.user.address1,
        addressLine2: this.user.address2,
        pincode: this.user.zip,
        city: this.user.city,
        state: this.user.state,
        landmark: this.user.landmark,
        // category: null,
        // subCategory: null,
        // specialization: null
    };

      this.http.post('http://localhost:8080/api/profile/edit-profile', payload, { headers })
        .subscribe({
            next: (res: any) => {
                // console.log('Profile updated successfully', res);
                alert('Profile updated successfully!');
                this.isEditing = false;   // hide Save/Cancel buttons
                this.fetchProfile();       // refresh with updated data
            },
          error: err => console.error('Error saving profile', err)
        });
    }
  }
}
