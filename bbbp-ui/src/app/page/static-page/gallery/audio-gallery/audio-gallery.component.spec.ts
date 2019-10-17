import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AudioGalleryComponent } from './audio-gallery.component';

describe('AudioGalleryComponent', () => {
  let component: AudioGalleryComponent;
  let fixture: ComponentFixture<AudioGalleryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AudioGalleryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AudioGalleryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
