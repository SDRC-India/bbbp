import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Observable';

import { AppService } from '../app.service';

@Injectable()
export class AuthGuard implements CanActivate {

  constructor(private app: AppService, private router: Router){}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {

      if(this.app.checkLoggedIn()){
        return true;
      }else{
        this.router.navigate(['/login'])
      }
  }
}
