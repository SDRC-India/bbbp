import { CommonModule } from '@angular/common';
import { NgModule, NO_ERRORS_SCHEMA } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MyDatePickerModule } from 'mydatepicker';
import { RecaptchaModule } from 'ng-recaptcha';
import { NgxImageGalleryModule } from 'ngx-image-gallery';
import { NgxPaginationModule } from 'ngx-pagination';

import { BestpraticePipe } from '../../filters/bestpratice.pipe';
import { ContributionPipe } from '../../filters/contribution.pipe';
import { OrderByPipe } from '../../filters/order-by.pipe';
import { SanctionorderPipe } from '../../filters/sanctionorder.pipe';
import { AboutusComponent } from './aboutus/aboutus.component';
import { AchievementsComponent } from './achievements/achievements.component';
import { BreadcrumbPaginationComponent } from './breadcrumb-pagination/breadcrumb-pagination.component';
import { BrochureComponent } from './brochure/brochure.component';
import { ContactusComponent } from './contactus/contactus.component';
import { DisclaimerComponent } from './disclaimer/disclaimer.component';
import { DocumentComponent } from './document/document.component';
import { ActivitiesComponent } from './events-activities/activities/activities.component';
import { FaqComponent } from './faq/faq.component';
import { AudioGalleryComponent } from './gallery/audio-gallery/audio-gallery.component';
import { PhotoGalleryComponent } from './gallery/photo-gallery/photo-gallery.component';
import { SubPhotoGalleryComponent } from './gallery/sub-photo-gallery/sub-photo-gallery.component';
import { VideoGalleryComponent } from './gallery/video-gallery/video-gallery.component';
import { HandoutComponent } from './handout/handout.component';
import { HelpComponent } from './help/help.component';
import { KeyContactsComponent } from './key-contacts/key-contacts.component';
import { OrganizationstructureComponent } from './organizationstructure/organizationstructure.component';
import { OthersComponent } from './others/others.component';
import { PanelComponent } from './panel/panel.component';
import { PostersComponent } from './posters/posters.component';
import { PrivacyComponent } from './privacy/privacy.component';
import { ReportComponent } from './report/report.component';
import { BestpraticesComponent } from './resources/bestpratices/bestpratices.component';
import { ConsultationsComponent } from './resources/consultations/consultations.component';
import { ExposurevisitsComponent } from './resources/exposurevisits/exposurevisits.component';
import { GuidelinesComponent } from './resources/guidelines/guidelines.component';
import { MeetingComponent } from './resources/meeting/meeting.component';
import { NavdishareportComponent } from './resources/navdishareport/navdishareport.component';
import { RevalidationletterComponent } from './resources/revalidationletter/revalidationletter.component';
import { SanctionorderComponent } from './resources/sanctionorder/sanctionorder.component';
import { TrainingmoduleComponent } from './resources/trainingmodule/trainingmodule.component';
import { RtiComponent } from './rti/rti.component';
import { SearchTextPipe } from './search-text.pipe';
// import { StaticPageService } from './services/static-page.service';
import { routing } from './static.routing';
import { TermsofuseComponent } from './termsofuse/termsofuse.component';
import { UnderconstructionComponent } from './underconstruction/underconstruction.component';
import { WhatsnewComponent } from './whatsnew/whatsnew.component';
import { WhatsnewcontentComponent } from './whatsnewcontent/whatsnewcontent.component';
import { CmsAreaFilterPipe } from './cms-area-filter.pipe';
import { SitemapComponent } from './sitemap/sitemap.component';



@NgModule({
  imports: [
    CommonModule,
    routing,
    FormsModule,
    ReactiveFormsModule,
    NgxImageGalleryModule,
    NgxPaginationModule,
    MyDatePickerModule,
    RecaptchaModule.forRoot()

  ],
  declarations: [
    TermsofuseComponent,
    DisclaimerComponent,
    PrivacyComponent,
    AboutusComponent,
    PhotoGalleryComponent,
    SubPhotoGalleryComponent,
    VideoGalleryComponent,
    AudioGalleryComponent,
    OrderByPipe,
    SanctionorderPipe,
    BestpraticePipe,
    GuidelinesComponent,
    BestpraticesComponent,
    SanctionorderComponent,
    ConsultationsComponent,
    NavdishareportComponent,
    SanctionorderPipe,
    ContributionPipe,
    ContactusComponent,
    FaqComponent,
    BrochureComponent,
    HandoutComponent,
    PanelComponent,
    PostersComponent,
    KeyContactsComponent,
    HelpComponent,
    OrganizationstructureComponent,
    WhatsnewComponent,
    RevalidationletterComponent,
    UnderconstructionComponent,
    AchievementsComponent,
    ActivitiesComponent,
    WhatsnewcontentComponent,
    DocumentComponent,
    SearchTextPipe,
    BreadcrumbPaginationComponent,
    ReportComponent,
    RtiComponent,
    OthersComponent,
    TrainingmoduleComponent,
    ExposurevisitsComponent,
    MeetingComponent,
    CmsAreaFilterPipe,
    SitemapComponent 
],
  // providers: [StaticPageService],
  schemas: [ NO_ERRORS_SCHEMA ]
})
export class StaticModule { }
