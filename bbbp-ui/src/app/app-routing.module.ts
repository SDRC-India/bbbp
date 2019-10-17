
import { AuthGuard } from './guard/auth.guard';
import { RoleGuardService } from './guard/role-guard.service' 
import { NgModule, NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './page/home/home.component';
import { LoginComponent } from './page/login/login.component';
import { UserManagementComponent } from './page/user-module/user-management/user-management.component';
import { DataEntryHeadComponent } from './page/data-entry-module/data-entry-head/data-entry-head.component';
import { InstitutionalComponent } from './page/data-entry-module/institutional/institutional.component';
import { GirlChildSurvivalComponent } from './page/data-entry-module/girl-child-survival/girl-child-survival.component';
import { Exception404Component } from './page/exception404/exception404.component';
import { LoggedinGuardService } from './guard/loggedin-guard.service';
import { ChangePasswordComponent } from './page/user-module/change-password/change-password.component';
import { TrainingComponent } from './page/data-entry-module/training/training.component';
import { AwarenessComponent } from './page/data-entry-module/awareness/awareness.component';
import { DataEntrySelectionComponent } from './page/data-entry-module/data-entry-selection/data-entry-selection.component'
import { DraftsComponent } from './page/drafts/drafts.component';
import { ResetPasswordComponent } from './page/user-module/reset-password/reset-password.component';
import { SitemapComponent } from './page/static-page/sitemap/sitemap.component';
import { UploadCsrComponent } from './page/user-module/upload-csr/upload-csr.component';
import { SessionOutComponent } from './page/session-out/session-out.component';

const routes: Routes = [

  {
    path: '',
    component: HomeComponent,
    pathMatch: 'full'
  },
  {
    path: 'login',
    component: LoginComponent,
    pathMatch: 'full',
    canActivate: [LoggedinGuardService]
  },
  {
    path: 'sitemap',
    component: SitemapComponent,
    pathMatch: 'full'
  },
  {
    path: 'data-entry-selection',
    component: DataEntrySelectionComponent,
    pathMatch: 'full',
    canActivate: [RoleGuardService],
    data: {
      expectedRoles: ['DISTRICT']
    }
  },
  {
    path: 'data-entry',
    component: InstitutionalComponent,
    pathMatch: 'full',
    canActivate: [RoleGuardService],
    data: {
      expectedRoles: ['DISTRICT']
    }
  },
  {
    path: 'data-entry-survival',
    component: GirlChildSurvivalComponent,
    pathMatch: 'full',
    canActivate: [RoleGuardService],
    data: {
      expectedRoles: ['DISTRICT']
    }
  },
  {
    path: 'data-entry-training',
    component: TrainingComponent,
    pathMatch: 'full',
    canActivate: [RoleGuardService],
    data: {
      expectedRoles: ['DISTRICT']
    }
  },
  {
    path: 'data-entry-awareness', 
    component: AwarenessComponent,
    pathMatch: 'full',
    canActivate: [RoleGuardService],
    data: {
      expectedRoles: ['DISTRICT']
    }
  },
  {
    path: 'drafts',
    component: DraftsComponent,
    pathMatch: 'full',
    canActivate: [RoleGuardService],
    data: {
      expectedRoles: ['DISTRICT']
    }
  },
  {
    path: 'user-management',
    component: UserManagementComponent,
    pathMatch: 'full',
    canActivate: [RoleGuardService],
    data: {
      expectedRoles: ['ADMIN']
    }
  },
  {
    path: 'upload-csr',
    component: UploadCsrComponent,
    pathMatch: 'full',
    canActivate: [RoleGuardService],
    data: {
      expectedRoles: ['ADMIN']
    }
  },
  {
    path: 'error',
    component: Exception404Component,
    pathMatch: 'full'
  },{
    path: 'session-out',
    component: SessionOutComponent,
    pathMatch: 'full'
  },
  {
    path: 'exception',
    component: Exception404Component,
    pathMatch: 'full'
  },
  {
    path: 'change-password',
    component: ChangePasswordComponent,
    pathMatch: 'full',
    canActivate: [RoleGuardService],
    data: {
      expectedRoles: ['STATE', 'NATIONAL', 'DISTRICT', 'ADMIN']
    }
  },
  {
    path: 'reset-password',
    component: ResetPasswordComponent,
    pathMatch: 'full',
    canActivate: [RoleGuardService],
    data: {
      expectedRoles: ['ADMIN']
    }
  },
  {
    path: 'pages',
    loadChildren: './page/static-page/static.module#StaticModule',
  },
  {
    path: 'dashboard',
    loadChildren: './page/dashboard/dashboard.module#DashboardModule'
    // canActivate: [RoleGuardService],
    // data: {
    //   expectedRoles: ['STATE', 'NATIONAL', 'DISTRICT', 'ADMIN']
    // }
  },
  {
    path: 'cms',
    loadChildren: './page/cms/cms.module#CmsModule',
    canActivate: [RoleGuardService],
    data: {
      expectedRoles: ['ADMIN']
    }
  },
  {
    path: 'snapshot-view',
    loadChildren: './page/snapshot/snapshot.module#SnapshotModule'
  },
  {
    path: 'raw-report',
    loadChildren: './page/report/report.module#ReportModule'
  },

  { path: '**', redirectTo: 'exception' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  schemas: [NO_ERRORS_SCHEMA]
})
export class AppRoutingModule { }