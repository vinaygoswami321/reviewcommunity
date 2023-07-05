import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth/auth.service';

/**
 *  User Auth Guard is a route guard to restrict the access to the user routes,
 *  based on the authentication status of the user, if user is not authenticated 
 *  he/she gets redirected to the login page.
 */
@Injectable({
  providedIn: 'root'
})
export class UserAuthGuard implements CanActivate{

  constructor(private router: Router,private authService: AuthService) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
      if(this.authService.isUserLoggedIn()){
        return true;
      }

      this.router.navigateByUrl('user/login');
      return false;
  }
}
