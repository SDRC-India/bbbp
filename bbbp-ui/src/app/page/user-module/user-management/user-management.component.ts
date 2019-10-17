import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';

import { Constants } from '../../../constants';
import { AreaFilterPipe } from '../../../filters/area-filter.pipe';
import { FormControlService } from '../../../service/form-control.service';
import { FormFieldsService } from '../../../service/form-fields.service';

declare var $: any;


@Component({
  selector: 'app-user-management',
  templateUrl: './user-management.component.html',
  styleUrls: ['./user-management.component.scss']
})
export class UserManagementComponent implements OnInit {
  form: FormGroup;
  formFields: any;
  formFieldsAll: any;
  payLoad = '';
  selectedRole : any;
  selectedRoleId: number;
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
  paramModal: any;
  validationMsg: any;
  btnDisable: boolean = false;

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
    this.selectedRole = userRole.roleName
    this.selectedRoleId = userRole.roleId
    this.selectednationalUser = '';
    this.selectedState = '';
    this.selectedDistrict = '';
  }
  selectNationalUser(country: IArea){
    this.nationalParentAreaId = country.areaId;
    this.selectednationalUser = country.shortName; 
    this.selectedState = '';
    this.selectedDistrict = '';  
  }
  selectState(selectedState: IArea){
    this.stateParentAreaId = selectedState.areaId;    
    this.selectedState = selectedState.areaName;
    this.selectedDistrict = '';   
  }
  selectDistrict(selectedDist: IArea){
    this.distParentAreaId = selectedDist.areaId;
    this.selectedDistrict = selectedDist.areaName;   
  }
  submitForm(roleId:any, areaId:any){ 
     let userDetails={
       'roleId' : roleId,
       'areaId': areaId
     };
     this.http.post(Constants.HOME_URL+'addUser', userDetails).subscribe((data) => {        
      if(data===true){
        $("#successMatch").modal('show');
      }else{
        $("#oldPassNotMatch").modal('show');
        this.validationMsg = "The given user details are already exists";
      }
     }, err=>{
      $("#oldPassNotMatch").modal('show');
      //this.validationMsg = err.error;
      this.validationMsg = "The given user details are already exists";
    });
  }
  successModal(){
    this.selectedRole = "";
    this.selectednationalUser ="";
    this.selectedState = '';
    this.selectedDistrict = '';
    $("#successMatch").modal('hide');
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
