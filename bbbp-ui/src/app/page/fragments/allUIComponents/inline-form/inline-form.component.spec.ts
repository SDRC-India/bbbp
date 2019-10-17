import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InlineFormComponent } from './inline-form.component';
import { NO_ERRORS_SCHEMA } from '@angular/core';

describe('InlineFormComponent', () => {
  let component: InlineFormComponent;
  let fixture: ComponentFixture<InlineFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InlineFormComponent ],
      schemas: [NO_ERRORS_SCHEMA]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InlineFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
