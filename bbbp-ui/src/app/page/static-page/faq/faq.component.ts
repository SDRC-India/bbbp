import { Component, OnInit } from '@angular/core';
import { StaticPageService } from '../services/static-page.service';
import { Router } from '../../../../../node_modules/@angular/router';
import { ApiService } from '../../cms/services/api.service';
declare var $: any;

@Component({
  selector: 'app-faq',
  templateUrl: './faq.component.html',
  styleUrls: ['./faq.component.scss']
})
export class FaqComponent implements OnInit {
  faqlinesData :any;
  FaqlineDetails: any[] = [];
 faqlineSection: any[] = [];
 router: Router;
 visible: boolean = false;

  constructor(private Faqservice:StaticPageService,private route: Router, private apiService: ApiService) { 
    this.router = route;
  }

  ngOnInit() {
    this.Faqservice.getData("FAQs").subscribe(data=>{
      this.faqlinesData =  data;
      this.FaqlineDetails = this.faqlinesData.viewContent.english;
      for(var i=0;i<this.FaqlineDetails.length;i++)
			{
        this.faqlineSection = this.FaqlineDetails[i].data;
			}
    })
    $(".side-bar-container").css("min-height", $(window).height()-150);
    $("body:not(.rename-btn,.menu-input)").click(function(){
      $(".menu-input").attr("disabled", "true")
    })
  }
  setSubmenu(event){
    if(!$(event.target).closest('.menu').hasClass("active")){
      $(".menu.active").find(".submenu-list").slideUp();
      $(".menu.active").removeClass("active")
      $(event.target).closest('.menu').find('.submenu-list').slideDown();
      $(event.target).closest('.menu').addClass("active")
    }
    else{
    $(event.target).closest('.menu').removeClass("active")
    $(event.target).closest('.menu').find('.submenu-list').slideUp();
    }
  }
}
