import { Component, OnInit } from '@angular/core';
import { IMyDpOptions } from 'mydatepicker';

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.scss']
})
export class ReportComponent implements OnInit {
  
  
  public fromModel: any;
  public toModel: any;

  public mytime: Date = new Date();
  currentYear: any = this.mytime.getUTCFullYear();
  currentDate: any = this.mytime.getUTCDate() + 1;
  currentMonth: any = this.mytime.getUTCMonth() + 1; //months from 1-12

  public myDatePickerOptions: IMyDpOptions = {
    disableSince: {year: this.currentYear, month: this.currentMonth, day: this.currentDate},
    showTodayBtn: false,
    editableDateField: false,
    dateFormat: 'yyyy-mm-dd',
    yearSelector: false,
    showClearDateBtn: false
  };

  public myToDatePickerOptions: IMyDpOptions = { 
    showTodayBtn: false,
    editableDateField: false,
    dateFormat: 'yyyy-mm-dd',
    yearSelector: false,
    showClearDateBtn: false
  };

  constructor() { }

  ngOnInit() {
  }
  

}
