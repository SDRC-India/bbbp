import 'rxjs/add/operator/finally';

import { Component } from '@angular/core';
import { Router } from '@angular/router';

declare var $: any;


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {


  router: Router;
  constructor(router: Router) { 
    this.router =router;
  }

  ngAfterViewInit() {
    $(".main-content").css("min-height", $(window).height() - 150);
  }

  ngOnInit() {
    /** start of header fix on scroll down **/
    $(window).scroll(function () {
      // console.log($(window).scrollTop())
      if ($(window).scrollTop() > 149 && $(window).width() < 2000) {
        $('#header').addClass('navbar-fixed');
        $(".left-list").addClass('left-side-scroll');
        $(".report-data-info").addClass('report-info-fixed');
      }
      if ($(window).scrollTop() < 149 && $(window).width() < 2000) {
        $('#header').removeClass('navbar-fixed');
        $(".left-list").removeClass('left-side-scroll');
        $(".report-data-info").removeClass('report-info-fixed');
      }
    });
    /** end of header fix on scroll down **/
  }

  ngAfterViewChecked() {
    if ($(window).width() <= 992) {
      $(".collapse").removeClass("show");
      $(".navbar-nav .nav-item").not('.dropdown').click(function () {
        $(".collapse").removeClass("show");
      })
    }
  }

}
