import { Component, OnInit } from '@angular/core';
import { StaticHomeService } from '../../../service/static-home.service';
import { Router } from '../../../../../node_modules/@angular/router';
import { AppService } from '../../../app.service';

@Component({
  selector: 'app-sitemap',
  templateUrl: './sitemap.component.html',
  styleUrls: ['./sitemap.component.scss']
})
export class SitemapComponent implements OnInit {
  router:Router;
  app: any;
   staticService:StaticHomeService;
  constructor( router:Router,private appService: AppService,private staticServiceProviders: StaticHomeService) { 
    this.staticService = staticServiceProviders;
    this.app = appService;
  }

  ngOnInit() {
    this.staticService.getMenuList('portalMenu').subscribe( data => {
      this.staticService.siteMenus = <any[]> data['viewContent']['english'];
    })
   
   
  }

}
