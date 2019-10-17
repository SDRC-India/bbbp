import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators } from '@angular/forms';
import { FormModel } from '../../../interface/form.model';
import { FormControlService } from '../../../service/form-control.service';
import { FormFieldsService } from '../../../service/form-fields.service';
import { AreaFilterPipe } from '../../../filters/area-filter.pipe'
import { HttpClient } from '@angular/common/http/';
import { Constants } from '../../../constants';

declare var $: any;


@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss']
})
export class ResetPasswordComponent implements OnInit {
  form: FormGroup;
  formFields: any;
  formFieldsAll: any;
  payLoad = '';
  selectedRole : any;
  areaDetails: any;  
  natAreaDetails: any;
  stateList: any;
  selectednationalUser: any;
  selectedState: any;
  parentAreaId: number;
  nationalParentAreaId: number;
  stateParentAreaId: number;
  distParentAreaId:number;
  selectedDistrict: any;
  allUser: any;
  allData: any;
  areaCode: any;
  tableData: any;
  newPassword: any;
  userId: any;
  validationMsg: any;
  user: UserPass = {
    username: '',
    oldPassword: '',
    password: '',
    confirmPassword: ''
  }

  constructor(private formControlService: FormControlService, private formFieldService: FormFieldsService,
  private areaPipe: AreaFilterPipe, private http: HttpClient) { }

  ngOnInit() {
    this.formFieldService.getUserRoles().subscribe(data=>{
      this.formFieldsAll = data;      
    })    
    this.formFieldService.getAreaDetails().subscribe(data=>{
      this.areaDetails = data;      
    })   
   
  }

  selectDropdown(userRole){
    this.selectedRole = userRole; 
    this.selectednationalUser = '';
    this.selectedState = '';
    this.selectedDistrict = '';
    this.tableData = '';
  }
  selectNationalUser(country: IArea){
    this.nationalParentAreaId = country.areaId;
    this.areaCode = country.areaCode;
    this.selectednationalUser = country.shortName; 
    this.selectedState = '';
    this.selectedDistrict = '';
    this.tableData = '';
  }
  selectState(selectedState: IArea){
    this.stateParentAreaId = selectedState.areaId;    
    this.areaCode = selectedState.areaCode;
    this.selectedState = selectedState.areaName;
    this.selectedDistrict = '';
    this.tableData = '';
    //console.log(this.areaDetails);
  }
  selectDistrict(selectedDist: IArea){
    this.distParentAreaId = selectedDist.areaId;
    this.areaCode = selectedDist.areaCode;
    this.selectedDistrict = selectedDist.areaName;
    this.tableData = '';
  }
  getDetails(){ 
    this.http.get(Constants.HOME_URL+'allUser').subscribe((response) => {
      this.allData  = response;
      this.allUser  = Object.keys(this.allData);    
      for(let i=0; i<= this.allUser.length; i++){
        if(this.allUser[i] == this.areaCode){         
          this.tableData = this.allData[this.allUser[i]];         
          this.userId = this.tableData[0].userId;
        }
      }
    }); 
 }
 resetModal(){
   $("#resetPassModal").modal('show');
 }
 resetBox(user){
  this.user.password = "";
  this.user.confirmPassword = "";
  document.getElementById('newPasserror').innerHTML = "";
  document.getElementById('confirmPasserror').innerHTML = "";
 }
 submitModal(userId:number, user){  
  let passDetails = {
    'userId' : userId,
    'newPassword': this.user.password
  };

 if(this.user.password === "" || this.user.password === undefined){
   document.getElementById('newPasserror').innerHTML  = "Please enter new password."; 
 } else if(this.user.confirmPassword === "" || this.user.confirmPassword === undefined){
   document.getElementById('confirmPasserror').innerHTML  = "Please enter confirm password."; 
 } else if(this.user.confirmPassword === this.user.password) {
    this.http.post(Constants.HOME_URL + 'resetPassword', passDetails).subscribe((data)=>{  
      if(data===true){
        $("#resetPassModal").modal('hide');
        $("#successMatch").modal('show');
        this.user.password = "";
        this.user.confirmPassword = "";
        this.selectedRole = "";
        this.selectednationalUser ="";
        this.selectedState = '';
        this.selectedDistrict = '';
        this.tableData = '';
      }else{
        $("#resetPassModal").modal('hide');
        $("#oldPassNotMatch").modal('show');
        this.user.password = "";
        this.user.confirmPassword = "";
        this.validationMsg = "Error occurred";
      }  
    }, err=>{
      $("#oldPassNotMatch").modal('show');
      this.validationMsg ="Error occurred";
    });
  }
}

newPassError(){
  if(this.user.password != undefined || this.user.password != "")
  document.getElementById('newPasserror').innerHTML  = "";
};

confirmPassError(){
  if(this.user.confirmPassword != undefined || this.user.confirmPassword != "")
  document.getElementById('confirmPasserror').innerHTML  = "";
};

  ngAfterViewInit(){
    $("input, textarea, .select-dropdown").focus(function() {
      $(this).closest(".input-holder").parent().find("> label").css({"color": "#4285F4"})
      
    })
    $("input, textarea, .select-dropdown").blur(function(){
      $(this).closest(".input-holder").parent().find("> label").css({"color": "#333"})
    })
  }

}
