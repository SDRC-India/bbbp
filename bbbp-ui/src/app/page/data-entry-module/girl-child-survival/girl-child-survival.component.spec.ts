import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GirlChildSurvivalComponent } from './girl-child-survival.component';

describe('GirlChildSurvivalComponent', () => {
  let component: GirlChildSurvivalComponent;
  let fixture: ComponentFixture<GirlChildSurvivalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GirlChildSurvivalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GirlChildSurvivalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
