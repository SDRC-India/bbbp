import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

import { FormControlService } from '../../../service/form-control.service';
import { FormDataSaveService } from '../../../service/form-data-save.service';
import { FormFieldsService } from '../../../service/form-fields.service';

declare var $: any;

@Component({
  selector: 'app-data-entry-selection',
  templateUrl: './data-entry-selection.component.html',
  styleUrls: ['./data-entry-selection.component.scss']
})
export class DataEntrySelectionComponent implements OnInit {

  form: FormGroup;
  selectionFields: any;
  formFieldsAll: any;
  payLoad = '';
  centralModalError:string; 
  constructor(private formControlService: FormControlService, private formFieldService: FormFieldsService, private router: Router, private formDataSave: FormDataSaveService) { }

  ngOnInit() {
    this.formFieldService.getDataEntryTimeperiodSelection().subscribe(data=>{
      this.selectionFields = data; 
    }); 
    // $("#UAT").modal('show')
  }

  submitForm(){
    var errorFound: boolean = false;
    for(let i=0; i<this.selectionFields.length; i++){
      if(this.selectionFields[i].value == '' || this.selectionFields[i].value == undefined){
        errorFound = true;
        this.selectionFields[i].errorFound = true;
        this.selectionFields[i].errorMessage = 'Please '+ this.selectionFields[i].label;
      }
    }
    if(!errorFound){
      this.formDataSave.periodReferenceId = this.selectionFields[1].key;
      this.formDataSave.periodreferenceName = this.selectionFields[1].value;
      this.formDataSave.yearId = this.selectionFields[0].key;
      this.formDataSave.yearName = this.selectionFields[0].value;
      this.formFieldService.getCheckSubmission(this.selectionFields[0].key, this.selectionFields[1].key).subscribe(response=>{
        if(response == null){
          this.formFieldService.getQuestions(null).subscribe(data=>{
            this.formDataSave.form = data;
            this.formDataSave.setForm();
            this.router.navigateByUrl('/data-entry');
        });
        console.log(response);
        }
      },
      error => {
         if(error.error.text == "Data for the selected time period is saved for editing. Click Ok Go to Drafts"){
           $('#alreadySaved').modal('show');
         }
         if(error.error.text == "Data for the selected time period is already submitted "){
          $('#alreadySubmitted').modal('show');
        }
         
      })
      
    }
    
  }
  goToDrafts(){
    $('#alreadySaved').modal('hide');
    this.formFieldService.getDrafts().subscribe(data=>{
      this.formDataSave.drafts = data;
      this.router.navigateByUrl('/drafts')
    })
    
  }
  selectDropdown(selectedOption, model, index){
    this.selectionFields[index].errorFound = false;
    this.selectionFields[index].errorMessage= "";
    this.selectionFields[index].value = selectedOption.value;
    this.selectionFields[index].key = selectedOption.key;
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
