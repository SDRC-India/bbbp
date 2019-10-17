import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { AppService } from '../../app.service';
import { StaticHomeService } from '../../service/static-home.service';

declare var $: any;

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  whatnew:string = "What's New";
  homeHindiDetails : any[] = [];
  appService: AppService;
  staticService: StaticHomeService;
  constructor(private app: AppService, private staticServiceProvider: StaticHomeService, private router: Router) {

    this.appService = app;
    this.staticService = staticServiceProvider;
   }

  ngOnInit(){
     this.staticService.getData("Home").subscribe(data=>{
      this.staticService.reinitializeData(data);
    })
  }

  getNewsContent(item){
    this.staticService.whatsNewCategory = item;
     this.router.navigate(['pages/whatsnewcontent']);
    //window.open('whatsnewcontent');
  }

}