import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Constants } from '../../constants';

@Injectable()
export class SnapshotService {

  areaLevels: any;
  areas: any;
  constructor(private http: HttpClient) { }

  getSnapshotData(levelId, areaId){
    return this.http.get(Constants.DASHBOARD_URL + 'getSnapChatData?areaLevelId=' + levelId + '&areaId=' + areaId);
    // return this.http.get('assets/snapshotdata.json');
  }

  getAreaLevels(){
    return this.http.get(Constants.DASHBOARD_URL + 'getAllAreaLevels');
  }

  getAreas(){
    return this.http.get(Constants.DASHBOARD_URL + 'getAllArea');
  }

  downloadFullpagePdf(data){
    console.log(data);
    return this.http.post(Constants.DASHBOARD_URL + "snapShotViewPDF", data, {
      responseType: "blob"
      })
  }

  downloadFullpageExcel(data){
    console.log(data);
    return this.http.post(Constants.DASHBOARD_URL + "snapShotViewExcel", data, {
      responseType: "blob"
      })
  }
  

}
