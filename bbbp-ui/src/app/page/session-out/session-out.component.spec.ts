import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SessionOutComponent } from './session-out.component';

describe('SessionOutComponent', () => {
  let component: SessionOutComponent;
  let fixture: ComponentFixture<SessionOutComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SessionOutComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SessionOutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
