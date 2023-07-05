import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth/auth.service';
import { SharedService } from 'src/app/services/shared/shared.service';

/*
    Navbar component shows the different navigation links
    to choose from.
*/
@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent {
  isMenuOpen = false;
  userName: string = '';

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  constructor(private sharedService: SharedService,private authService: AuthService,private router: Router) {}

  isUserLoggedIn() {
    if (this.authService.isUserLoggedIn()) {
      this.userName = localStorage.getItem('firstName') ?? '';
      return true;
    }
    return false;
  }

  isAdminLoggedIn() {
    if (this.authService.isAdminLoggedIn()) {
      this.userName = localStorage.getItem('firstName') ?? '';
      return true;
    }
    return false;
  }

  logout() {
    this.sharedService.logout(this.isAdminLoggedIn());
  }

  isAdminRoute(): boolean{
    return this.router.url.includes("/admin");
  }
}
