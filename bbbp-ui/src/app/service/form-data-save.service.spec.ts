import { TestBed, inject } from '@angular/core/testing';

import { FormDataSaveService } from './form-data-save.service';

describe('FormDataSaveService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [FormDataSaveService]
    });
  });

  it('should be created', inject([FormDataSaveService], (service: FormDataSaveService) => {
    expect(service).toBeTruthy();
  }));
});
