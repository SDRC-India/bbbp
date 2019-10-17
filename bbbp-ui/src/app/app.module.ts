import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { NgModule, NO_ERRORS_SCHEMA } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { LoadingBarHttpClientModule } from '@ngx-loading-bar/http-client';
import { LoadingBarRouterModule } from '@ngx-loading-bar/router';
import { MDBBootstrapModule } from 'angular-bootstrap-md';

import { AppRoutingModule } from './/app-routing.module';
import { AppComponent } from './app.component';
import { AppService } from './app.service';
import { AreaFilterPipe } from './filters/area-filter.pipe';
import { ObjIteratePipe } from './filters/obj-iterate.pipe';
import { RemoveElementPipe } from './filters/remove-from-array/remove-element.pipe';
import { AuthGuard } from './guard/auth.guard';
import { LoggedinGuardService } from './guard/loggedin-guard.service';
import { RoleGuardService } from './guard/role-guard.service';
import { User } from './interface/form-model';
import { CmsModule } from './page/cms/cms.module';
import { DashboardModule } from './page/dashboard/dashboard.module';
import { AwarenessComponent } from './page/data-entry-module/awareness/awareness.component';
import { DataEntryHeadComponent } from './page/data-entry-module/data-entry-head/data-entry-head.component';
import { DataEntrySelectionComponent } from './page/data-entry-module/data-entry-selection/data-entry-selection.component';
import { GirlChildSurvivalComponent } from './page/data-entry-module/girl-child-survival/girl-child-survival.component';
import { InstitutionalComponent } from './page/data-entry-module/institutional/institutional.component';
import { TrainingComponent } from './page/data-entry-module/training/training.component';
import { DraftsComponent } from './page/drafts/drafts.component';
import { Exception404Component } from './page/exception404/exception404.component';
import { DynamicFormComponent } from './page/fragments/allUIComponents/dynamic-form/dynamic-form.component';
import { InlineFormComponent } from './page/fragments/allUIComponents/inline-form/inline-form.component';
import { SliderComponent } from './page/fragments/allUIComponents/slider/slider.component';
import { FooterComponent } from './page/fragments/footer/footer.component';
import { HeaderComponent } from './page/fragments/header/header.component';
import { UploadingModalComponent } from './page/fragments/uploading-modal/uploading-modal.component';
import { HomeComponent } from './page/home/home.component';
import { LoginComponent } from './page/login/login.component';
import { StaticModule } from './page/static-page/static.module';
import { ChangePasswordComponent } from './page/user-module/change-password/change-password.component';
import { ResetPasswordComponent } from './page/user-module/reset-password/reset-password.component';
import { UserManagementComponent } from './page/user-module/user-management/user-management.component';
import { UserSideMenuComponent } from './page/user-module/user-side-menu/user-side-menu.component';
import { FormControlService } from './service/form-control.service';
import { FormDataSaveService } from './service/form-data-save.service';
import { FormFieldsService } from './service/form-fields.service';
import { PagerService } from './service/pager.service';
import { PouchdbService } from './service/pouchdb.service';
import { SessionCheckService } from './service/session-check.service';
import { StaticHomeService } from './service/static-home.service';
import { UserService } from './service/user/user.service';
import { XhrInterceptorService } from './service/xhr-interceptor.service';
import { StaticPageService } from './page/static-page/services/static-page.service';
import { SnapshotModule } from './page/snapshot/snapshot.module';
import { ReportModule } from './page/report/report.module';
import { SdrcLoaderModule } from '../../lib/loader/public_api';
import { SessionOutComponent } from './page/session-out/session-out.component';
import { UploadCsrComponent } from './page/user-module/upload-csr/upload-csr.component';




@NgModule({
  declarations: [
    HeaderComponent,
    FooterComponent,
    AppComponent,
    LoginComponent,
    HomeComponent,
    InlineFormComponent,
    SliderComponent,
    RemoveElementPipe,
    DynamicFormComponent,
    DataEntryHeadComponent,
    InstitutionalComponent,
    UserManagementComponent,
    GirlChildSurvivalComponent,
    Exception404Component,
    ChangePasswordComponent,
    TrainingComponent,
    AwarenessComponent,
    ObjIteratePipe,
    DataEntrySelectionComponent,
    DraftsComponent,
    AreaFilterPipe,
    ResetPasswordComponent,
    UploadingModalComponent,
    UserSideMenuComponent,
    SessionOutComponent,
    UploadCsrComponent
  ],
  imports: [
    BrowserModule,
    LoadingBarHttpClientModule,
    LoadingBarRouterModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    MDBBootstrapModule.forRoot(),
    DashboardModule,
    CmsModule,
    StaticModule,
    RouterModule,
    SnapshotModule,
    ReportModule,
    SdrcLoaderModule
  ],
  schemas: [ NO_ERRORS_SCHEMA ],
  providers: [{ provide: HTTP_INTERCEPTORS, useClass: XhrInterceptorService, multi: true }, AppService,
  UserService, AuthGuard, User, HttpClientModule, FormControlService, FormFieldsService,
  PouchdbService, RoleGuardService, LoggedinGuardService, FormDataSaveService,
  AreaFilterPipe, SessionCheckService, XhrInterceptorService, PagerService, StaticHomeService, StaticPageService],
  bootstrap: [AppComponent]
})
export class AppModule { }
