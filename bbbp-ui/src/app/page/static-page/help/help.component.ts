import { Component, OnInit } from '@angular/core';

import { Constants } from '../../../constants';
import { StaticPageService } from '../services/static-page.service';


@Component({
  selector: 'app-help',
  templateUrl: './help.component.html',
  styleUrls: ['./help.component.scss']
})
export class HelpComponent implements OnInit {

  staticService: StaticPageService;
  constructor(private staticServiceProvider: StaticPageService) {
    this.staticService = staticServiceProvider;
   }

  ngOnInit() {
      this.staticService.getData("User Guide").subscribe(data=>{
      this.staticService.reinitializeData(data);
  })
  }

  downloadFiles(fileName){  
    window.location.href = Constants.HOME_URL + 'cms/downloadCmsDoc?fileName='+fileName;
  }

}
