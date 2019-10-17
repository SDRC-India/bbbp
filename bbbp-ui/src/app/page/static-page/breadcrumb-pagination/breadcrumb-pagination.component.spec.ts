import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BreadcrumbPaginationComponent } from './breadcrumb-pagination.component';

describe('BreadcrumbPaginationComponent', () => {
  let component: BreadcrumbPaginationComponent;
  let fixture: ComponentFixture<BreadcrumbPaginationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BreadcrumbPaginationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BreadcrumbPaginationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
