import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService } from '../../cms/services/api.service';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit {
  landingPagedata: any;
  lastUpdatedDate: any;
  router:Router;
  visitorCount:any;

  constructor(router:Router, private apiService: ApiService) {
    this.router = router;
   }

  ngOnInit() {
    this.apiService.getCmsLandingPageData().subscribe(res => {
      this.landingPagedata = res;
      this.lastUpdatedDate = this.landingPagedata.cMSRecentContent.lastUpdateOn;
    });

    this.apiService.getVistorCount().subscribe(res => {
      this.visitorCount = res;
    });
  }
  

}
