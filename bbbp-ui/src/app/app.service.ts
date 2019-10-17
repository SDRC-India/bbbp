import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Cookie } from 'ng2-cookies';
import { error } from 'selenium-webdriver';

import { Constants } from './constants';

@Injectable()
export class AppService {
    userName: string;
    authenticated = false;
    logoutSuccess:boolean = false;
    constructor(private http: HttpClient, private router: Router) {
        console.log(Cookie.get('access_token'));
        // this.userIdle.onTimeout().subscribe(() => console.log('Time is up!'));
    }

    
    authenticate(credentials, callback) {

        const headers = new HttpHeaders(credentials ? {
            authorization : 'Basic ' + btoa(credentials.username + ':' + credentials.password)
        } : {});

        this.http.get(Constants.HOME_URL + 'user', {headers: headers}).subscribe(response => {
            if (response['name']) {
                this.authenticated = true;
            } else {
                this.authenticated = false;
            }
            this.saveToken(response);
            return callback && callback();
        },
        error => {
            if(error.status == 401){
                this.authenticated = false;
                //console.log("Your session has expired. Please login again...");
                return callback && callback();
            }
        }
    );

    }


    checkLoggedIn() : boolean{
        if (!Cookie.check('access_token')){
            return false
        }else{
          return true
        }
    }
    

    logout() {
            
        this.http.post(Constants.HOME_URL + 'logout',{}).subscribe(response =>{
            this.deleteCookies()
            this.router.navigate(['/']);
            this.logoutSuccess = true;
            setTimeout(()=>{
                this.logoutSuccess = false;
            },2000)           
        }, eror => {
            this.deleteCookies()
        })
    }

    deleteCookies(){
        Cookie.delete('access_token');
            Cookie.delete('JSESSIONID');
            Cookie.delete('XSRF-TOKEN');
    }


    saveToken(token){
        var expireDate = new Date().getTime();
        let date = new Date(expireDate);
    
        Cookie.set("access_token", JSON.stringify(token), 30);
        this.router.navigate(['/']);
    }
    getToken(): any{
        return Cookie.get("access_token");
    }

}
