import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { RawDataReportComponent } from './raw-data-report/raw-data-report.component';
import { routing } from './report.routing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MyDatePicker, MyDatePickerModule } from 'mydatepicker';
import { AreaFilterPipe } from './area-filter.pipe';
import { ReportService } from './report.service';

@NgModule({
  imports: [
    CommonModule,
    routing,
    FormsModule,
    ReactiveFormsModule,
    MyDatePickerModule
  ],
  declarations: [RawDataReportComponent, AreaFilterPipe],
  providers: [ReportService]
})
export class ReportModule { }
