import { Component, OnInit } from '@angular/core';

import { DomSanitizer } from '../../../../../node_modules/@angular/platform-browser';
import { Router } from '../../../../../node_modules/@angular/router';
import { Constants } from '../../../constants';
import { StaticPageService } from '../services/static-page.service';

declare var $: any;

@Component({
  selector: 'app-achievements',
  templateUrl: './achievements.component.html',
  styleUrls: ['./achievements.component.scss']
})
export class AchievementsComponent implements OnInit {
  isDesc:any;
  column :any;
  direction:number;
  searchTexts: any;
  url: any;
  p: number = 1;
  staticService:StaticPageService;
  constructor(private staticServiceProvider: StaticPageService, private router: Router,
  private sanitizer: DomSanitizer) {
    this.staticService = staticServiceProvider;
   }

  ngOnInit() {
    this.staticService.getData("Achievement and Progress").subscribe(data=>{
      this.staticService.reinitializeData(data);
  })
  }
  redirectToWhatsNew() {
    this.router.navigate(['/']);
  }
  downloadFiles(fileName){  
    window.location.href = Constants.HOME_URL + 'cms/downloadCmsDoc?fileName='+fileName;
  }
  openPDF(category){
    this.url = this.sanitizer.bypassSecurityTrustResourceUrl(Constants.HOME_URL + 'cms/downloadCmsDoc?fileName= '+category + '&inline='+ true);
    $("#myModal").modal("show");
  }
 
}
