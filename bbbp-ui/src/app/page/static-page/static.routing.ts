import { Routes, RouterModule } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { DisclaimerComponent } from './disclaimer/disclaimer.component';
import { TermsofuseComponent } from './termsofuse/termsofuse.component';
import { PrivacyComponent } from './privacy/privacy.component';
import { AboutusComponent } from './aboutus/aboutus.component';
import { PhotoGalleryComponent } from './gallery/photo-gallery/photo-gallery.component';
import { SubPhotoGalleryComponent } from './gallery/sub-photo-gallery/sub-photo-gallery.component';
import { VideoGalleryComponent } from './gallery/video-gallery/video-gallery.component';
import { AudioGalleryComponent } from './gallery/audio-gallery/audio-gallery.component';
import { GuidelinesComponent } from './resources/guidelines/guidelines.component';
import { SanctionorderComponent } from './resources/sanctionorder/sanctionorder.component';
import { RevalidationletterComponent } from './resources/revalidationletter/revalidationletter.component';
import { ConsultationsComponent } from './resources/consultations/consultations.component';
import { BestpraticesComponent } from './resources/bestpratices/bestpratices.component';
import { NavdishareportComponent } from './resources/navdishareport/navdishareport.component';
import { HelpComponent } from './help/help.component';
import { KeyContactsComponent } from './key-contacts/key-contacts.component';
import { ContactusComponent } from './contactus/contactus.component';
import { FaqComponent } from './faq/faq.component';
import { BrochureComponent } from './brochure/brochure.component';
import { HandoutComponent } from './handout/handout.component';
import { PanelComponent } from './panel/panel.component';
import { PostersComponent } from './posters/posters.component';
import { UnderconstructionComponent } from './underconstruction/underconstruction.component';
import { AchievementsComponent } from './achievements/achievements.component';
import { OrganizationstructureComponent } from './organizationstructure/organizationstructure.component';
import { WhatsnewComponent } from './whatsnew/whatsnew.component';
import { ActivitiesComponent } from './events-activities/activities/activities.component';
import { WhatsnewcontentComponent } from './whatsnewcontent/whatsnewcontent.component';
import { DocumentComponent } from './document/document.component';
import { BreadcrumbPaginationComponent } from './breadcrumb-pagination/breadcrumb-pagination.component';
import { ReportComponent } from './report/report.component';
import { RtiComponent } from './rti/rti.component';
import { OthersComponent } from './others/others.component';
import { TrainingmoduleComponent } from './resources/trainingmodule/trainingmodule.component';
import { ExposurevisitsComponent } from './resources/exposurevisits/exposurevisits.component';
import { MeetingComponent } from './resources/meeting/meeting.component';
import { RoleGuardService } from '../../guard/role-guard.service';

export const routes: Routes = [
    {
        path: 'disclaimer',
        component: DisclaimerComponent,
        pathMatch: 'full',
        data: {
          breadcrumb: 'Disclaimer'
        }
      },
      {
        path: 'report',
        component:ReportComponent,
        pathMatch: 'full',
        data: {
          breadcrumb: 'Report'
        }
      },
      {
        path: '',
        data: {
          breadcrumb: 'RTI'
        },
        children: [
          {
            path: 'Applications and Appeals',
            component: RtiComponent,
            pathMatch: 'full',
            data: {
              breadcrumb: 'Applications and Appeals'
            }
          }]
      },
      {
        path: 'terms-of-use',
        component: TermsofuseComponent,
        pathMatch: 'full',
        data: {
          breadcrumb: 'Terms of Use'
        }
      },
      {
        path: 'privacy-policy',
        component: PrivacyComponent,
        pathMatch: 'full',
        data: {
          breadcrumb: 'Privacy Policy'
        }
      },
      {
        path: '',
        data: {
          breadcrumb: 'About Us'
        },
        children: [
          {
            path: 'aboutus',
            component: AboutusComponent,
            pathMatch: 'full',
            data: {
              breadcrumb: 'About Scheme'
            }
          },
          {
            path: 'organizationstructure',
            component: OrganizationstructureComponent,
            pathMatch: 'full',
            data: {
              breadcrumb: 'Organisational Structure'
            }
          },
          {
            path: 'guidelines',
            component: GuidelinesComponent,
            pathMatch: 'full',
            data: {
              breadcrumb: 'BBBP Guidelines'
            }
          },
          {
            path: 'key-contacts',
            component: KeyContactsComponent,
            pathMatch: 'full',
            data: {
              breadcrumb: 'Key Contacts'
            },
          },
        ]
      },
      {
        path: '',
        data: {
          breadcrumb: 'eKnowledge'
        },
        children: [
          {
              path: 'Innovative initiatives',
              component: BestpraticesComponent,
              pathMatch: 'full',
              data: {
                breadcrumb: 'Innovative initiatives'
              }
            },
            {
              path: 'documents',
              component: DocumentComponent,
              pathMatch: 'full',
              data: {
                breadcrumb: "Documents"
              }
            },
            {
              path: 'trainingmodule',
              component: TrainingmoduleComponent,
              pathMatch: 'full',
              data: {
                breadcrumb: 'Training Modules'
              }
            },
            {
              path: 'reports',
              component: NavdishareportComponent,
              pathMatch: 'full',
              data: {
                breadcrumb: 'Reports and Publications'
              }
            },
            {
              path: 'userGuide',
              component: HelpComponent,
              pathMatch: 'full',
              data: {
                breadcrumb: 'User Guide',
                expectedRoles: ['STATE', 'NATIONAL', 'DISTRICT', 'ADMIN']
              },
            },
        ]
      },
      {
        path: '',
        data: {
          breadcrumb: 'Orders & Letters'
        },
        children: [
            {
              path: 'sanctionorders',
              component: SanctionorderComponent,
              pathMatch: 'full',
              data: {
                breadcrumb: 'Sanction Orders',
                expectedRoles: ['STATE', 'NATIONAL', 'DISTRICT', 'ADMIN']
              },
              canActivate: [RoleGuardService]
            },
            {
              path: 'letters',
              component: RevalidationletterComponent,
              pathMatch: 'full',
              data: {
                breadcrumb: 'Revalidation Letters',
                expectedRoles: ['STATE', 'NATIONAL', 'DISTRICT', 'ADMIN']
              },
              canActivate: [RoleGuardService]
            },
            {
              path: 'Others',
              component: OthersComponent,
              pathMatch: 'full',
              data: {
                breadcrumb: 'Others',
                expectedRoles: ['STATE', 'NATIONAL', 'DISTRICT', 'ADMIN']
              },
              canActivate: [RoleGuardService]
            }
        ]
      },
      {
        path: '',
        data: {
          breadcrumb: 'Creative Corner'
        },
        children: [
          {
            path: 'brochure',
            component: BrochureComponent,
            pathMatch: 'full',
            data: {
              breadcrumb: 'Brochures'
            }
          },
          {
            path: 'handout',
            component: HandoutComponent,
            pathMatch: 'full',
            data: {
              breadcrumb: 'Handouts'
            }
          },
          {
            path: 'panel',
            component: PanelComponent,
            pathMatch: 'full',
            data: {
              breadcrumb: 'Panels'
            }
          },
          {
            path: 'posters',
            component: PostersComponent,
            pathMatch: 'full',
            data: {
              breadcrumb: 'Posters'
            }
          },
          {
            path: 'photo-gallery',
            component: PhotoGalleryComponent,
            pathMatch: 'full',
            data: {
              breadcrumb: 'Photo Gallery'
            }
          },
          {
            path: 'sub-photo-gallery',
            component: SubPhotoGalleryComponent,
            pathMatch: 'full',
            data: {
              breadcrumb: 'Photo Gallery'
            }
          },
          {
            path: 'video-gallery',
            component: VideoGalleryComponent,
            pathMatch: 'full',
            data: {
              breadcrumb: 'Videos'
            }
          },
          {
            path: 'audio-gallery',
            component: AudioGalleryComponent,
            pathMatch: 'full',
            data: {
              breadcrumb: 'Audio'
            }
          }
        ]
      },
      {
        path: '',
        data: {
          breadcrumb: 'Events & Activities'
        },
        children: [
          {
            path: 'activities',
            component: ActivitiesComponent,
            pathMatch: 'full',
            data: {
              breadcrumb: "Activities"
            }
          },
          {
            path: 'consultations',
            component: ConsultationsComponent,
            pathMatch: 'full',
            data: {
              breadcrumb: 'Consultations'
            }
          },
          {
            path: 'Exposure Visits',
            component: ExposurevisitsComponent,
            pathMatch: 'full',
            data: {
              breadcrumb: "Exposure Visits"
            }
          },
          {
            path: 'Meeting',
            component: MeetingComponent,
            pathMatch: 'full',
            data: {
              breadcrumb: "Meeting"
            }
          },
        ]
      },
      {
        path: 'contactus',
        component: ContactusComponent,
        pathMatch: 'full',
        data: {
          breadcrumb: 'Contact Us'
        }
      },
      {
        path: 'faq',
        component: FaqComponent,
        pathMatch: 'full',
        data: {
          breadcrumb: 'FAQs'
        }
      },
      {
        path: 'underconstruction',
        component: UnderconstructionComponent,
        pathMatch: 'full'
      },
      {
        path: 'achievements',
        component: AchievementsComponent,
        pathMatch: 'full',
        data: {
          breadcrumb: 'Achievements & Progress'
        }
      },
     
      {
        path: 'whatsnew',
        component: WhatsnewComponent,
        pathMatch: 'full',
        data: {
          breadcrumb: "What's New"
        }
      },
      
      {
        path: 'whatsnewcontent',
        component: WhatsnewcontentComponent,
        pathMatch: 'full',
        data: {
          breadcrumb: "What's New"
        }
      },
      {
        path: 'breadcrumbs',
        component: BreadcrumbPaginationComponent,
        pathMatch: 'full'
      }
  ];

export const routing: ModuleWithProviders = RouterModule.forChild(routes);
