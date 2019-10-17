import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BestpraticesComponent } from './bestpratices.component';

describe('BestpraticesComponent', () => {
  let component: BestpraticesComponent;
  let fixture: ComponentFixture<BestpraticesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BestpraticesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BestpraticesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
