import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { FormModel } from '../../../interface/form.model';
import { FormControlService } from '../../../service/form-control.service';
import { FormFieldsService } from '../../../service/form-fields.service';
import { HttpClient } from '@angular/common/http';
import { Cookie } from 'ng2-cookies';
import { AppService } from '../../../app.service'
import { log } from 'util';
import { Constants } from '../../../constants';
declare var $: any;

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss']
})
export class ChangePasswordComponent implements OnInit {
  form: FormGroup;
  formFields: any;
  formFieldsAll: any;
  payLoad = '';
  userName: string;
  validationMsg: any;
  user: UserPass = {
    username: '',
    oldPassword: '',
    password: '',
    confirmPassword: ''
  }

  constructor(private formControlService: FormControlService, private formFieldService: FormFieldsService, private http: HttpClient, private appService: AppService) { }

  ngOnInit() {
    if(Cookie.check('access_token')){
      var token = JSON.parse(Cookie.get('access_token'));
      this.userName = token.username;
    }    
  }
  
  changePassword(userName: string, user){  

    let passDetails = {
      'userName' : userName,
      'oldPassword': this.user.oldPassword,
      'newPassword': this.user.password,
      'confirmPassword': this.user.confirmPassword
    };   
   
    if(this.user.oldPassword === "" || this.user.oldPassword === undefined){
      document.getElementById('oldPasserror').innerHTML  = "Please enter old password."; 
    } else if(this.user.password === "" || this.user.password === undefined){
      document.getElementById('newPasserror').innerHTML  = "Please enter password."; 
    } else if(this.user.oldPassword == this.user.password){
			document.getElementById('newPasserror').innerHTML  = "New password should not be same as old password.";
		} else if(this.user.confirmPassword === "" || this.user.confirmPassword === undefined){
      document.getElementById('confirmPasserror').innerHTML  = "Please enter confirm password."; 
    } else if(this.user.confirmPassword === this.user.password){
      document.getElementById('oldPasserror').innerHTML  = "";
      document.getElementById('newPasserror').innerHTML  = "";
      document.getElementById('confirmPasserror').innerHTML  = "";

    this.http.post(Constants.HOME_URL +'changePassword', passDetails).subscribe((data)=>{    
      //console.log(data);
      $("#successMatch").modal('show');
      this.validationMsg = data;
    }, err=>{
      if(err.status === 403){
        $("#oldPassNotMatch").modal('show');
        this.validationMsg = err.error;
      }else{
        $("#oldPassNotMatch").modal('show');
        this.validationMsg = err.error;
      }
    });   
    }
   }

  oldPassError(){
		if(this.user.oldPassword != undefined || this.user.oldPassword != "")
		document.getElementById('oldPasserror').innerHTML  = "";
  };
  
  newPassError(){
		if(this.user.password != undefined || this.user.password != "")
		document.getElementById('newPasserror').innerHTML  = "";
  };
  
  confirmPassError(){
		if(this.user.confirmPassword != undefined || this.user.confirmPassword != "")
		document.getElementById('confirmPasserror').innerHTML  = "";
  };
  
  passSuccess(){
    this.appService.logout();
    this.appService.userName = '';
  }

  ngAfterViewInit(){
    $("input, textarea, .select-dropdown").focus(function() {
      $(this).closest(".input-holder").parent().find("> label").css({"color": "#4285F4"})      
    })
    $("input, textarea, .select-dropdown").blur(function(){
      $(this).closest(".input-holder").parent().find("> label").css({"color": "#333"})
    })
  }

}


