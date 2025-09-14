import { Component, OnInit, OnDestroy, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  templateUrl: './home.html',
  styleUrls: ['./home.css']
})
export class HomeComponent implements OnInit, OnDestroy {
  private removeListeners: (() => void)[] = [];
  isLoggedIn = false; // track login state
  loginButtonText = 'Login ⌄';
Account: any;

  constructor(
    @Inject(PLATFORM_ID) private platformId: Object,
    private router: Router
  ) {}

  ngOnInit(): void {
    if (isPlatformBrowser(this.platformId)) {
      const searchForm = document.querySelector('.search-form') as HTMLElement;
      const shoppingCart = document.querySelector('.shopping-cart') as HTMLElement;
      const loginForm = document.querySelector('.login-form') as HTMLElement;
      const navbar = document.querySelector('.navbar') as HTMLElement;

      const addClickListener = (selector: string, callback: () => void) => {
        const el = document.querySelector(selector);
        if (el) {
          el.addEventListener('click', callback);
          this.removeListeners.push(() => el.removeEventListener('click', callback));
        }
      };

      // Toggle handlers
      addClickListener('#search-btn', () => {
        searchForm?.classList.toggle('active');
        shoppingCart?.classList.remove('active');
        loginForm?.classList.remove('active');
        navbar?.classList.remove('active');
      });

      addClickListener('#cart-btn', () => {
        shoppingCart?.classList.toggle('active');
        searchForm?.classList.remove('active');
        loginForm?.classList.remove('active');
        navbar?.classList.remove('active');
      });

      addClickListener('#login-btn', () => {
        if (!this.isLoggedIn) {
          this.openLoginPage();
        } else {
          this.openProfilePage();
        }
        searchForm?.classList.remove('active');
        shoppingCart?.classList.remove('active');
        navbar?.classList.remove('active');
      });

      addClickListener('#menu-btn', () => {
        navbar?.classList.toggle('active');
        searchForm?.classList.remove('active');
        shoppingCart?.classList.remove('active');
        loginForm?.classList.remove('active');
      });

      const onScroll = () => {
        searchForm?.classList.remove('active');
        shoppingCart?.classList.remove('active');
        loginForm?.classList.remove('active');
        navbar?.classList.remove('active');
      };

      window.addEventListener('scroll', onScroll);
      this.removeListeners.push(() => window.removeEventListener('scroll', onScroll));

      // ✅ Import Swiper dynamically
      import('swiper').then(({ default: Swiper }) => {
        import('swiper/modules').then(({ Autoplay, Navigation, Pagination }) => {
          new Swiper('.product-slider', {
            modules: [Autoplay, Navigation, Pagination],
            loop: true,
            spaceBetween: 20,
            autoplay: { delay: 3000, disableOnInteraction: false },
            breakpoints: {
              0: { slidesPerView: 1 },
              768: { slidesPerView: 2 },
              1024: { slidesPerView: 3 }
            }
          });

          new Swiper('.review-slider', {
            modules: [Autoplay, Navigation, Pagination],
            loop: true,
            spaceBetween: 20,
            autoplay: { delay: 3000, disableOnInteraction: false },
            breakpoints: {
              0: { slidesPerView: 1 },
              768: { slidesPerView: 2 },
              1024: { slidesPerView: 3 }
            }
          });
        });
      });
    }
  }

  // --- Navigation Methods ---
  openSignUpPage(): void {
    this.router.navigate(['/signup']);
  }

  openLoginPage(): void {
    this.router.navigate(['/signin']);
  }

  openProfilePage(): void {
    this.router.navigate(['/profile']);
  }

  updateLoginState(): void {
    this.loginButtonText = this.isLoggedIn ? 'My Profile ⌄' : 'Login ⌄';
  }

  ngOnDestroy(): void {
    this.removeListeners.forEach(fn => fn());
  }
}
