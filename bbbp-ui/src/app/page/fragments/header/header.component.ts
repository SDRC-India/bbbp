import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';
import { Cookie } from 'ng2-cookies';

import { AppService } from '../../../app.service';
import { StaticHomeService } from '../../../service/static-home.service';
import { StaticPageService } from '../../static-page/services/static-page.service';
import { timingSafeEqual } from 'crypto';

declare var $: any;



@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  router:Router;
  app: any;
  userLevel: string;
  userName: string;
  homeLangDetails: any[] = [];
  homeData: any;
  homeLang: any[] = [];
  multiLang: any[] = [];
  hindi: string = 'हिन्दी';
  english: string= 'English';
  menuName: string;
  staticService: StaticHomeService;
  staticPageService: StaticPageService
  userManagementLink: any = {data: { expectedRole: 'NATIONAL'}};
  viewPageName: string='';
  pageData: any;
  // @Input() routerLinkActiveIsDisabled: boolean;
  constructor( router:Router, private appService: AppService,
     private staticServiceProviders: StaticHomeService, private staticPageServiceProviders: StaticPageService) { 
    this.router = router;
    this.app = appService;
    this.staticService = staticServiceProviders;
    this.staticPageService = staticPageServiceProviders;
  }

  ngOnInit() {
    this.staticService.getMenuList('portalMenu').subscribe( data => {
      this.staticService.menus = <any[]> data['viewContent']['english'];
    })

    if(Cookie.check('access_token')){
      var token = JSON.parse(Cookie.get('access_token'));
      this.app.userName = token.username;
    }
    else{
      this.app.userName = "";
    }
    this.staticService.selectedLang = "english"
    this.staticService.getViewName();
    if(this.staticService.lastURLSegment == ''){
      this.viewPageName = "Home"
    }else if(this.staticService.lastURLSegment == 'achievement'){
      this.viewPageName = "Achievement and Progress"
    }
    this.staticService.getData(this.viewPageName).subscribe(data=>{
      this.homeData =  data;
      this.homeLang = this.homeData.viewContent;
      this.multiLang = Object.keys(this.homeLang);
      if(this.multiLang[0] == 'hindi'){
        this.multiLang[0] = this.hindi;
      }
      if(this.multiLang[1] == 'english'){
        this.multiLang[1] = this.english;
      }
    })
  }

  // submenu selection
  subMenuSelection(menu){
     this.menuName = menu.section;
  }
  removeMenu(){
    this.menuName = '';
  }

  setFontSize(fontSize) {
    $("#bodyMain *").css("font-size", fontSize+"px");

  }
  //handles nav-links which are going to be shown 
  checkUserAuthorization(route) {

    const expectedRoles = route;
    if(Cookie.check('access_token')){
      var token = JSON.parse(Cookie.get('access_token'));
      this.app.userName = token.username;
    }
    let flag = false;
    if (this.appService.checkLoggedIn()) {
      expectedRoles.forEach(expectedRole => {
        if(token.roles[0] == expectedRole){
          flag = true;
        }
      });
    }
    return flag;
  }
  switchToLanguage(index) {
    if(index == 0){
      this.staticService.selectedLang = 'hindi';
      this.staticPageService.selectedLang = 'hindi';
    }
    else {
      this.staticService.selectedLang = 'english';
      this.staticPageService.selectedLang = 'english';
    }
    this.staticService.getViewName();
    if(this.staticService.lastURLSegment != ''){
      this.staticService.getMenuList('portalMenu').subscribe( data => {
        this.staticService.menus = <any[]> data['viewContent']['english'];
        this.staticService.menus.forEach(element => {
            for (let index = 0; index < element.data.length; index++) {
              let url = '/'+ element.data[index].url;
              if(url == this.staticService.currentURL.replace("%20"," ")){
                this.viewPageName = element.data[index].content;
                this.staticService.getData(this.viewPageName).subscribe(data=>{
                  this.pageData =  data;
                  this.staticService.reinitializeData(this.pageData);
                  this.staticPageService.reinitializeData(this.pageData);
                })
              }else if(this.staticService.lastURLSegment == 'achievements'){
                this.staticService.getData('Achievement and Progress').subscribe(data=>{
                  this.pageData =  data;
                  this.staticService.reinitializeData(this.pageData);
                  this.staticPageService.reinitializeData(this.pageData);
                })
              }else if(this.staticService.lastURLSegment == 'whatsnew'){
                this.staticService.getData("What's New").subscribe(data=>{
                  this.pageData =  data;
                  this.staticService.reinitializeData(this.pageData);
                  this.staticPageService.reinitializeData(this.pageData);
                })
              }
            }
        });
      })
    }else{
      this.staticService.getData("Home").subscribe(data=>{
        this.pageData =  data;
        this.staticService.reinitializeData(this.pageData);
        this.staticPageService.reinitializeData(this.pageData);
      })
    }
    
     
    // }
  }

  logout(){
    this.appService.logout();
    this.app.userName = "";
  }


}
