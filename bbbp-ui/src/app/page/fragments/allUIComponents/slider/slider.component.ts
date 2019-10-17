import { Component, OnInit } from '@angular/core';

import { StaticHomeService } from '../../../../service/static-home.service';

declare var $:any;
@Component({
  selector: 'app-slider',
  templateUrl: './slider.component.html',
  styleUrls: ['./slider.component.scss']
})
export class SliderComponent implements OnInit {

  homeData: any;
  homeDetails: any[] = [];
  homeSection: any = {};
  staticService: StaticHomeService;
  constructor(private staticServiceProvider:StaticHomeService) {
    this.staticService = staticServiceProvider;
   }

  ngOnInit() {
    // $(".carousel-main .carousel-inner .carousel-item:first").addClass("carousel-item active");
    // $(".carousel-main .carousel-inner .carousel-item:second").addClass("carousel-item");
    // $(".carousel-main .carousel-inner .carousel-item:third").addClass("carousel-item");
    $('a[data-slide="prev"]').click(function() {
      $('#bbbp-carousel').carousel('prev');
    });
    
    $('a[data-slide="next"]').click(function() {
      $('#bbbp-carousel').carousel('next');
    });
    
 }


}
