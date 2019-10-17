import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadingModalComponent } from './uploading-modal.component';

describe('UploadingModalComponent', () => {
  let component: UploadingModalComponent;
  let fixture: ComponentFixture<UploadingModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UploadingModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UploadingModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
