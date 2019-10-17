import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { FormModel } from '../interface/form.model'

@Injectable()
export class FormControlService {

  constructor() { }
  toFormGroup(questions: FormModel<any>[] ) {
    let group: any = {};
    if(questions)
    questions.forEach(question => {
      group[question.key] = question.required ? new FormControl(question.value || '', Validators.required)
                                              : new FormControl(question.value || '');
    });
    return new FormGroup(group);
  }
}
