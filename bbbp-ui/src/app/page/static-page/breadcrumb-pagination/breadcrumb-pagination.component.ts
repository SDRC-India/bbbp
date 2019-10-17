import { Component, OnInit } from '@angular/core';

import { ActivationEnd, Router } from '../../../../../node_modules/@angular/router';

declare var $: any;

export interface BreadCrumb{
  label: string;
  url: string;
}

@Component({
  selector: 'app-breadcrumb-pagination',
  templateUrl: './breadcrumb-pagination.component.html',
  styleUrls: ['./breadcrumb-pagination.component.scss']
})

export class BreadcrumbPaginationComponent implements OnInit {
  
  public href: string = '';
  public newBreadcrumbs: BreadCrumb[];

  constructor(private router: Router) {
    var bc= this.newBreadcrumbs = [];
    router.events.subscribe((evt) => {
    if(evt instanceof ActivationEnd){
      let breadcrumb: BreadCrumb = {
        label: evt.snapshot.data["breadcrumb"],
        url: evt.snapshot.url.map(segment => segment.path).join("/")
      };
      bc.push(breadcrumb);
    }
    bc.reverse();
  });
   }

  ngOnInit() {
  }
}
