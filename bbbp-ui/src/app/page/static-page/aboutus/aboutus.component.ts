import { Component, OnInit } from '@angular/core';

import { DomSanitizer } from '../../../../../node_modules/@angular/platform-browser';
import { Constants } from '../../../constants';
import { StaticHomeService } from '../../../service/static-home.service';

declare var $: any;

@Component({
  selector: 'app-aboutus',
  templateUrl: './aboutus.component.html',
  styleUrls: ['./aboutus.component.scss']
})

export class AboutusComponent implements OnInit {

 staticService: StaticHomeService;
 url: any;
 gateway=Constants.HOME_URL+Constants.CMS_URL;
  constructor(private staticServiceProvider: StaticHomeService,private sanitizer: DomSanitizer) {
    this.staticService = staticServiceProvider;
   }

  ngOnInit() {
    this.staticService.getData("About Us").subscribe(data=>{
      this.staticService.reinitializeData(data);
      // if(this.staticService.contentDetails[0].data[0].url == ""){
      //   $(".about-us-image").css({"display": "none"});
      // }
      // else {
      //   $(".about-us-image").css({"display": "block"});
      // }
    })
  }

  downloadFiles(fileName){  
    window.location.href = Constants.HOME_URL + 'cms/downloadCmsDoc?fileName='+fileName;
  }
  openPDF(category){
    this.url = this.sanitizer.bypassSecurityTrustResourceUrl(Constants.HOME_URL + 'cms/downloadCmsDoc?fileName= '+category + '&inline='+ true );
   
    $("#myModal").modal("show");
  }
}
 