import { Component, OnInit } from '@angular/core';

import { ApiService } from '../services/api.service';

@Component({
  selector: 'app-resources-guidelines',
  templateUrl: './resources-guidelines.component.html',
  styleUrls: ['./resources-guidelines.component.scss']
})
export class ResourcesGuidelinesComponent implements OnInit {

  apiService: ApiService;
  questions: any;
  constructor(private apiServiceProvider: ApiService) {
    this.apiService = apiServiceProvider;
   }

  ngOnInit() {
    this.getQuestionFields();
  }

  getQuestionFields(){
    this.apiService.getResourceQuestionList().subscribe(res => {
      this.questions = res;
    })
  }

  selectDropdown(value, model){
    model.optionValue = value;
    model.value = value.value;
  }

  submitForm(){

  }
}
