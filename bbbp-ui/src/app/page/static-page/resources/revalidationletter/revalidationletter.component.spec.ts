import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RevalidationletterComponent } from './revalidationletter.component';

describe('RevalidationletterComponent', () => {
  let component: RevalidationletterComponent;
  let fixture: ComponentFixture<RevalidationletterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RevalidationletterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RevalidationletterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
