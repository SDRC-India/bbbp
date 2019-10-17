import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadCsrComponent } from './upload-csr.component';

describe('UploadCsrComponent', () => {
  let component: UploadCsrComponent;
  let fixture: ComponentFixture<UploadCsrComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UploadCsrComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UploadCsrComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
