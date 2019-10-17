import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DataEntryHeadComponent } from './data-entry-head.component';

describe('DataEntryHeadComponent', () => {
  let component: DataEntryHeadComponent;
  let fixture: ComponentFixture<DataEntryHeadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DataEntryHeadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DataEntryHeadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
