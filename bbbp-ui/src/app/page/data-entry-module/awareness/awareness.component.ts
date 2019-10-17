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
  selector: 'app-awareness',
  templateUrl: './awareness.component.html',
  styleUrls: ['./awareness.component.scss']
})
export class AwarenessComponent implements OnInit {
  form: FormGroup;
  formFields: any;
  formSubSections:any;
  formSections: any;
  payLoad = '';
  selectedYear: string;
  selectedQuarter: string;
  sectionName: string = "Awareness/Outreach Activities";
  baseUrl = Constants.HOME_URL;
  formDataSave: FormDataSaveService;
  constructor(private formControlService: FormControlService, private formDataSaveService: FormDataSaveService, 
    private httpClient: HttpClient, private router: Router, private formFieldService: FormFieldsService) { 
      this.formDataSave = formDataSaveService;
    }
  ngOnInit() {
    window.scrollTo(0, 0);
    this.formDataSave.agreeCorrectInformation = false;
    if(this.formDataSave.form){
      this.formSections = this.formDataSave.form;
      this.formSubSections = this.formDataSave.form[this.sectionName];
      this.selectedYear = this.formDataSave.yearName;
      this.selectedQuarter = this.formDataSave.periodreferenceName;
      this.labelHighlightOnFocus();
    }
    else{
      this.router.navigateByUrl('/data-entry-selection');
    }
}
  submitConfirm(){

  }
  submitFormData(){
    this.formDataSave.submitFormData(true);
    
  }
  previousSection(){
    this.router.navigateByUrl('/data-entry-training');
  }
  saveDataProceed(){
    this.formDataSave.saveData(this.sectionName, this.formSubSections);
  }
  saveFormData(){
    this.formDataSave.submitFormData(false);
  }

  SelectAgreeCorrectInformation() {
    this.formDataSave.agreeCorrectInformation = !this.formDataSave.agreeCorrectInformation;
  }

  selectDropdown(selectedOption, subsectionIndex, subsectionKey, index){
    this.formDataSave.form[this.sectionName][subsectionIndex][subsectionKey][index].value = selectedOption.value;
    // this.formFieldsAll["Awareness/ Outreach Activities"].value = selectedOption.value;
    this.formDataSave.form[this.sectionName][subsectionIndex][subsectionKey][index].optionValue = selectedOption;

  }
  clearSelections(fileIndex, subsectionIndex, subsectionKey, index, element, fileValueKey){
    // if(this.formDataSave.form[this.sectionName][subsectionIndex][subsectionKey][index]['fileValue'][fileValueKey][fileIndex].isAttached)
      this.formDataSave.form[this.sectionName][subsectionIndex][subsectionKey][index]['fileValue'][fileValueKey][fileIndex].isDeleted = true;
      this.formDataSave.form[this.sectionName][subsectionIndex][subsectionKey][index]['fileValue'][fileValueKey][fileIndex].base64 = null;
    // else
    //   this.formDataSave.form[this.sectionName][subsectionIndex][subsectionKey][index]['fileValue'][fileValueKey].splice(fileIndex)
    $(element.target).parent().parent().find('input').val("");
  }
  onFileChange(event, key, i, j, k) {
    var codedFiles = [];
    if(event.target.files && event.target.files.length > 0) {
      let files = event.target.files;
      let totalFileSize = 0;
      
      var subsectionJson = this.formDataSave.form[this.sectionName][k];
            var subsectionQuestions = subsectionJson[Object.keys(subsectionJson)[j]];
            var selectionType = subsectionQuestions[subsectionQuestions[i].parentIndex[2]].value;
            subsectionJson[Object.keys(subsectionJson)[j]][i].wrongFileExtensions = false;
      var previousFileValue = subsectionJson[Object.keys(subsectionJson)[j]][i].fileValue;
      subsectionJson[Object.keys(subsectionJson)[j]][i].duplicateFilesDetected = false;
      for (let m = 0; m < files.length; m++) {
        for(let b=0; b<Object.keys(previousFileValue).length; b++){
          for (let c = 0; c < previousFileValue[Object.keys(previousFileValue)[b]].length; c++) {
            const elem = previousFileValue[Object.keys(previousFileValue)[b]][c];
            if(files[m] && files[m].name == elem.fileName && !elem.isDeleted){
              files = [];
              
            }
          }
          
        }
        
      }
      for(let b=0; b<Object.keys(previousFileValue).length; b++){
        for (let c = 0; c < previousFileValue[Object.keys(previousFileValue)[b]].length; c++) {
          const element = previousFileValue[Object.keys(previousFileValue)[b]][c];
          if(!element.isDeleted)
          totalFileSize += Math.round(element.fileSize/1024);
        }
        
      }
      for(let a=0; a<files.length; a++){
        totalFileSize += Math.round(files[a].size/1024)
      }
      if(files.length){
        if(totalFileSize < 1024 * 30){
          for(let a=0; a<files.length; a++){
            let file = files[a];
            let extension = file.name.split('.')[file.name.split('.').length-1];
            if((selectionType == "Reports" && (extension.toLowerCase() == 'pdf' || extension.toLowerCase() == 'doc' || extension.toLowerCase() == 'docx' || extension.toLowerCase() == 'xls' || extension.toLowerCase() == 'xlsx'))
          || (selectionType == "Photographs" && (extension.toLowerCase() == 'jpg' || extension.toLowerCase() == 'png' || extension.toLowerCase() == 'gif'))
            || (selectionType == "Video Clippings " && (extension.toLowerCase() == 'mp4' || extension.toLowerCase() == 'mkv' || extension.toLowerCase() == 'mpeg'))){
              let reader = new FileReader();
              reader.readAsDataURL(file);
              reader.onload = () => {
                
                codedFiles.push({'description': (reader.result as string).split(',')[1], groupName: file.name})
                
                // this.saveCodedFiles(codedFiles, i, j, k);
                var subsectionJson = this.formDataSave.form[this.sectionName][k];
                var subsectionQuestions = subsectionJson[Object.keys(subsectionJson)[j]];
                var selectionType = subsectionQuestions[subsectionQuestions[i].parentIndex[2]].value;
                if(subsectionQuestions[i]['fileValue'][selectionType]){
                  subsectionJson[Object.keys(subsectionJson)[j]][i]['fileValue'][selectionType].push({
                    'base64': (reader.result as string).split(',')[1], 
                    'fileName': file.name, 
                    'fileSize': file.size,
                    'typeId': subsectionQuestions[subsectionQuestions[i].parentIndex[2]].optionValue.key,
                    'isDeleted': false,
                    'submissionId': this.formFieldService.submissionId,
                    'isAttached': false,
                    'columnName': subsectionJson[Object.keys(subsectionJson)[j]][i].columnName,
                    'monthRange': this.formDataSave.periodreferenceName,
                    'yearRange': this.formDataSave.yearName,
                    'typeName': selectionType,
                    'attachmentId': null
                  })
                }
                else{
                  subsectionQuestions[i]['fileValue'][selectionType] = [{
                    'base64': (reader.result as string).split(',')[1], 
                    'fileName': file.name, 
                    'fileSize': file.size,
                    'typeId': subsectionQuestions[subsectionQuestions[i].parentIndex[2]].optionValue.key,
                    'isDeleted': false,
                    'submissionId': this.formFieldService.submissionId,
                    'isAttached': false,
                    'columnName': subsectionJson[Object.keys(subsectionJson)[j]][i].columnName,
                    'monthRange': this.formDataSave.periodreferenceName,
                    'yearRange': this.formDataSave.yearName,
                    'typeName': selectionType,
                    'attachmentId': null
                  }]
                }
                
                subsectionJson[Object.keys(subsectionJson)[j]][i].fileSizeExceeds = false;
                // console.log(Math.round(file.size/1024) +'KB');
                $(event.target).parent().parent().find('input').val("");
              };
            }
            else{
              subsectionJson[Object.keys(subsectionJson)[j]][i].wrongFileExtensions = true;
              subsectionJson[Object.keys(subsectionJson)[j]][i].fileSizeExceeds = false;
              $(event.target).parent().parent().find('input').val("");
            }
          }
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
  clearFormAndRedirect(){
    this.formDataSave.form = "";
    $("#centralModalSuccess, #centralModalFailed").modal('hide');
    this.router.navigateByUrl('/');
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
