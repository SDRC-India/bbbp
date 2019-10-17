import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Constants } from '../constants';
import { FormModel } from '../interface/form.model';

@Injectable()
export class FormFieldsService {
  questions:any ;
  userRole: any;
  useAreaDetails: any;
  public paramVar: string;
  submissionId: number = null;
  constructor(private httpClient: HttpClient) { 
    
  }
  
  // Todo: get from a remote source of question metadata
  // Todo: make asynchronous

  getQuestions(id) {  
    if(id == null){
      this.submissionId = null;
      return this.httpClient.get(Constants.HOME_URL + 'getAllDataEntryQuestions');
    }
    else{
      this.submissionId = id;
      return this.httpClient.get(Constants.HOME_URL + 'getAllDataEntryQuestions?submissionId=' + id);
    }
      
  }   
  getTabWiseQuestions(questions,paramVar):FormModel<any>[] {   
    return questions[paramVar];
  }

  getUserRoles(){
    this.userRole = this.httpClient.get(Constants.HOME_URL + 'allRole');   
    return this.userRole;
  }

  getAreaDetails(){
    this.useAreaDetails = this.httpClient.get(Constants.HOME_URL + 'getAllArea');   
    return this.useAreaDetails;
  }

  getDataEntryTimeperiodSelection(){    
    return this.httpClient.get(Constants.HOME_URL + 'getAllPeriodReference')
  }
  getCheckSubmission(yearId, periodReferenceId){
    return this.httpClient.get(Constants.HOME_URL + 'getCheckSubmission?periodReferenceId='+ periodReferenceId + '&yearId=' + yearId)
  }
  getDrafts(){
    return this.httpClient.get(Constants.HOME_URL + 'getAllDrafts')
  }
 
  downloadAttachment(id){
    if(id){
      this.httpClient.get(Constants.HOME_URL + 'downloadAttached?attachmentId='+id).subscribe(response=>{
        
      })
    }
  }
}

