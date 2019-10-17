import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { routing } from './snapshot.routing';
import { SnapshotViewComponent } from './snapshot-view/snapshot-view.component';
import { BarChartComponent } from './bar-chart/bar-chart.component';
import { SnapshotService } from './snapshot.service';
import { PieChartComponent } from './pie-chart/pie-chart.component';
import { StackedBarChartComponent } from './stacked-bar-chart/stacked-bar-chart.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LineChartComponent } from './line-chart/line-chart.component';
import { AreaFilterPipe } from './area-filter.pipe';

@NgModule({
  imports: [
    CommonModule,
    routing,
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [SnapshotViewComponent, BarChartComponent, PieChartComponent, StackedBarChartComponent, LineChartComponent, AreaFilterPipe],
  providers: [SnapshotService]
})
export class SnapshotModule { }
