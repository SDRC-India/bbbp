import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Constants } from '../../../constants';
import { StaticHomeService } from '../../../service/static-home.service';
import { DomSanitizer } from '../../../../../node_modules/@angular/platform-browser';
declare var $: any;

@Component({
  selector: 'app-whatsnewcontent',
  templateUrl: './whatsnewcontent.component.html',
  styleUrls: ['./whatsnewcontent.component.scss']
})
export class WhatsnewcontentComponent implements OnInit {

  whatnewcontentData: any;
  whatnewcontentDetails: any[] = [];
  whatnewcontentSection: any[] = [];
  isDesc: any;
  column: any;
  direction: number;
  url: any;
  gateway=Constants.HOME_URL+Constants.CMS_URL;
  staticService: StaticHomeService;
  constructor(private router: Router,
    private statichomeService: StaticHomeService,private sanitizer: DomSanitizer) {
    this.staticService = statichomeService;
  }

  ngOnInit() {
    if (!this.statichomeService.whatsNewCategory) {
      this.router.navigate(['pages/whatsnew']);
    }
  }
  redirectToWhatsNew() {
    this.router.navigate(['pages/whatsnew']);
  }

  showImage(){
    $("#previewImageModal").modal("show");
  }
  downloadFiles(fileName) {
    window.location.href = Constants.HOME_URL + 'cms/downloadCmsDoc?fileName=' + fileName;
  }
  previewImage(){
    $("#previewImageModal").modal("show");
  }
  
  openPDF(category){
    this.url = this.sanitizer.bypassSecurityTrustResourceUrl(Constants.HOME_URL + 'cms/downloadCmsDoc?fileName= '+category + '&inline='+ true);
    $("#myModal").modal("show");
  }
}
