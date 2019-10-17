import { Component, OnInit } from '@angular/core';
import { FormGroup, NgForm, FormBuilder, FormControl } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { ReportService } from '../report.service';
import { Constants } from '../../../constants';
import { Cookie } from 'ng2-cookies';
import saveAs from 'save-as';

declare var $;

@Component({
  selector: 'app-raw-data-report',
  templateUrl: './raw-data-report.component.html',
  styleUrls: ['./raw-data-report.component.scss']
})
export class RawDataReportComponent implements OnInit {

  selectedState: any = {}; selectedStateName: string;
  selectedDistrict: any = {}; selectedDistrictName: string;
  selectedTimeperiod: any = {}; selectedTimeperiodName: string;

  allStates = {
    "areaName": "All States",
    "areaId": "All"
  }
  allDistricts = {
    "areaName": "All Districts",
    "areaId": "All"
  }
  reportService: ReportService;

  constructor(private reportProvider: ReportService) {
    this. reportService = reportProvider;
  }

  ngOnInit(){
    this.getAllArea();
    this.getAllTimeperiod();
  }

  getAllArea(){
    this.reportService.getAreas().subscribe(res => {
      this.reportService.areas = res;
    })
  }

  getAllTimeperiod(){
    this.reportService.getTimeperiods().subscribe(res => {
      this.reportService.timeperiods = res;
    })
  }

  selectState(opt) {
    this.selectedState = opt;
    this.selectedStateName = opt.areaName;
    this.selectedDistrict = {};
    this.selectedDistrictName = '';
  }

  selectDistrict(opt) {
    this.selectedDistrict = opt;
    this.selectedDistrictName = opt.areaName;
  }

  selectTimeperiod(opt) {
    this.selectedTimeperiod = opt;
    this.selectedTimeperiodName = opt.timeperiod;
  }

  downloadReport() {
    if (this.selectedState.areaId === 'All') {
      this.selectedDistrict.areaId = 'All';
    }
    if (!this.selectedState.areaId) {
      this.selectedState.areaId = 'Null';
    }
    this.reportService.downloadRawDataReport(this.selectedState.areaId, this.selectedDistrict.areaId)
    .subscribe(res => {
      let name = (this.selectedStateName ? this.selectedStateName + "_" : '') + this.selectedDistrictName + "_" + new Date().getTime().toString();
      saveAs(res, name + ".xls");
      this.selectedState = {}; this.selectedStateName = '';
  this.selectedDistrict = {}; this.selectedDistrictName = '';
  this.selectedTimeperiod = {}; this.selectedTimeperiodName = '';
    })
  }
}