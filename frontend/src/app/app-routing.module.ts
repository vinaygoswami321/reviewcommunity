import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserHomeComponent } from './components/user-home/home.component';
import { ProductSearchComponent } from './components/product-search/product-search.component';
import { UserLoginComponent } from './components/user-login/user-login.component';
import { UserRegisterComponent } from './components/user-register/user-register.component';
import { ReviewComponent } from './components/review/review.component';
import { UserAuthGuard } from './guards/user-auth.guard';
import { AdminAuthGuard } from './guards/admin-auth.guard';
import { AdminLoginComponent } from './components/admin-login/admin-login.component';
import { ApproveReviewComponent } from './components/approve-review/approve-review.component';
import { RequestReviewComponent } from './components/request-review/request-review.component';


const routes: Routes = [
  {path:"",component:UserHomeComponent},
  {path:"admin",component:UserHomeComponent},
  {path:"user/login",component:UserLoginComponent},
  {path: "user/register",component:UserRegisterComponent},
  {path: "product",component:ProductSearchComponent,canActivate:[UserAuthGuard]},
  {path: "reviews",component:ReviewComponent,canActivate:[UserAuthGuard]},
  {path: "request/review",component:RequestReviewComponent,canActivate:[UserAuthGuard]},
  {path: "admin/login",component:AdminLoginComponent},
  {path: "admin/reviews",component:ApproveReviewComponent,canActivate:[AdminAuthGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
