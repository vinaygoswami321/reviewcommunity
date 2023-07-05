import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UserHomeComponent } from './components/user-home/home.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { SharedService } from './services/shared/shared.service';
import { ApiService } from './services/api/api.service';
import { AuthInterceptor } from './interceptors/auth.interceptor';
import { NgxUiLoaderModule, NgxUiLoaderConfig } from 'ngx-ui-loader';
import { NavbarComponent } from './components/navbar/navbar.component';
import { ProductSearchComponent } from './components/product-search/product-search.component';
import { UserLoginComponent } from './components/user-login/user-login.component';
import { UserRegisterComponent } from './components/user-register/user-register.component';
import { ReviewComponent } from './components/review/review.component';
import { UserAuthGuard } from './guards/user-auth.guard';
import { AdminAuthGuard } from './guards/admin-auth.guard';
import { AdminLoginComponent } from './components/admin-login/admin-login.component';
import { ApproveReviewComponent } from './components/approve-review/approve-review.component';
import { RequestReviewComponent } from './components/request-review/request-review.component';

const ngxUiLoaderConfig: NgxUiLoaderConfig = {
    fgsColor: 'rgb(48, 157, 123)',
    pbColor: 'rgb(48, 157, 123)',
    overlayColor: 'rgba(255,255,255,1)'
}

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    UserHomeComponent,
    UserLoginComponent,
    UserRegisterComponent,
    ProductSearchComponent,
    ReviewComponent,
    AdminLoginComponent,
    ApproveReviewComponent,
    RequestReviewComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    NgxUiLoaderModule.forRoot(ngxUiLoaderConfig)
  ],
  providers: [
    SharedService,
    ApiService,
    UserAuthGuard,
    AdminAuthGuard,
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor , multi: true},
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
