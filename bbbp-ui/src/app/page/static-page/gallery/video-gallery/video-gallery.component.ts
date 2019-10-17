import { Component, OnInit } from '@angular/core';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { StaticPageService } from '../../services/static-page.service';
import { document } from 'angular-bootstrap-md/utils/facade/browser';
declare var $: any;

@Component({
  selector: 'app-video-gallery',
  templateUrl: './video-gallery.component.html',
  styleUrls: ['./video-gallery.component.scss']
})
export class VideoGalleryComponent implements OnInit {

  videoGalleryData: any;
  videoGalleryDetails: any[] = [];
  videoGallerySection: any = {};
  videoGallery: any[] = [];
  p: number = 1;
  link: SafeResourceUrl;
  title: any;
  searchTexts: any;
  selectionFields: any;
  filteredGalleryDetails: any[] = [];
  staticService: StaticPageService;
  constructor(private staticServiceProvider: StaticPageService, private sanitizer: DomSanitizer) {
    this.staticService = staticServiceProvider;
  }

  ngOnInit() {
    this.staticService.getDataEntryTimeperiodSelection().subscribe(data => {
      this.selectionFields = data;
    });
    this.staticService.getData("Video Gallery").subscribe(data => {
      this.staticService.reinitializeData(data);
      this.filteredGalleryDetails = this.staticService.contentList;
    })
  }

  openVideo(category) {
    let id = category.link.split("=")[1];
    document.getElementById('videoIframe').src = 'https://www.youtube.com/embed/' + id;
    // this.url = this.sanitizer.bypassSecurityTrustResourceUrl('https://www.youtube.com/embed/' +id);
    // this.url = category.url.split("=")[1];
    this.title = category.title;
    $("#openVideo").modal("show");
  }

  getId(link) {
    return link.split("=")[1];
  }


  resetVal(modelVal){
    modelVal.forEach(element => {
      if(element.label == "Select Year"){
        element.value = undefined;
      }
      if(element.label == "Select Quarter"){
       element.value = undefined;
     }
    });
    this.staticService.getData("Video Gallery").subscribe(data => {
      this.staticService.reinitializeData(data);
      this.filteredGalleryDetails = this.staticService.contentList;
    })
   }

  selectDropdown(selectedOption, model, index) {
    this.selectionFields[index].errorFound = false;
    this.selectionFields[index].errorMessage = "";
    this.selectionFields[index].value = selectedOption.value;
    this.selectionFields[index].key = selectedOption.key;
    this.filteredGalleryDetails = [];

    this.staticService.contentList.forEach(gd => {

      if ((gd.year && gd.quarter && (gd.year=== model[0].value) && gd.quarter === model[1].value) ||
        (!model[1].value && gd.year === selectedOption.value)
        || (!model[0].value && gd.quarter === selectedOption.value)) {
        this.filteredGalleryDetails.push(gd);
      }

    })
    console.log(this.filteredGalleryDetails);
    
  }
}
