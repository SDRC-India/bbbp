import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

import { Constants } from '../../../constants';
import { FormControlService } from '../../../service/form-control.service';
import { FormDataSaveService } from '../../../service/form-data-save.service';
import { FormFieldsService } from '../../../service/form-fields.service';

declare var $: any;

@Component({
  selector: 'app-institutional',
  templateUrl: './institutional.component.html',
  styleUrls: ['./institutional.component.scss']
})
export class InstitutionalComponent implements OnInit {
  form: FormGroup;
  formFields: any;
  formFieldsAll: any;
  formSubSections: any;
  payLoad = '';
  selectedYear: string;
  selectedQuarter: string;
  firstTime: boolean = true;
  sectionName: string = "Inter-sectoral convergence/initiatives";
  baseUrl = Constants.HOME_URL;
  formDataSave: FormDataSaveService;

  constructor(private formControlService: FormControlService, private formDataSaveService: FormDataSaveService, 
    private httpClient: HttpClient, private router: Router, private formFieldService: FormFieldsService) { 
      this.formDataSave = formDataSaveService;
    }

  ngOnInit() {
      window.scrollTo(0, 0);
      if(this.formDataSave.form){
        console.log(this.formDataSave.form);
        this.selectedYear = this.formDataSave.yearName;
      this.selectedQuarter = this.formDataSave.periodreferenceName;
        this.formSubSections = this.formDataSave.form[this.sectionName];
        this.labelHighlightOnFocus();
      }
      else{
        this.router.navigateByUrl('/data-entry-selection');
      }
      
  }
  submitForm(){
    console.log(this.formFields);
  }
  saveFormData(){
    this.formDataSave.submitFormData(false);
  }
  clearSelections(fileIndex, subsectionIndex, subsectionKey, index, element){
    // if(this.formDataSave.form[this.sectionName][subsectionIndex][subsectionKey][index]['fileValue'][fileIndex].isAttached)
      this.formDataSave.form[this.sectionName][subsectionIndex][subsectionKey][index]['fileValue'][fileIndex].isDeleted = true;
      this.formDataSave.form[this.sectionName][subsectionIndex][subsectionKey][index]['fileValue'][fileIndex].base64 = null;
      // else
      // this.formDataSave.form[this.sectionName][subsectionIndex][subsectionKey][index]['fileValue'].splice(fileIndex, 1);  
    $(element.target).parent().parent().find('input').val("");
  }
  saveDataProceed(){
    this.formDataSave.saveData(this.sectionName, this.formSubSections);
    this.router.navigateByUrl('/data-entry-survival');
  }
  selectCheckBox(changedOptionIndex, subsectionIndex, subsectionKey, index, event){
    let field = this.formDataSave.form[this.sectionName][subsectionIndex][subsectionKey][index];
    if(!field.options[changedOptionIndex].isSelected){
      field.options[changedOptionIndex].isSelected = true;
    }
    else{
      field.options[changedOptionIndex].isSelected = false;
    }
    if(field.options[field.options.length-1].isSelected){
      field.isOthersSelected = true;
    }
    else{
      field.isOthersSelected = false;
      field.othersValue = null;
    }
    field.value = "";
    field.valueKeys = "";
    for(let i=0; i<field.options.length; i++){
      if(field.options[i].isSelected){
        if(!field.value){
          field.value += field.options[i].value;
          field.valueKeys += field.options[i].key
        }
        else{
          field.value += ","+field.options[i].value;
          field.valueKeys += ","+field.options[i].key
        }
          
      }
    }
  }
  selectDropdown(selectedOption, subsectionIndex, subsectionKey, index){
    this.formDataSave.form[this.sectionName][subsectionIndex][subsectionKey][index].value = selectedOption.value;
    console.log(this.formDataSave.form)
    this.formDataSave.form[this.sectionName][subsectionIndex][subsectionKey][index].optionValue = selectedOption;
    this.formDataSave.clearDependentMembers(this.sectionName, subsectionIndex, subsectionKey, index)
  }
  clearFieldValue(subsectionIndex, index){
    this.formDataSave.clearFieldValue(this.sectionName, subsectionIndex, index);
  }
  preventNegative(e){
    console.log('preventNegative')
    if(!((e.keyCode > 95 && e.keyCode < 106)
    || (e.keyCode > 47 && e.keyCode < 58) 
    || e.keyCode == 8)) {
      return false;
  }
  }
  onFileChange(event, key, i, j, k) {
    
    if(event.target.files && event.target.files.length > 0) {
      
      let files = event.target.files;
      console.log(files);
      let totalFileSize = 0;
      var subsectionJson = this.formDataSave.form[this.sectionName][k];
      subsectionJson[Object.keys(subsectionJson)[j]][i].duplicateFilesDetected = false;
      var previousFileValue = subsectionJson[Object.keys(subsectionJson)[j]][i].fileValue;
      for (let m = 0; m < files.length; m++) {
        for (let j = 0; j < previousFileValue.length; j++) {
          if(files[m] && files[m].name == previousFileValue[j].fileName && !previousFileValue[j].isDeleted){
            files = [];
            
          }
          
        }
        
      }
      subsectionJson[Object.keys(subsectionJson)[j]][i].wrongFileExtensions = false;
      for(let b=0; b<previousFileValue.length; b++){
        if(!previousFileValue[b].isDeleted)
        totalFileSize += Math.round(previousFileValue[b].fileSize/1024);
      }
      for(let a=0; a<files.length; a++){
        totalFileSize += Math.round(files[a].size/1024)
      }
      if(files.length){
        if(totalFileSize < 1024 * 10){
          for(let a=0; a<files.length; a++){
            let file = files[a]
            let extension = file.name.split('.')[file.name.split('.').length-1];
            if(extension.toLowerCase() == 'pdf' || extension.toLowerCase() == 'doc' || extension.toLowerCase() == 'docx' || extension.toLowerCase() == 'xls' || extension.toLowerCase() == 'xlsx'){
              let reader = new FileReader();
              reader.readAsDataURL(file);
              reader.onload = () => {
                
                
                // this.saveCodedFiles(codedFiles, i, j, k);
                var subsectionJson = this.formDataSave.form[this.sectionName][k];
                subsectionJson[Object.keys(subsectionJson)[j]][i].fileValue.push({
                  'base64': (reader.result as string).split(',')[1], 
                  'fileName': file.name, 
                  'fileSize': file.size,
                  'typeId': null,
                  'isDeleted': false,
                  'submissionId': this.formFieldService.submissionId,
                  'isAttached': false,
                  'columnName': subsectionJson[Object.keys(subsectionJson)[j]][i].columnName,
                  'monthRange': this.formDataSave.periodreferenceName,
                  'yearRange': this.formDataSave.yearName,
                  'typeName': '',
                  'attachmentId': null
                })
                subsectionJson[Object.keys(subsectionJson)[j]][i].fileSizeExceeds = false;
                // console.log(Math.round(file.size/1024) +'KB');
                
              };
            }
            else{
              subsectionJson[Object.keys(subsectionJson)[j]][i].wrongFileExtensions = true;
              subsectionJson[Object.keys(subsectionJson)[j]][i].fileSizeExceeds = false;
            }
          
          }
          $(event.target).parent().parent().find('input').val("");
        }
        else{
          var subsectionJson = this.formDataSave.form[this.sectionName][k];
          subsectionJson[Object.keys(subsectionJson)[j]][i].fileSizeExceeds = true;
          $(event.target).parent().parent().find('input').val("");
        }
      }
      else{
        subsectionJson[Object.keys(subsectionJson)[j]][i].duplicateFilesDetected = true;
        $(event.target).parent().parent().find('input').val("");
      }
    }
    
  }
  downloadAttachment(id){
    this.formFieldService.downloadAttachment(id)
  }
  clearAllChildFields(parentColumn, dependentCondition, subsectionIndex, subsectionKey, index){
    console.log('clearAllChildFields')
    let subsectionQuestions = this.formDataSave.form[this.sectionName][subsectionIndex][subsectionKey];
    if(!subsectionQuestions[index].value){
      for (let i = 0; i < subsectionQuestions.length; i++) {
        const element = subsectionQuestions[i];
        if(element.dependentColumn == subsectionQuestions[index].columnName && element.dependentCondition[0] == dependentCondition){
          if(element.controlType == "dropdown" || element.controlType == "checkbox"){
            this.formDataSave.form[this.sectionName][subsectionIndex][subsectionKey][i].optionValue = undefined;
            this.formDataSave.form[this.sectionName][subsectionIndex][subsectionKey][i].value = undefined;
            let options = this.formDataSave.form[this.sectionName][subsectionIndex][subsectionKey][i]['options']
            for (let index = 0; index < options.length; index++) {
              const element = options[index];
              element.isSelected = false;
            }
            this.formDataSave.form[this.sectionName][subsectionIndex][subsectionKey][i].othersValue = null;
            this.formDataSave.form[this.sectionName][subsectionIndex][subsectionKey][i].isOthersSelected = false;
          }
          else{
            this.formDataSave.form[this.sectionName][subsectionIndex][subsectionKey][i].value = undefined;
          }
        }
      }
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
      $('.select-checkbox').on('click', function (event) {
        $(this).closest('.btn-group').toggleClass('open');
       });
      $('body').on('click', function (e) {
        if (!$('.checkbox-list  .checkbox-group label').is(e.target) 
            && $('.checkbox-list').has(e.target).length === 0 
            && $('.check-btn-group.open').has(e.target).length === 0
        ) {
            $('.check-btn-group.open').removeClass('open');
        }
      });
      // $(".checkbox-list, .checkbox-list .checkbox-group, .checkbox-list .checkbox-group label, .checkbox-list .checkbox-group input").click(function(event){
      //   event.stopPropagation();
      // })
    }, 500)
    
  }

}
