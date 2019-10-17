import { TestBed, inject, getTestBed } from '@angular/core/testing';

import { FormControlService } from './form-control.service';
import { FormModel } from '../interface/form.model';

describe('FormControlService', () => {
  let injector;
  let service: FormControlService;
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [FormControlService]
    });
    injector = getTestBed();
    service = injector.get(FormControlService);
  });

  /**
   * toFormGroup() is converting questions to formgroup 
   */
  describe('toFormGroup():', () => {
    it('Works fine', () => {
      let dummyQuestions:FormModel<any>[] = [{  
        "value":"Bombasto",
        "key":"firstName",
        "label":"First name",
        "required":true,
        "controlType":"textbox",
        "type":"text"
     },
     {  
        "key":"emailAddress",
        "label":"Email",
        "required":false,
        "controlType":"textbox",
        "type":"email"
     }];
      expect(service.toFormGroup(dummyQuestions).value.emailAddress).toBe('');
      expect(service.toFormGroup(dummyQuestions).value.firstName).toBe('Bombasto');
    })
  })
});
