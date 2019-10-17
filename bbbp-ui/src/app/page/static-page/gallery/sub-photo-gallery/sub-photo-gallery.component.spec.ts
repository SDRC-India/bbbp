import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SubPhotoGalleryComponent } from './sub-photo-gallery.component';

describe('SubPhotoGalleryComponent', () => {
  let component: SubPhotoGalleryComponent;
  let fixture: ComponentFixture<SubPhotoGalleryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SubPhotoGalleryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SubPhotoGalleryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
