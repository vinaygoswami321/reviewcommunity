import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { AuthService } from '../services/auth/auth.service';
import { Observable } from 'rxjs';

/**
 *  Admin Auth Guard is a route guard to restrict the access to the admin routes,
 *  based on the authentication status of the admin, if admin is not authenticated 
 *  he/she gets redirected to the login page.
 */
@Injectable({
  providedIn: 'root'
})
export class AdminAuthGuard {
  constructor(private router: Router,private authService: AuthService) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
      if(this.authService.isAdminLoggedIn()){
        return true;
      }

      this.router.navigateByUrl('admin/login');
      return false;
  }
}
