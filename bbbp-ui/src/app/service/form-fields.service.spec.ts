import { TestBed, inject, getTestBed } from '@angular/core/testing';

import { FormFieldsService } from './form-fields.service';

describe('FormFieldsService', () => {
  let injector;
  let service: FormFieldsService;
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [FormFieldsService]
    });
    injector = getTestBed();
    service = injector.get(FormFieldsService);
  });

  /**
   * getQuestions() returning questions or not
   */
  // describe('getQuestions():', () => {
  //   it('returning all questions', () => {
  //     expect(service.getQuestions()).toBe(service.questions);
  //   })
  // })
});
