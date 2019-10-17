import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TrainingmoduleComponent } from './trainingmodule.component';

describe('TrainingmoduleComponent', () => {
  let component: TrainingmoduleComponent;
  let fixture: ComponentFixture<TrainingmoduleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TrainingmoduleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TrainingmoduleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
