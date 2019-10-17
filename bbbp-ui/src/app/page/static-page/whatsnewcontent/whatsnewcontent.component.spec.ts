import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WhatsnewcontentComponent } from './whatsnewcontent.component';

describe('WhatsnewcontentComponent', () => {
  let component: WhatsnewcontentComponent;
  let fixture: ComponentFixture<WhatsnewcontentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WhatsnewcontentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WhatsnewcontentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
