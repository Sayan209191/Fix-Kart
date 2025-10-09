import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';

interface Address {
  addressLine1: string;
  addressLine2: string;
  pincode: string;
  city: string;
  state: string;
  landmark: string;
}

interface UserData {
  firstName: string;
  lastName: string;
  emailId: string;
  mobileNumber: string;
  address: Address;
  avatarUrl: string;
}

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './profile.html',
  styleUrls: ['./profile.css']
})
export class ProfileComponent implements OnInit {

  @ViewChild('avatarInput') avatarInputRef!: ElementRef<HTMLInputElement>;
  @ViewChild('avatarImg') avatarImgRef!: ElementRef<HTMLImageElement>;

  userData: UserData = {
    firstName: '',
    lastName: '',
    emailId: '',
    mobileNumber: '',
    avatarUrl: 'https://via.placeholder.com/100',
    address: {
      addressLine1: '',
      addressLine2: '',
      pincode: '',
      city: '',
      state: '',
      landmark: ''
    }
  };

  ngOnInit(): void {
    this.loadUserData();
  }

  // ---------- LOAD DATA ----------
  loadUserData(): void {
    // Simulated API call
    console.log('User data ready for loading...');
  }

  // ---------- UPLOAD NEW AVATAR ----------
  triggerAvatarUpload(): void {
    this.avatarInputRef.nativeElement.click();
  }

  onAvatarChange(event: Event): void {
    const file = (event.target as HTMLInputElement).files?.[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (e) => {
        this.userData.avatarUrl = e.target?.result as string;
        this.avatarImgRef.nativeElement.src = this.userData.avatarUrl;
      };
      reader.readAsDataURL(file);
    }
  }

  // ---------- DELETE AVATAR ----------
  deleteAvatar(): void {
    this.userData.avatarUrl = 'https://via.placeholder.com/100';
    this.avatarImgRef.nativeElement.src = this.userData.avatarUrl;
    this.avatarInputRef.nativeElement.value = '';
  }

  // ---------- SAVE ----------
  saveChanges(): void {
    console.log('Saving user data:', this.userData);
    alert('Profile updated successfully!');
  }
}
