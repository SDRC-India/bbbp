import { CommonModule } from '@angular/common';
import { NgModule, NO_ERRORS_SCHEMA } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TooltipModule } from 'ng2-tooltip-directive';

import { routing } from './dashboard.routing';
import { HorizontalBarChartComponent } from './horizontal-bar-chart/horizontal-bar-chart.component';
import { IndexViewComponent } from './index-view/index-view.component';
import { DashboardService } from './services/dashboard.service';
import { ThematicMapComponent } from './thematic-map/thematic-map.component';
import { TrendChartComponent } from './trend-chart/trend-chart.component';
import { HorizontalBarComponent } from './horizontal-bar/horizontal-bar.component';
import { TableModule } from '../../../../lib/public_api';
import { ConvertToTableFormatPipe } from './convert-to-table-format.pipe';



@NgModule({
  imports: [
    CommonModule,
    routing,
    FormsModule,
    ReactiveFormsModule,
    TooltipModule,
    TableModule
  ],
  declarations: [IndexViewComponent, ThematicMapComponent, TrendChartComponent, HorizontalBarChartComponent, HorizontalBarComponent, ConvertToTableFormatPipe],
  providers: [DashboardService],
  schemas: [ NO_ERRORS_SCHEMA ]
})
export class DashboardModule { }
