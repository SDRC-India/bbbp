import { Routes, RouterModule } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { IndexViewComponent } from './index-view/index-view.component';


export const routes: Routes = [
    { path: 'index-view', pathMatch: 'full', component: IndexViewComponent},
  ];

export const routing: ModuleWithProviders = RouterModule.forChild(routes);


