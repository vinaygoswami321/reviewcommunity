import { Injectable } from "@angular/core";
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from "@angular/common/http";
import { Observable } from "rxjs";

/**
 *  The auth interceptor intercepts the outgoing http request and checks for the availability 
 *  of token and if a token is found it clones the outgoing http request and add an
 *  "Authorization" header to the request.
 */
@Injectable()
export class AuthInterceptor implements HttpInterceptor{
    
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
          const token = localStorage.getItem('token');
          if(token != null){
                const request = req.clone({
                    setHeaders:{
                        Authorization : `Bearer ${token}`
                    }
                });

                return next.handle(request);
          }
          return next.handle(req);
    }
}
