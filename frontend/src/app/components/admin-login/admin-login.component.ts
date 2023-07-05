import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService } from 'src/app/services/api/api.service';

/*
    Admin login component provides login functionality
    for the admin, it provides a form for admin login.
    It handles the authentication of admin and show error message for unsuccessful login.
*/
@Component({
  selector: 'app-admin-login',
  templateUrl: './admin-login.component.html',
  styleUrls: ['./admin-login.component.css']
})
export class AdminLoginComponent {
  email: string = '';
  password: string = '';
  emailValid = false;
  emailInValid = false;
  passwordValid = false;
  passwordInValid = false; 
  loginSuccess = false;
  loginUnSuccess = false;
  errorMessage = "Invalid Credentials"
  constructor(private router: Router,private apiService: ApiService){}

  onSubmit(){
     this.emailValid = this.validateEmail(this.email);
     this.emailInValid = !this.emailValid;
     
     this.passwordValid = this.validatePassword(this.password);
     this.passwordInValid = !this.passwordValid;
    
     if(this.emailInValid || this.passwordInValid) return;
     
     this.apiService.adminLogin(this.email,this.password).subscribe({
      next: (response: any) => {
            localStorage.setItem("role","admin");
            localStorage.setItem("token",response.token);
            localStorage.setItem("firstName",response.admin_first_name);
            localStorage.setItem("LastName",response.admin_last_name);
            this.loginSuccess = true;
            this.loginUnSuccess = false;
          
            setTimeout(()=>{
                this.router.navigateByUrl("/admin");
            },400)
      },
      error: (err: any) => {
            if(err.status == 0) this.errorMessage = "Server not responding, try again";
            else this.errorMessage = "Invalid Credentials"
            this.loginUnSuccess = true;
            this.loginSuccess = false;
      }
     })
  }
  
  validateEmail(email: string): boolean{
     const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
     return emailPattern.test(email);
  }

  validatePassword(password: string): boolean{
    return password.length != 0;
  }
}
