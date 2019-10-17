import { Component, OnInit } from '@angular/core';
import { SnapshotService } from '../snapshot.service';
import html2canvas from 'html2canvas';
import saveAs from 'save-as'
import { SdrcLoaderService } from '../../../../../lib/loader/src/sdrc-loader/sdrc-loader.service';
declare var $: any;

@Component({
  selector: 'app-snapshot-view',
  templateUrl: './snapshot-view.component.html',
  styleUrls: ['./snapshot-view.component.scss']
})
export class SnapshotViewComponent implements OnInit {

  snapshotService: SnapshotService;
  snapshotData: any;
  kpiIndexList: number[];
  areaLevel:any = {}; areaLevelName: string;
  selectedState:any = {}; selectedStateName: string;
  selectedDistrict:any = {}; selectedDistrictName: string;
  snapshotTimeperiod: any;
  
  selectedChart: string;
  constructor(private snapshotProvider: SnapshotService, private loader: SdrcLoaderService) {
    this.snapshotService = snapshotProvider;
   }

  ngOnInit() {
    this.getAllAreaLevels();
    this.getAllArea();
    this.getSnapshotData(1, 1);
  }

  getAllAreaLevels(){
    this.snapshotService.getAreaLevels().subscribe(res => {
      this.snapshotService.areaLevels = res;
      this.selectAreaLevel(this.snapshotService.areaLevels.filter(el => el.id === 1)[0]);
    });
  }

  getAllArea(){
    this.snapshotService.getAreas().subscribe(res => {
      this.snapshotService.areas = res;
    })
  }

  getSnapshotData(levelId, areaId){
    this.snapshotService.getSnapshotData(levelId, areaId).subscribe(res => {
      this.snapshotTimeperiod = res['timePeriod'];
      this.snapshotData = res['snapshotData'];
    })
  }

  getKpiIndexList(kpiListLength){
    let indexArray=[];
    for (let i = 0; i < Math.ceil(kpiListLength/2); i++) {
      indexArray.push(i)
    }
    return indexArray;
  }

  selectAreaLevel(opt){
    this.areaLevel = opt;
    this.areaLevelName = opt.keyValue;
    this.selectedState = {};
    this.selectedStateName = '';
    this.selectedDistrict = {};
    this.selectedDistrictName = '';
  }

  selectState(opt){
    this.selectedState = opt;
    this.selectedStateName = opt.areaName;
    this.selectedDistrict = {};
    this.selectedDistrictName = '';
  }

  selectDistrict(opt){
    this.selectedDistrict = opt;
    this.selectedDistrictName = opt.areaName;
  }

  selectChart(chart, chartName){
    chart.selectedChart = chartName;
  }

  getStackKeys(dataArr){
    let allKeys = Object.keys(dataArr[0]);
    allKeys.splice(allKeys.indexOf("axis"), 1);
    return allKeys;
  }

  removeReference(data){
    return JSON.parse(JSON.stringify(data));
  }

  convertToLineChartFormat(chartData) {
    for (let i = 0; i < chartData.length; i++) {
      const el = chartData[i];
      el.timeperiod = el.axis ? el.axis: el.timeperiod;
    }
    return chartData;
  }

  isAvailable(arr, el) {
    if (arr.indexOf(el) !== -1) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Download each image on click
   * @param el 
   * @param id 
   * @param indicatorName 
   */
  downloadChartToImage(el, id, indicatorName) {
    $('.download-chart').css('display', "none");
    html2canvas(document.getElementById(id)).then((canvas) => {
      canvas.toBlob((blob)=> {
        $('.download-chart').css('display', "block");
        saveAs(blob, indicatorName + ".jpg");
      });
    });
  }

  /**
   * Download full page content as pdf
   * @param districtName 
   * @param blockName 
   */
  async downloadAllChartsToPdfExcel(format) {
    let data = {
      areaLevelId: this.areaLevel.id ? this.areaLevel.id : null,
      areaLevelName: this.areaLevelName ? this.areaLevelName : null,
      stateId: this.selectedState.areaId ? this.selectedState.areaId : null,
      stateName: this.selectedStateName ? this.selectedStateName : null,
      districtId: this.selectedDistrict.areaId ? this.selectedDistrict.areaId : null,
      districtName: this.selectedDistrictName ? this.selectedDistrictName : null,
      timeperiod: this.snapshotTimeperiod ? this.snapshotTimeperiod: null,
      svgs: null
    }
    let barccards : any = [];
    let divIds = $(".snapshot-repeat").map(function () {
      if (this.id) {
        return this.id;
      }
    }).get();
    let base64Card: any = {};
    this.loader.show();
    $('.download-chart').css('display', "none");
    for (let i = 0; i < divIds.length; i++) {
     await html2canvas(document.getElementById(divIds[i])).then( (canvas)=> {
      // const context = canvas.getContext("2d");
      // var data = context.getImageData(0, 0, canvas.width, canvas.height);
      // // store the current globalCompositeOperation
      // var compositeOperation = context.globalCompositeOperation;
      // // set to draw behind current content
      // context.globalCompositeOperation = "destination-over";
      // //set background color
      // context.fillStyle = "#F00";
      // // draw background/rectangle on entire canvas
      // context.fillRect(0,0,canvas.width,canvas.height);

      // var tempCanvas = document.createElement("canvas"),
      //     tCtx = tempCanvas.getContext("2d");

      // tempCanvas.width = canvas.width - 2;
      // tempCanvas.height = canvas.height;

      // tCtx.drawImage(canvas,0,0);

      const base64encodedstring = (canvas.toDataURL('image/jpg', 1));
        if (base64Card[$('#' + divIds[i]).attr('sector')]) {
          if (base64Card[$('#' + divIds[i]).attr('sector')][$('#' + divIds[i]).attr('subsector')]) {
            base64Card[$('#' + divIds[i]).attr('sector')][$('#' + divIds[i]).attr('subsector')].push($('#' + divIds[i]).attr('grpId') + "@_@" + base64encodedstring);
          }else {
            base64Card[$('#' + divIds[i]).attr('sector')][$('#' + divIds[i]).attr('subsector')] = [$('#' + divIds[i]).attr('grpId') + "@_@" + base64encodedstring];
          }
        }else {
          base64Card[$('#' + divIds[i]).attr('sector')] = {};
          base64Card[$('#' + divIds[i]).attr('sector')][$('#' + divIds[i]).attr('subsector')] = [$('#' + divIds[i]).attr('grpId') + "@_@" + base64encodedstring];
        }
          
    })
    }
    data.svgs = base64Card;
    let areaName = data.districtName ? data.districtName: data.stateName ? data.stateName: "India";
    $('.download-chart').css('display', "block");
    if(format == 'pdf'){
      this.snapshotService.downloadFullpagePdf(data).subscribe(res => {
        saveAs(res, 'bbbp_'+ areaName +'_'+ new Date().getTime().toString() + '.pdf');
      });
    }else {
      this.snapshotService.downloadFullpageExcel(data).subscribe(res => {
        saveAs(res, 'bbbp_' + areaName +'_'+ new Date().getTime().toString() + '.xlsx');
      });
    }
    
  }

  

  removeWhiteSpace(val: string){
    return val.replace(/\s/g,'').replace(/[({:,?})&/]/g,"");;
  }


  ngAfterViewInit() {
    $("body").tooltip({ selector: '[data-toggle=tooltip]' });
  }

}
