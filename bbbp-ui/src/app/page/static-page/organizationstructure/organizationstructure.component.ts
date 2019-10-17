import { Component, OnInit } from '@angular/core';
import { StaticHomeService } from '../../../service/static-home.service';
import { Constants } from '../../../constants';

@Component({
  selector: 'app-organizationstructure',
  templateUrl: './organizationstructure.component.html',
  styleUrls: ['./organizationstructure.component.scss']
})
export class OrganizationstructureComponent implements OnInit {
organizationData:any;
gateway=Constants.HOME_URL+Constants.CMS_URL;
staticService: StaticHomeService;
  constructor(private staticServiceProvider: StaticHomeService) { 
    this.staticService = staticServiceProvider;
  }

  ngOnInit() {
    this.staticService.getData("Organisational Structure").subscribe(data=>{
      this.organizationData =  data;
      this.staticService.reinitializeData(data);
  })
  }

}
