import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Constants } from '../../../constants';
import { ThematicIndicatorModel } from '../models/thematic-indicator-model';
import { ThematicSectorModel } from '../models/thematic-sector-model';
import { ThematicSubSectorModel } from '../models/thematic-sub-sector-model';
import { ThematicTimeperiodModel } from '../models/thematic-timeperiod-model';

@Injectable()
export class DashboardService {

  thematicDropDownList: {sectors: [ThematicSectorModel], timeperiods: [ThematicTimeperiodModel]};
  timeperiods:ThematicTimeperiodModel[];
  thematicData: any;
  isMapLoading: boolean;
  mapData: any;
  allStates: any;
  selectedMapDataPeriod: any;
  selectedTimeperiod: ThematicTimeperiodModel = new ThematicTimeperiodModel();
  selectedSector: ThematicSectorModel = new ThematicSectorModel();
  selectedSubSector: ThematicSubSectorModel = new ThematicSubSectorModel()
  selectedIndicator: ThematicIndicatorModel = new ThematicIndicatorModel();
  submittedTimeperiod: ThematicTimeperiodModel = new ThematicTimeperiodModel();
  submittedSector: ThematicSectorModel = new ThematicSectorModel();
  submittedSubSector: ThematicSubSectorModel = new ThematicSubSectorModel()
  submittedIndicator: ThematicIndicatorModel = new ThematicIndicatorModel();

  trendChartData: any = [];
  districtData: any;
  selectedMapState: any;
  selectedMapStateId: number;
  constructor(private httpClient: HttpClient) { }

  getcsrData(){
    return this.httpClient.get<any>(Constants.DASHBOARD_URL + 'csrMapView')
  }
  getThematicData(indicatorId){
    return this.httpClient.get<any>(Constants.HOME_URL +'dashboard/thematicView?indicatorId='+ indicatorId)
  }

  getThematicDropDownList(){
    return this.httpClient.get<any>(Constants.HOME_URL + "dashboard/getSectorsSubsectorIndecators")
  }
  getAllStates(){
    return this.httpClient.get<any>(Constants.HOME_URL + "dashboard/getAllStateData")
  }

  downloadThematicPdf(data){
    return this.httpClient.post(Constants.HOME_URL + "dashboard/exportThematicViewPDF", data, {
      responseType: "blob"
      })
  }

  downloadThematicExcel(data){
    return this.httpClient.post(Constants.HOME_URL + "dashboard/exportThematicViewExcel", data, {
      responseType: "blob"
      })
  }


  getTrendChartData(areaNID){
    let id;
    if(this.submittedSector.sectorId == null){
      id = 45
    }
    else{
      id = this.submittedIndicator.indicatorId
    }
    return this.httpClient.get(Constants.HOME_URL + "dashboard/csrTrendView?areaId="+areaNID + "&&indicatorId="+ id)
  }

  getDistrictData(indicatorId, timeperiodId, areaId){
    return this.httpClient.get(Constants.HOME_URL + "dashboard/getThematicView?indicatorId=" + indicatorId +
     '&timePeriodId=' + timeperiodId + '&areaId=' + areaId)
  }

  dragElement(elmnt) {
    var pos1 = 0, pos2 = 0, pos3 = 0, pos4 = 0;
    if (document.getElementById(elmnt.id + "header")) {
      /* if present, the header is where you move the DIV from:*/
      document.getElementById(elmnt.id + "header").onmousedown = dragMouseDown;
    } else {
      /* otherwise, move the DIV from anywhere inside the DIV:*/
      elmnt.onmousedown = dragMouseDown;
    }
  
    function dragMouseDown(e) {
      e = e || window.event;
      e.preventDefault();
      // get the mouse cursor position at startup:
      pos3 = e.clientX;
      pos4 = e.clientY;
      document.onmouseup = closeDragElement;
      // call a function whenever the cursor moves:
      document.onmousemove = elementDrag;
    }
  
    function elementDrag(e) {
      e = e || window.event;
      e.preventDefault();
      // calculate the new cursor position:
      pos1 = pos3 - e.clientX;
      pos2 = pos4 - e.clientY;
      pos3 = e.clientX;
      pos4 = e.clientY;
      // set the element's new position:
      elmnt.style.top = (elmnt.offsetTop - pos2) + "px";
      elmnt.style.left = (elmnt.offsetLeft - pos1) + "px";
    }
  
    function closeDragElement() {
      /* stop moving when mouse button is released:*/
      document.onmouseup = null;
      document.onmousemove = null;
    }
  }
  isEqual(value, other) {

    // Get the value type
    var type = Object.prototype.toString.call(value);
  
    // If the two objects are not the same type, return false
    if (type !== Object.prototype.toString.call(other)) return false;
  
    // If items are not an object or array, return false
    if (['[object Array]', '[object Object]'].indexOf(type) < 0) return false;
  
    // Compare the length of the length of the two items
    var valueLen = type === '[object Array]' ? value.length : Object.keys(value).length;
    var otherLen = type === '[object Array]' ? other.length : Object.keys(other).length;
    if (valueLen !== otherLen) return false;
  
    // Compare two items
    var compare =  (item1, item2) => {
  
      // Get the object type
      var itemType = Object.prototype.toString.call(item1);
  
      // If an object or array, compare recursively
      if (['[object Array]', '[object Object]'].indexOf(itemType) >= 0) {
        if (!this.isEqual(item1, item2)) return false;
      }
  
      // Otherwise, do a simple comparison
      else {
  
        // If the two items are not the same type, return false
        if (itemType !== Object.prototype.toString.call(item2)) return false;
  
        // Else if it's a function, convert to a string and compare
        // Otherwise, just compare
        if (itemType === '[object Function]') {
          if (item1.toString() !== item2.toString()) return false;
        } else {
          if (item1 !== item2) return false;
        }
  
      }
    };
  
    // Compare properties
    if (type === '[object Array]') {
      for (var i = 0; i < valueLen; i++) {
        if (compare(value[i], other[i]) === false) return false;
      }
    } else {
      for (var key in value) {
        if (value.hasOwnProperty(key)) {
          if (compare(value[key], other[key]) === false) return false;
        }
      }
    }
  
    // If nothing failed, return true
    return true;
  
  };
  
}
