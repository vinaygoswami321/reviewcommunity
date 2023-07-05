import { Injectable } from '@angular/core';

/**
 *  The auth service provides methods to check if the user or admin is authenticated or not,
 *  by checking for the authentication token availability
 */
@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor() {}

  isUserLoggedIn(): boolean {
    return (
      localStorage.getItem('role') == 'user' &&
      localStorage.getItem('token') != null
    );
  }

  isAdminLoggedIn(): boolean {
    return (
      localStorage.getItem('role') == 'admin' &&
      localStorage.getItem('token') != null
    );
  }
}
