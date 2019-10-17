import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DataEntrySelectionComponent } from './data-entry-selection.component';

describe('DataEntrySelectionComponent', () => {
  let component: DataEntrySelectionComponent;
  let fixture: ComponentFixture<DataEntrySelectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DataEntrySelectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DataEntrySelectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
