import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

import { FormControlService } from '../../../service/form-control.service';
import { FormDataSaveService } from '../../../service/form-data-save.service';

declare var $: any;

@Component({
  selector: 'app-training',
  templateUrl: './training.component.html',
  styleUrls: ['./training.component.scss']
})
export class TrainingComponent implements OnInit {

  form: FormGroup;
  formFields: any;
  formFieldsVal: any;
  formSubSections: any;
  payLoad = '';
  selectedYear: string;
  selectedQuarter: string;
  formDataSave: FormDataSaveService;
  sectionName: any="Training and Capacity-building";
  constructor(private formControlService: FormControlService, private formDataSaveService: FormDataSaveService, 
    private httpClient: HttpClient, private router: Router) {
      this.formDataSave = formDataSaveService;
     }
  ngOnInit() {
    window.scrollTo(0, 0);
    if(this.formDataSave.form){
      this.formSubSections = this.formDataSave.form[this.sectionName];
      this.selectedYear = this.formDataSave.yearName;
      this.selectedQuarter = this.formDataSave.periodreferenceName;
      // this.formDataSave.setForm();
      this.labelHighlightOnFocus();
    }
    else{
      this.router.navigateByUrl('/data-entry-selection');
    }
}
  submitForm(){
    console.log(this.formFields);
  }

  selectDropdown(selectedOption, subsectionIndex, subsectionKey, index){
    this.formDataSave.form[this.sectionName][subsectionIndex][subsectionKey][index].value = selectedOption.value;
    // this.formFieldsAll["Awareness/ Outreach Activities"].value = selectedOption.value;
    this.formDataSave.form[this.sectionName][subsectionIndex][subsectionKey][index].optionValue = selectedOption;
  }
  previousSection(){
    this.router.navigateByUrl('/data-entry-survival');
  }
  saveDataProceed(){
    this.formDataSave.saveData(this.sectionName, this.formSubSections);
    this.router.navigateByUrl('/data-entry-awareness');
  }
  saveFormData(){
    this.formDataSave.submitFormData(false);
  }
  preventNegative(e){
    if(!((e.keyCode > 95 && e.keyCode < 106)
    || (e.keyCode > 47 && e.keyCode < 58) 
    || e.keyCode == 8)) {
      return false;
  }
  }
  labelHighlightOnFocus(){
    setTimeout(function(){
      $("input, textarea, .select-dropdown").focus(function() {
        $(this).closest(".input-holder").parent().find("> label").css({"color": "#4285F4"})
        
      })
      $("input, textarea, .select-dropdown").blur(function(){
        $(this).closest(".input-holder").parent().find("> label").css({"color": "#333"})
      })
    }, 500)
    
  }

}
