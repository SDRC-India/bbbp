import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Constants } from '../../../constants';
import { Router } from '@angular/router';

@Injectable()
export class StaticPageService {
  selectedLang: string = 'english';
  viewName : String ;
  contentDetails: any[] = [];
  contentSection: any = {};
  contentData: any;
  contentList: any[] = [];
  imageCategory: any;
  imageSection: string;
  whatsNewCategory:any;
  viewType: string;
  sectionName: any;
  whatnewdataSection : string;
  shortDescription: string;
  noDataFound: Boolean = false;
  fileTitle: string;
  nestedSection: any[] = [];
  keys: string[] = [];
  useAreaDetails: any;
  currentURL:string='';
  lastURLSegment: string='';

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
  

  getSubPhotogalleryData(subPhoto){
    return this.httpClient.post(Constants.HOME_URL+Constants.PORTAL_URL + 'fetchAlbumPhotos', subPhoto);
  }

  getAreaDetails(){
    return this.httpClient.get(Constants.HOME_URL + 'getAllArea');
  }

  getDataEntryTimeperiodSelection(){    
    return this.httpClient.get(Constants.HOME_URL + 'getAllPeriodReference?isUser='+true)
  }
  getViewName(){
    this.currentURL =  this.router.url;
    this.lastURLSegment = this.currentURL.substr(this.currentURL.lastIndexOf('/') + 1);
  }
  reinitializeData(data){

    this.nestedSection = [];
    this.contentDetails = [];
    this.contentSection = {};
    this.noDataFound = false;


    this.contentData = data;
    this.contentDetails = this.contentData.viewContent[this.selectedLang].slice().reverse();
    this.shortDescription = this.contentData.viewType;
    this.viewName = this.contentData.viewName;
    
    // this.homeHindiDetails = this.homeData.viewContent.hindi;
    for(var i=0;i<this.contentDetails.length;i++){
      this.contentList = this.contentDetails[i].data;
        this.nestedSection.push(this.contentDetails[i].data);
    }
    this.contentList = this.contentList.slice().reverse();
    // this.nestedSection = this.nestedSection.slice().reverse();
   
    this.keys = Object.keys(this.contentList);
     if(Object.keys(this.contentList[0]).length === 0){
        this.noDataFound = true;
      }else{
        this.noDataFound = false;
      } 
    if(this.contentData.viewName == 'Home'){
      for(var i=0;i<this.contentDetails.length;i++)
      {
        this.contentSection[this.contentDetails[i].key] = this.contentDetails[i];
      }
    }
  }

}
