import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ResourcesGuidelinesComponent } from './resources-guidelines.component';

describe('ResourcesGuidelinesComponent', () => {
  let component: ResourcesGuidelinesComponent;
  let fixture: ComponentFixture<ResourcesGuidelinesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ResourcesGuidelinesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ResourcesGuidelinesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
