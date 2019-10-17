import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router } from '@angular/router';
import { Cookie } from 'ng2-cookies';
import { Observable } from 'rxjs/Observable';

import { AppService } from '../app.service';

@Injectable()
export class RoleGuardService implements CanActivate {

  constructor(private app: AppService, private router: Router) { }
  canActivate(route: ActivatedRouteSnapshot): Observable<boolean> | Promise<boolean> | boolean {

      // this will be passed from the route config
    // on the data property
    const expectedRoles = route.data.expectedRoles;
    if(this.app.checkLoggedIn()){
      const token = JSON.parse(Cookie.get('access_token'));
      let flag = false;
      expectedRoles.forEach(expectedRole => {
        if (token.roles[0] == expectedRole) {
          flag = true;
        }
        
      });
      if(!flag)
        this.router.navigate(['/exception']);
      return flag;
    }
    else{
      this.router.navigate(['/exception']);
      return false;
    }
  }
}
