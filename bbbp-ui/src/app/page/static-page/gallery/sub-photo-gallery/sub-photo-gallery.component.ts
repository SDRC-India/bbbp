import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { NgxImageGalleryComponent, GALLERY_IMAGE, GALLERY_CONF } from "ngx-image-gallery";
import { DEMO_GALLERY_CONF_INLINE } from "./const";
import { StaticPageService } from '../../services/static-page.service';
import { Constants } from '../../../../constants';
declare var $: any;


@Component({
  selector: 'app-sub-photo-gallery',
  templateUrl: './sub-photo-gallery.component.html',
  styleUrls: ['./sub-photo-gallery.component.scss']
})
export class SubPhotoGalleryComponent implements OnInit {

  subgalleryData: any;
  subgalleryDetails: any[] = [];
  subgallerySection: any = {};
  subphotoCategory: any[] = [];
  isImageGalleryOpen: Boolean = false;
  gateway=Constants.HOME_URL+Constants.CMS_URL;
  // slideIndex: number = 1;
  //  showSlides(slideIndex) : void {}

  public showConf: boolean = true;
  // get reference to gallery component
  @ViewChild('ngxImageGallery') ngxImageGallery: NgxImageGalleryComponent;

  // gallery configuration
  conf: GALLERY_CONF = DEMO_GALLERY_CONF_INLINE;

  // gallery images
  //  images: GALLERY_IMAGE[] = DEMO_GALLERY_IMAGE;
  images: GALLERY_IMAGE[] = [];

  @Input() filterBy?: string = 'all'

  visibleImages: any[];
  fileImages: any;
  imageLength: number;
  p: number = 1;
  searchTexts: any;
  staticService: StaticPageService;
  constructor(private router: Router, private staticServiceProvider: StaticPageService) {
    
    // this.visibleImages = this.imageService.getImages();
    this.staticService = staticServiceProvider;
  }

  ngOnInit() {

    if(!this.staticService.imageCategory){
      this.router.navigate(['pages/photo-gallery']);
      }
    this.images = this.staticService.imageCategory;
   this.imageLength = Object.keys( this.images[0]).length;
    this.images.forEach((value) => {
      this.fileImages = value.url;
    });
  }
 

  redirectToGallrey() {
    this.router.navigate(['pages/photo-gallery']);
  }

  // METHODS
  // open gallery
  openGallery(index) {
    // this.isImageGalleryOpen = true;
    this.ngxImageGallery.open(index);
  }

  // close gallery
  closeGallery() {
    this.ngxImageGallery.close();
  }

  // set new active(visible) image in gallery
  newImage(index: number = 0) {
    this.ngxImageGallery.setActiveImage(index);
  }

  // next image in gallery
  nextImage() {
    this.ngxImageGallery.next();
  }

  // prev image in gallery
  prevImage() {
    this.ngxImageGallery.prev();
  }

  /**************************************************/

  // EVENTS
  // callback on gallery opened
  galleryOpened(index) {
    console.info('Gallery opened at index ', index);
  }

  // callback on gallery closed
  galleryClosed() {
    this.isImageGalleryOpen = false;
    console.info('Gallery closed.');
  }

  // callback on gallery image clicked
  galleryImageClicked(index) {
    console.info('Gallery image clicked with index ', index);
  }

  // callback on gallery image changed
  galleryImageChanged(index) {
    console.info('Gallery image changed to index ', index);
  }

  openModal(index: number) {

    
    this.isImageGalleryOpen = true;
    // this.ngxImageGallery.loading=false;
    this.ngxImageGallery.opened=true;
    this.ngxImageGallery.activeImageIndex = index;
    this.ngxImageGallery.setActiveImage(index);
    document.getElementById("ngxalbum").style.display = "block";
    
    }
}
