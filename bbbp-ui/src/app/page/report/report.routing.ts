import { Routes, RouterModule } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { RawDataReportComponent } from './raw-data-report/raw-data-report.component';


export const routes: Routes = [
    { path: '', pathMatch: 'full', component: RawDataReportComponent},
  ];

export const routing: ModuleWithProviders = RouterModule.forChild(routes);


