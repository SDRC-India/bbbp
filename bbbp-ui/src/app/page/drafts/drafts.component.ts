import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { FormDataSaveService } from '../../service/form-data-save.service';
import { FormFieldsService } from '../../service/form-fields.service';

@Component({
  selector: 'app-drafts',
  templateUrl: './drafts.component.html',
  styleUrls: ['./drafts.component.scss']
})
export class DraftsComponent implements OnInit {

  drafts: any;
  constructor(private formFieldService: FormFieldsService, private formDataSave: FormDataSaveService, 
    private router:Router) { }

  ngOnInit() {
    
      this.formFieldService.getDrafts().subscribe(data=>{
        this.drafts = data;
        console.log(this.drafts);
        
      })

 
  }
  getSavedQuestions(draft){
    this.formFieldService.getQuestions(draft.submissionId).subscribe(data=>{
      this.formDataSave.submissionId = draft.submissionId;
      this.formDataSave.form = data;
      this.formDataSave.yearName = draft.yearRange;
      this.formDataSave.periodreferenceName = draft.timeRange;
      this.formDataSave.periodReferenceId = draft.periodId;
      this.formDataSave.yearId = draft.yearId;
      this.formDataSave.setForm();
      this.formDataSave.retrieveDropdownValue();
      this.router.navigateByUrl('/data-entry');
      console.log(this.formDataSave.form);
      
    })
  }

}
