import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MyDatePickerModule } from 'mydatepicker';
import { DragulaModule, DragulaService } from 'ng2-dragula';
import { NgxPaginationModule } from 'ngx-pagination';

import { routing } from './cms.routing';
import { ContentSectionComponent } from './content-section/content-section.component';
import { LeftSideBarComponent } from './left-side-bar/left-side-bar.component';
import { RemoveTableColumPipe } from './pipes/remove-table-colum.pipe';
import { SafeHtmlPipe } from './pipes/safe-html.pipe';
import { SearchTextPipe } from './pipes/search-text.pipe';
import { ResourcesGuidelinesComponent } from './resources-guidelines/resources-guidelines.component';
import { ApiService } from './services/api.service';
import { DataService } from './services/data.service';
import { DropDownFilterPipePipe } from './pipes/drop-down-filter-pipe.pipe';


@NgModule({
  imports: [
    CommonModule,
    routing,
    FormsModule,
    ReactiveFormsModule,
    DragulaModule,
    NgxPaginationModule,
    MyDatePickerModule,
  ],
  declarations: [
    LeftSideBarComponent,
    ResourcesGuidelinesComponent,
    ContentSectionComponent,
    RemoveTableColumPipe,
    SafeHtmlPipe,
    SearchTextPipe,
    DropDownFilterPipePipe,
  ],
  providers: [ApiService, DataService, DragulaService]
})
export class CmsModule { }
