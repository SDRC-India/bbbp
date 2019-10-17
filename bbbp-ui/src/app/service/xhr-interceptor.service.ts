import { HttpErrorResponse, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Cookie } from 'ng2-cookies';
import { Observable } from 'rxjs';
import { of } from 'rxjs/observable/of';
import { catchError } from 'rxjs/operators';

import { Constants } from '../constants';

@Injectable()
export class XhrInterceptorService implements HttpInterceptor{

  // constructor(private sessionCheckService: SessionCheckService) { }
  
  // intercept(req: HttpRequest<any>, next: HttpHandler) {
  //   const xhr = req.clone({
  //     headers: req.headers.set('X-Requested-With', 'XMLHttpRequest')
  //   });
  //   this.sessionCheckService.resetTimer();
  //   return next.handle(xhr);
  // }

  constructor(private router: Router) { }
  
  private handleAuthError(err: HttpErrorResponse): Observable<any> {
    //handle your auth error or rethrow
    if (err.status === 401 || err.status === 403) {
      this.deleteCookies();
        this.router.navigateByUrl('/session-out');
        // if you've caught / handled the error, you don't want to rethrow it unless
        // you also want downstream consumers to have to handle it as well.
        return of(err.message);
    }
    return Observable.throw(err);
  }
 
  intercept(req: HttpRequest<any>, next: HttpHandler) {
    const xhr = req.clone({
      headers: req.headers.set('X-Requested-With', 'XMLHttpRequest')
    });
    // this.sessionCheckService.resetTimer();
    if(xhr.url != Constants.HOME_URL + 'user')
      return next.handle(xhr).pipe(catchError(x=> this.handleAuthError(x)));
    else
      return next.handle(xhr);
  }
  deleteCookies(){
    Cookie.delete('access_token');
    Cookie.delete('JSESSIONID');
    Cookie.delete('XSRF-TOKEN')
  }
}
