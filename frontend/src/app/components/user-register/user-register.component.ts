import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService } from 'src/app/services/api/api.service';

/**
 *  Registration component provides a form for user to create a new account,
 *  it handles validation of information provided by user before submitting 
 *  the request to the backend
 */
@Component({
  selector: 'app-user-register',
  templateUrl: './user-register.component.html',
  styleUrls: ['./user-register.component.css'],
})
export class UserRegisterComponent {
  email: string = '';
  firstName: string = '';
  lastName: string = '';
  password: string = '';
  confirmPassword: string = '';
  emailValid = false;
  emailInValid = false;
  passwordValid = false;
  passwordInValid = false;
  firstNameValid = false;
  firstNameInValid = false;
  lastNameValid = false;
  lastNameInValid = false;
  confirmPasswordValid = false;
  confirmPasswordInValid = false;
  userRegisterSuccess = false;
  userRegisterUnSuccess = false;
  errorMessage = 'Users Already Registered';
  constructor(private router: Router, private apiService: ApiService) {}

  navigateToLogin() {
    this.router.navigateByUrl('user/login');
  }

  onSubmit() {
    this.emailValid = this.validateEmail(this.email);
    this.emailInValid = !this.emailValid;
    this.firstNameValid = this.validateFirstName(this.firstName);
    this.firstNameInValid = !this.firstNameValid;
    this.lastNameValid = this.validateLastName(this.lastName);
    this.lastNameInValid = !this.lastNameValid;
    this.passwordValid = this.validatePassword(this.password);
    this.passwordInValid = !this.passwordValid;
    this.confirmPasswordValid = this.validateConfirmPassword(
      this.confirmPassword
    );
    this.confirmPasswordInValid = !this.confirmPasswordValid;

    if (this.isInvalid()) return;

    this.apiService
      .userRegister(this.email, this.firstName, this.lastName, this.password)
      .subscribe({
        next: (response: any) => {
          this.userRegisterSuccess = true;
        },
        error: (err: any) => {
          if (err.status == 0)
            this.errorMessage = 'Server not responding, try again';
          else this.errorMessage = 'Users Already Registered';

          this.userRegisterUnSuccess = true;
        },
      });

    setTimeout(() => {
      this.router.navigateByUrl('user/login');
    }, 1000);
  }

  validateEmail(email: string): boolean {
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailPattern.test(email);
  }

  validateFirstName(firstName: string): boolean {
    const firstNamePattern = /^[a-zA-Z]+$/;
    return firstNamePattern.test(firstName) && firstName.length < 20;
  }

  validateLastName(lastName: string): boolean {
    const lastNamePattern = /^[a-zA-Z]+$/;
    return lastNamePattern.test(lastName) && lastName.length < 30;
  }

  validatePassword(password: string): boolean {
    const passwordPattern =
      /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
    return passwordPattern.test(password);
  }

  validateConfirmPassword(confirmPassword: string): boolean {
    return confirmPassword === this.password && confirmPassword.length != 0;
  }

  isInvalid(): boolean {
    return (
      this.confirmPasswordInValid ||
      this.emailInValid ||
      this.firstNameInValid ||
      this.lastNameInValid ||
      this.passwordInValid
    );
  }
}
