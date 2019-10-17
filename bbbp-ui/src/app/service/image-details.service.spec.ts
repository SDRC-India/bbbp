import { TestBed, inject } from '@angular/core/testing';

import { ImageDetailsService } from './image-details.service';

describe('ImageDetailsService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ImageDetailsService]
    });
  });

  it('should be created', inject([ImageDetailsService], (service: ImageDetailsService) => {
    expect(service).toBeTruthy();
  }));
});
