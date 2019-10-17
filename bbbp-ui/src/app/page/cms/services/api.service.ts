import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Constants } from '../../../constants';

@Injectable()
export class ApiService {

  cmsData: any;
  constructor(private http: HttpClient) { }

  getResourceQuestionList(){
    return this.http.get(Constants.HOME_URL + Constants.CMS_URL+'fetchFeatureDetails')
    // return this.http.get('assets/getQuestions.json')
  }
  getCmsLandingPageData(){
    return this.http.get(Constants.HOME_URL + Constants.PORTAL_URL + 'fetchLandingPageData');
  }
  getMenuList(){
    return this.http.get(Constants.HOME_URL + Constants.PORTAL_URL +'getPageContent?viewName=Menu&viewType=cmsMenu')
  }

  getResourceQuestionListByViewName(viewName){
    return this.http.get(Constants.HOME_URL + Constants.CMS_URL+'fetchFeatureDetails?viewName='+viewName)
    // return this.http.get('assets/getQuestions.json')
  }
  getVistorCount(){
    return this.http.get(Constants.HOME_URL + Constants.PORTAL_URL + 'fetchVistorCount');
  }
  
}
