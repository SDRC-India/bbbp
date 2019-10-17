import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { EmailValidator } from '@angular/forms/src/directives/validators';
import { Router } from '@angular/router';

import { AppService } from './../../app.service';

declare var $: any;

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  credentials: any = {
    username: '',
    password: ''
  }
  @ViewChild('resetPass') public resetPassModal: any;
  @ViewChild('side') public checkEmailModal: any;
  newPassword: any;
  confirmPassword: any;
  form: FormGroup;
  emailForPassReset:EmailValidator;
  
  constructor(private app: AppService, private http: HttpClient, private router: Router) { }

  ngOnInit() {
  }

  login(){
    this.app.authenticate(this.credentials, () => {
      if(this.app.authenticated == true){
        this.router.navigateByUrl('/');
      }
      else{
        $(".error-message").fadeIn("slow");
        setTimeout(function(){
          $(".error-message").fadeOut("slow");
        }, 5000)
      }
    });
    return false;
  }
  checkRegisteredEmail(){
    if(this.emailForPassReset){
      this.resetPassModal.show();
      this.checkEmailModal.hide();
    }
    else{
      alert("wrong")
    }
  }
  

  // getAccessTokenByRefreshToken(){
  //   this._userService.obtainAccessTokenFromRefreshToken()
  // }
  ngAfterViewInit(){
    $("input, textarea").focus(function() {
      $(this).parent().find("> label").css({"color": "#4285F4"})
      
    })
    $("input, textarea").blur(function(){
      $(this).parent().find("> label").css({"color": "#333"})
    })
  }
}
