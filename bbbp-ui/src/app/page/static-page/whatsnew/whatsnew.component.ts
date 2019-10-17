import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { StaticHomeService } from '../../../service/static-home.service';

@Component({
  selector: 'app-whatsnew',
  templateUrl: './whatsnew.component.html',
  styleUrls: ['./whatsnew.component.scss']
})
export class WhatsnewComponent implements OnInit {
  whatnewData:any;
  whatnewDetails: any[] = [];
  whatnewSection: any[] = [];
  isDesc:any;
  column :any;
  direction:number;
  searchTexts: any;
  p: number = 1;
  staticService:StaticHomeService;
  constructor(private staticServiceProvider: StaticHomeService, private router: Router) {
    this.staticService = staticServiceProvider;
   }

  ngOnInit() {
    
    this.staticService.getData("What's New").subscribe(data=>{
      this.whatnewData =  data;
      this.staticService.reinitializeData(data);
  })
  }
  redirectToWhatsNew() {
    this.router.navigate(['/']);
  }
  getNewsContent(item){
    this.staticService.whatsNewCategory=item;
    this.router.navigate(['pages/whatsnewcontent']);
  }


}
