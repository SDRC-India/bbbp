import { TestBed, inject } from '@angular/core/testing';

import { LoggedinGuardService } from './loggedin-guard.service';

describe('LoggedinGuardService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [LoggedinGuardService]
    });
  });

  it('should be created', inject([LoggedinGuardService], (service: LoggedinGuardService) => {
    expect(service).toBeTruthy();
  }));
});
