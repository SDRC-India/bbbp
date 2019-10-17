import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ExposurevisitsComponent } from './exposurevisits.component';

describe('ExposurevisitsComponent', () => {
  let component: ExposurevisitsComponent;
  let fixture: ComponentFixture<ExposurevisitsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ExposurevisitsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ExposurevisitsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
