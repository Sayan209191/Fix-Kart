import { Routes } from '@angular/router';
import { LayoutComponent } from './layout/layout';
import { HomeComponent } from './home/home';
import { SigninComponent } from './auth/signin/signin';
import { SignupComponent } from './auth/signup/signup';
import { ProfileComponent } from './profile/profile';

export const routes: Routes = [
  {
    path: '',
    component: LayoutComponent,   // parent layout
    children: [
      { path: '', component: HomeComponent },
      { path: 'signin', component: SigninComponent },
      { path: 'signup', component: SignupComponent },
      { path: 'profile', component: ProfileComponent },
      // add other routes like services, products, etc.
    ]
  }
];
