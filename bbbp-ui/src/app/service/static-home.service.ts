import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Constants } from '../constants';
import { Router } from '@angular/router';

@Injectable()
export class StaticHomeService {
  selectedLang: string = 'english';
  viewName : String ;
  contentDetails: any[] = [];
  contentSection: any = {};
  contentData: any;
  contentList: any[] = [];
  imageCategory: any;
  whatsNewCategory:any;
  viewType: any;
  sectionName: any;
  whatnewdataSection : string;
  menus: any[];
  siteMenus: any[];
  currentURL:string='';
  lastURLSegment: string='';
  // menuName: string;
 

  constructor(private httpClient: HttpClient,private router: Router) { }

  getMenuList(menu: string){
    return this.httpClient.get(Constants.HOME_URL+Constants.PORTAL_URL + 'getPageContent?viewName=Menu&viewType='+menu);
  } 
  getData(viewName: string){
    return this.httpClient.get(Constants.HOME_URL+Constants.PORTAL_URL + 'getPageContent?viewName='+viewName + '&viewType=');
  } 

  getPhotogalleryData(viewName: string){
    return this.httpClient.get(Constants.HOME_URL+Constants.PORTAL_URL + 'fetchPhotoGallery?viewName='+viewName);
  }

  getOrganisationalData() {
    return this.httpClient.get(Constants.HOME_URL + Constants.CONTACT_URL +'getOrganisationType');
  }
 
  getViewName(){
    this.currentURL =  this.router.url;
    this.lastURLSegment = this.currentURL.substr(this.currentURL.lastIndexOf('/') + 1);
  }

  reinitializeData(data){
    this.contentData = {};
    this.contentDetails = [];
    this.contentData = data;
    this.contentDetails = this.contentData.viewContent[this.selectedLang];
    this.contentSection = {};
    // this.homeHindiDetails = this.homeData.viewContent.hindi;
    for(var i=0;i<this.contentDetails.length;i++){
      this.contentList = this.contentDetails[i].data;
    }
   
    if(this.contentData.viewName == 'Home'){
      for(var i=0;i<this.contentDetails.length;i++)
      {
        this.contentSection[this.contentDetails[i].key] = this.contentDetails[i];
      }
    }
    // if(this.contentData.viewName == 'Photo Gallery'){
    //   for(var i=0;i<this.contentDetails.length;i++)
    //   {
    //     this.contentSection[this.contentDetails[i].section] = this.contentDetails[i];
    //   }
    // }
   
    // for(var i=0;i<this.contentDetails.length;i++){
    //   this.contentGallery = this.contentDetails[i].data;
    // }
  }

}



