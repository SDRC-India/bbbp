import { Component, OnInit } from '@angular/core';
import { StaticPageService } from '../../services/static-page.service';
import { Constants } from '../../../../constants';
import { DomSanitizer } from '../../../../../../node_modules/@angular/platform-browser';
// import { stat } from 'fs';
declare var $: any;

@Component({
  selector: 'app-activities',
  templateUrl: './activities.component.html',
  styleUrls: ['./activities.component.scss']
})
export class ActivitiesComponent implements OnInit {
  isDesc:any;
  column :any;
  direction:number;
  searchTexts: any;
  url: any;
  staticService:StaticPageService;
  constructor(private staticServiceProvider: StaticPageService, private sanitizer: DomSanitizer) {
    this.staticService = staticServiceProvider;
   }

  ngOnInit() {
    
    this.staticService.getData("Activities").subscribe(data=>{
      this.staticService.reinitializeData(data);
  })
    
  }

  downloadFiles(fileName){  
    window.location.href = Constants.HOME_URL + 'cms/downloadCmsDoc?fileName='+fileName;
  }
  openPDF(category,title){
    this.staticService.fileTitle = title.substring(title.lastIndexOf('/') + 1);
    this.url = this.sanitizer.bypassSecurityTrustResourceUrl(Constants.HOME_URL + 'cms/downloadCmsDoc?fileName= '+category + '&inline='+ true);
    $("#myModal").modal("show");
  }


}
