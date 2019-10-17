import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NavdishareportComponent } from './navdishareport.component';

describe('NavdishareportComponent', () => {
  let component: NavdishareportComponent;
  let fixture: ComponentFixture<NavdishareportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NavdishareportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NavdishareportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
