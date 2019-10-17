import { ModuleWithProviders } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ContentSectionComponent } from './content-section/content-section.component';
import { ResourcesGuidelinesComponent } from './resources-guidelines/resources-guidelines.component';


export const routes: Routes = [
    { path: 'guidelines', pathMatch: 'full', component: ResourcesGuidelinesComponent},
    { path: '', pathMatch: 'full', component: ContentSectionComponent},
  ];

export const routing: ModuleWithProviders = RouterModule.forChild(routes);
