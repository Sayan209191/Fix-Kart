import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Console } from 'console';

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
  imagePath?: string;
}

@Component({
  selector: 'app-profile',
  templateUrl: './profile.html',
  styleUrls: ['./profile.css'],
  imports: [CommonModule, FormsModule],
})
export class ProfileComponent implements OnInit {
    @ViewChild('avatarUpload', { static: false }) fileInputRef!: ElementRef<HTMLInputElement>;

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
        landmark: '',
        imagePath: ''
    };

    isEditing = false;
    selectedFile: File | null = null;
    avatarPreview: string = 'https://via.placeholder.com/100';

    constructor(private http: HttpClient) {}

    ngOnInit(): void {
        this.fetchProfile();
    }

    toggleEdit(): void {
        this.isEditing = !this.isEditing;
    }

    // Open file selector (fixes your previous error)
    triggerFileInput(): void {
        if (this.fileInputRef && this.fileInputRef.nativeElement) {
            this.fileInputRef.nativeElement.click();
        }
    }

  // Fetch profile
  fetchProfile(): void {
    try {
        if (typeof window !== 'undefined' && typeof localStorage !== 'undefined') {
            const token = localStorage.getItem('token');
            if (!token) return;

            const headers = new HttpHeaders({ Authorization: `Bearer ${token}` });

            this.http.get<any>('http://localhost:8080/api/profile/profile-me', { headers })
                .subscribe({
                    next: (res) => {
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
                            city: res.customerDetails.address.city || '',
                            state: res.customerDetails.address.state || '',
                            zip: res.customerDetails.address.pincode || '',
                            landmark: res.customerDetails.address.landmark || '',
                            country: 'India',
                            imagePath: res.customerDetails.user.imagePath || ''
                        };
                        this.avatarPreview = this.user.imagePath
                            ? `http://localhost:8080${this.user.imagePath}?t=${new Date().getTime()}`
                            : 'https://via.placeholder.com/100';
                        },
                    error: (err) => console.error('Error fetching profile', err),
                });
        }

    } catch (error) {
        console.error('Error fetching profile', error);
    }

    }

    saveProfile(): void {
        try {
            if (typeof window !== 'undefined' && typeof localStorage !== 'undefined') {
                const token = localStorage.getItem('token');
                if (!token) return;
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
                };
                this.http.post('http://localhost:8080/api/profile/edit-profile', payload, { headers })
                    .subscribe({
                    next: () => {
                    alert('Profile updated successfully!');
                    this.isEditing = false;
                    this.fetchProfile();
                    },
                    error: (err) => console.error('Error saving profile', err),
                });
            }
        } catch (error) {
            console.error('Error saving profile', error);
        }


    }

  // Handle file selection + preview
    onFileSelected(event: any): void {
        try {
            const file = event.target.files[0];
            if (file) {
                this.selectedFile = file;

                const reader = new FileReader();
                reader.onload = (e: any) => {
                    this.avatarPreview = e.target.result;
                };
                reader.readAsDataURL(file);


                this.uploadAvatar();
            }
        } catch (error) {
            console.error('Error saving profile', error);
        }

    }

  // Upload profile picture
    uploadAvatar(): void {
        try {
            if (!this.selectedFile || !this.user.userID) return;
            if (typeof window !== 'undefined' && typeof localStorage !== 'undefined') {
                const token = localStorage.getItem('token');
                if (!token) return;

                const formData = new FormData();
                formData.append('image', this.selectedFile);

                const headers = new HttpHeaders({ Authorization: `Bearer ${token}` });

                this.http.post(`http://localhost:8080/api/profile/upload-image`, formData, { headers })
                .subscribe({
                    next: () => {
                        alert('Profile picture uploaded successfully!');
                        this.fetchProfile();
                    },
                    error: (err) => console.error('Error uploading avatar', err),
                });
            }

        } catch (error) {
            console.error('Error uploading avatar', error);
        }
    }

    //  Delete profile picture
    deleteAvatar(): void {
        try{
            if (!this.user.userID) return;
            if (typeof window !== 'undefined' && typeof localStorage !== 'undefined') {
                const token = localStorage.getItem('token');
                if (!token) return;

                const headers = new HttpHeaders({ Authorization: `Bearer ${token}` });

                this.http.delete(`http://localhost:8080/api/profile/delete-image`, { headers, responseType: 'text' })
                .subscribe({
                    next: () => {
                        alert('Profile picture deleted successfully!');
                        // this.avatarPreview = 'https://via.placeholder.com/100';
                        // this.user.imagePath = '';
                        this.fetchProfile();
                    },
                    error: (err) => console.error('Error deleting avatar', err),
                });
            }
        }
        catch(error){
            console.error('Error deleting avatar', error);
        }

    }
}
