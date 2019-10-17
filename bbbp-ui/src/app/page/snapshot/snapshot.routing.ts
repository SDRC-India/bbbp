import { Routes, RouterModule } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { SnapshotViewComponent } from './snapshot-view/snapshot-view.component';


export const routes: Routes = [
    { path: '', pathMatch: 'full', component: SnapshotViewComponent},
  ];

export const routing: ModuleWithProviders = RouterModule.forChild(routes);


