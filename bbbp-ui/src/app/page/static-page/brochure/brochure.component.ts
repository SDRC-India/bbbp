import { Component, OnInit } from '@angular/core';

import { DomSanitizer } from '../../../../../node_modules/@angular/platform-browser';
import { Constants } from '../../../constants';
import { StaticPageService } from '../services/static-page.service';

declare var $: any;

@Component({
  selector: 'app-brochure',
  templateUrl: './brochure.component.html',
  styleUrls: ['./brochure.component.scss']
})
export class BrochureComponent implements OnInit {
  isDesc : any;
  column : any;
  direction: number;
  p: number = 1;
  url: any;
  searchTexts: any;
  staticService: StaticPageService;
  constructor(private staticServiceProvider:StaticPageService, private sanitizer: DomSanitizer) {
    this.staticService = staticServiceProvider;
   }
  ngOnInit() {
    this.staticService.getData("Brochures").subscribe(data=>{
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

  sort(property){
    this.isDesc = !this.isDesc; //change the direction    
    this.column = property;
    let direction = this.isDesc ? 1 : -1;
  
    this.staticService.contentList.sort(function(a, b){
        if(new Date(a[property].replace( /(\d{2})-(\d{2})-(\d{4})/, "$2/$1/$3")) < new Date(b[property].replace( /(\d{2})-(\d{2})-(\d{4})/, "$2/$1/$3"))){
            return -1 * direction;
        }
        else if(new Date(a[property].replace( /(\d{2})-(\d{2})-(\d{4})/, "$2/$1/$3")) > new Date(b[property].replace( /(\d{2})-(\d{2})-(\d{4})/, "$2/$1/$3"))){
            return 1 * direction;
        }
        else{
            return 0;
        }
    });
  };

}
