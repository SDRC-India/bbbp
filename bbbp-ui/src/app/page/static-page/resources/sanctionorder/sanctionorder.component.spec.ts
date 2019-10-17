import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SanctionorderComponent } from './sanctionorder.component';

describe('SanctionorderComponent', () => {
  let component: SanctionorderComponent;
  let fixture: ComponentFixture<SanctionorderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SanctionorderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SanctionorderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
