import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Constants } from '../../../constants';
import { StaticPageService } from '../services/static-page.service';

declare var $: any;

@Component({
  selector: 'app-contactus',
  templateUrl: './contactus.component.html',
  styleUrls: ['./contactus.component.scss']
})
export class ContactusComponent implements OnInit {
  organizationType: any;
  selectionFields: any;
  name: string;
  email: string;
  contactNumber: string = "";
  organisationTypeId: string;
  organisationType: string;
  organisation: string;
  designation: string;
  feedback: string = "";
  feedbackLimit: number = 250;
  captcha: any;
  errorName: string = "";
  errorEmail:string = "";
  errorComment: string = "";
  errorPhn: string = "";
  errorCaptcha: string = "";

  constructor(private contactService: StaticPageService, private http: HttpClient, private router: Router) { }
  ngOnInit() {
    this.contactService.getOrganisationalData().subscribe(data => {
      this.organizationType = data;
      $("#phoneNumberId").attr("autocomplete", "off");
    })
  }

  selectType(type) {
    this.organisationType = type.value;
    this.organisationTypeId = type.key;
  }

  resolved(captchaResponse: string) {
    console.log(`Resolved captcha with response ${captchaResponse}:`);
    this.captcha = captchaResponse;
  }

  submitFeedback() {
    var emailPattern = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    let feedbackModel = {
      'id': 0,
      'name': this.name,
      'email': this.email,
      'contactNumber': this.contactNumber,
      'organisationTypeId': parseInt(this.organisationTypeId),
      'organisationType': this.organisationType,
      'organisation': this.organisation,
      'designation': this.designation,
      'feedback': this.feedback
    }
    if (this.name === "" || this.name === undefined) {
      this.errorName = "Please enter name.";
      $('html,body').animate({
        scrollTop: $('#errorName').offset().top - 250
      }, 'slow');
      return false;
    }
    else if (this.email === "" || this.email === undefined) {
      this.errorEmail = "Please enter email address.";
      $('html,body').animate({
        scrollTop: $('#errorEmail').offset().top - 250
      }, 'slow');
      return false;
    }
    else if (!emailPattern.test(this.email)) {
      this.errorEmail = "Please provide a valid email address";
      $('html,body').animate({
        scrollTop: $('#errorComment').offset().top - 250
      }, 'slow');
      return false;
    }
    else if (this.feedback === "" || this.feedback === undefined) {
      this.errorComment = "Please give feedback.";
      $('html,body').animate({
        scrollTop: $('#errorComment').offset().top - 250
      }, 'slow');
      return false;
    }
    else if (this.contactNumber != "" && this.contactNumber != undefined && this.contactNumber.length < 10 ) {
        this.errorPhn = "Please provide 10 digit mobile number.";
        $('html,body').animate({
          scrollTop: $('#errorPhn').offset().top - 250
        }, 'slow');
        return false;
    } 
    else if(this.captcha === null || this.captcha === undefined){
      this.errorCaptcha  = "Please select captcha.";
      $('html,body').animate({
        scrollTop: $('#errorCaptcha').offset().top - 250},'slow');
        return false; 
    }else {
      this.errorPhn = "";
      this.http.post(Constants.HOME_URL + Constants.CONTACT_URL + 'saveContactUs', feedbackModel).subscribe((data) => {
        $("#pop").modal('show');
      }, err => {
        $("#error").modal('show');
      });
    }
  }

  reloadPage() {
    window.location.href = 'pages/contactus';
  }
  numberError(event) {
    if (event.which != 8 && event.which != 0 && (event.which < 48 || event.which > 57)) {
      return false;
    }
  }
  validatePhoneNo() {
    if (this.contactNumber.length < 10) {
      this.errorPhn = "Please provide 10 digit mobile number.";
    }
    else {
      this.errorPhn = "";
    }
    if (this.contactNumber.length == 0) {
      this.errorPhn = "";
    }
  }
  testName() {
    if (this.name != "" || this.name != undefined) {
      this.errorName = "";
    }
  }
  testEmail() {
    if (this.email != "" || this.email != undefined) {
      this.errorEmail = "";
    }
  }
  testFeedback() {
    if (this.feedback != "" || this.feedback != undefined) {
      this.errorComment = "";
    }
  }
  testCaptcha() {
    if (this.captcha != null || this.captcha != undefined) {
      this.errorCaptcha = "";
    }
  }
}

