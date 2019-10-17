import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DragulaService } from 'ng2-dragula';
import { Subscription } from 'rxjs';

import { StaticHomeService } from '../../../service/static-home.service';
import { ApiService } from '../services/api.service';
import { DataService } from '../services/data.service';

declare var $: any;

@Component({
  selector: 'app-left-side-bar',
  templateUrl: './left-side-bar.component.html',
  styleUrls: ['./left-side-bar.component.scss']
})
export class LeftSideBarComponent implements OnInit {

  router: Router;
  visible: boolean = false;
  dataService: DataService;
  menuOrderList = 'menuList';
  subMenuOrderList = 'subMenuOrderList';
  subs = new Subscription();
  menuListDetails: any = [];
  menuLists: any = [];
  maps: Map<string, any[]>;
  staticHomeService: StaticHomeService;
  constructor(private route: Router, private apiService: ApiService, private dataServiceProvider: DataService, private dragulaService: DragulaService,
    private staticHomeServiceProvider: StaticHomeService) {
    this.router = route;
    this.dataService = dataServiceProvider;
    this.staticHomeService = staticHomeServiceProvider;
    this.subs.add(dragulaService.dropModel(this.menuOrderList)
      .subscribe(({ el, target, source, sourceModel, targetModel, item }) => {
        this.dataService.changedMenuList = targetModel;
        console.log(targetModel);
      })
    );
    this.subs.add(dragulaService.dropModel(this.subMenuOrderList)
      .subscribe(({ el, target, source, sourceModel, targetModel, item }) => {
        this.dataService.changedMenuList = targetModel;
        console.log(targetModel);
      })
    );
  }

  ngOnInit() {
    $(".side-bar-container").css("min-height", $(window).height() - 150);
    if (!this.dataService.menus) {
      this.getMenuList();
    }
    $("body:not(.rename-btn,.menu-input)").click(function () {
      $(".menu-input").attr("disabled", "true")
    })
    if (!this.dataService.questions) {
      this.getInnerPageQuestions();
    }
    this.dataService.showNoCmsData = true;
    this.dataService.showList = false;
  }

  getMenuList() {
    this.apiService.getMenuList().subscribe(res => {
      this.dataService.menus = <any[]>res['viewContent']['english'];

    })
  }
  setSubmenu(event) {
    if (!$(event.target).closest('.menu').hasClass("active")) {
      $(".menu.active").find(".submenu-list").slideUp();
      $(".menu.active").removeClass("active")
      $(event.target).closest('.menu').find('.submenu-list').slideDown();
      $(event.target).closest('.menu').addClass("active")
    }
    else {
      $(event.target).closest('.menu').removeClass("active")
      $(event.target).closest('.menu').find('.submenu-list').slideUp();
    }
  }

  renameMenu(event) {
    event.stopPropagation();
    $(event.target).closest('.menu').find("a input").removeAttr('disabled');
  }

  getPageContentData(event, menu) {
    this.dataService.showList = true;
    this.dataService.showNoCmsData = false;
    this.dataService.a = 1;
    this.dataService.q = 1;
    this.dataService.m = 1;
    this.dataService.k = 1;
    this.dataService.s = 1;
    this.dataService.sc = 1;
    this.dataService.d = 1;
    this.dataService.dc = 1;
    this.dataService.nl = 1;
    this.dataService.searchTexts = "";
    this.dataService.searchTextsOfDependentTable = "";
    this.dataService.searchTextsOfNational = "";
    this.dataService.searchTextsOfStateLists = "";
    this.dataService.searchTextsOfStateListsContacts = "";
    this.dataService.searchTextsOfDistrict = "";
    this.dataService.searchTextsOfDistrictContacts = "";
    this.dataService.indexOfEachRowFirst = undefined;
    this.dataService.indexOfEachRowSecond = undefined;
    this.dataService.selectedTabMenu = menu;
    this.dataService.blankErrorMessage = "";
    this.dataService.textBlankErrorMessage = "";
    this.dataService.selectedMenuLists = [];
    this.dataService.dataOfHomePage = [" "];
    this.dataService.selectedTabMenuUrl = menu.url.split("/")[1];
    this.dataService.file = [];
    this.dataService.selectedRow = "";
    this.dataService.dataIndex = 0;
    this.dataService.languageIndex = 0;
    this.dataService.indexOfSection = undefined;
    this.dataService.selectedFileName = undefined;
    this.dataService.selectedImageName = undefined;
    this.dataService.selectedAudioName = undefined;
    this.dataService.acceptPdfType = undefined;
    this.dataService.acceptImageType = undefined;
    this.dataService.acceptAudioType = undefined;
    this.dataService.hashKeyVal = "";
    this.dataService.newNewsValidity = menu.size;
    this.dataService.showEditOption = false;
    this.dataService.showEditCallOption = false;
    // keycontacts model reset
    this.dataService.kcName = "";
    this.dataService.kcAddress = "";
    this.dataService.kcDepartment = "";
    this.dataService.kcDesignation = "";
    this.dataService.kcPhoneNo = "";
    this.dataService.kcEmail = "";
    this.dataService.selectLevelDropdownVal = "";
    this.dataService.stateLevelLists = [];
    this.dataService.keyContactsdetailsOfNationLevel = [];
    this.dataService.keyContactsdetailsOfStateLevel = [];
    this.dataService.keyContactsdetailsOfDistrictLevel = [];
    this.dataService.districtLevelLists = [];
    this.dataService.keyContactsInfoDetailsOfDistrict = [];
    this.dataService.keyContactsInfoDetailsOfState = [];
    this.dataService.selectedFilesToUpload = {};
    this.dataService.submitModel = {};
    this.dataService.editClicked = false;
    this.dataService.isImageSelected = false;
    if (this.dataService.selectedTabMenuUrl == "key-contacts") {
      this.dataService.showAddOptionOfKc = false;
      this.dataService.showEditOptionOfKc = false;
      this.dataService.showEditCallOptionOfKc = false;
    }
    if (this.dataService.selectedTabMenuUrl == "sanctionorders"
      || this.dataService.selectedTabMenuUrl == "letters"
      || this.dataService.selectedTabMenuUrl == "photo-gallery"
      || this.dataService.selectedTabMenuUrl == "documents"
      || this.dataService.selectedTabMenuUrl == "Exposure Visits" ) {
      this.dataService.showAddOption = true;

    } else {
      this.dataService.showAddOption = false;
    }
    if (this.dataService.selectedTabMenu.title != 'About Scheme') {
      this.dataService.uploadedFile = "";
      this.dataService.imageUploadedFile = "";
    }
    this.dataService.allMenuContents.forEach(element => {
      if (element.type == this.dataService.selectedTabMenuUrl) {
        this.dataService.tableData = [];
        this.dataService.selectedMenuLists = element.keyVal[0];
        this.dataService.uploadedFile = "";
        this.dataService.fileName = "";
        for (let i = 0; i < element.keyVal[0].listOfCmsDataDto.length; i++) {
          this.dataService.viewName = element.keyVal[0].listOfCmsDataDto[i].viewName;
          this.dataService.cmsType = element.keyVal[0].listOfCmsDataDto[i].cmsType;
          if (this.dataService.selectedTabMenuUrl == 'key-contacts' && this.dataService.cmsType != 'nested tabular') {
            this.dataService.keyContactsdetailsSecondTable = element.keyVal[0].listOfCmsDataDto;
            for (let k = 0; i < element.keyVal[0].listOfQuestionModel.length; k++) {
              if (element.keyVal[0].listOfQuestionModel[k].label == 'Select Level' || element.keyVal[0].listOfQuestionModel[k].label == 'Select State'
                || element.keyVal[0].listOfQuestionModel[k].label == 'State Name' || element.keyVal[0].listOfQuestionModel[k].label == 'District Name'
                || element.keyVal[0].listOfQuestionModel[k].label == 'Name' || element.keyVal[0].listOfQuestionModel[k].label == 'Designation'
                || element.keyVal[0].listOfQuestionModel[k].label == 'Address' || element.keyVal[0].listOfQuestionModel[k].label == 'E-mail'
                || element.keyVal[0].listOfQuestionModel[k].label == 'Phone No. ' || element.keyVal[0].listOfQuestionModel[k].label == 'Address'
                || element.keyVal[0].listOfQuestionModel[k].label == 'Department') {
                element.keyVal[0].listOfQuestionModel[k].value = "";
              }
              if (element.keyVal[0].listOfQuestionModel[k].label == "Tabular View" && element.keyVal[0].listOfQuestionModel[k].dependentCondition[0] == "") {
                this.dataService.nationalTableId = element.keyVal[0].listOfQuestionModel[k].columnName;
                $("#" + this.dataService.nationalTableId).hide();
              }
              if (element.keyVal[0].listOfQuestionModel[k].label == "Tabular View" && element.keyVal[0].listOfQuestionModel[k].dependentCondition[0] == "isState") {
                this.dataService.stateTableId = element.keyVal[0].listOfQuestionModel[k].columnName;
                $("#" + this.dataService.stateTableId).hide();
              }
              if (element.keyVal[0].listOfQuestionModel[k].label == "Tabular View" && element.keyVal[0].listOfQuestionModel[k].dependentCondition[0] == "isDistrict") {
                this.dataService.districtTableId = element.keyVal[0].listOfQuestionModel[k].columnName;
                $("#" + this.dataService.districtTableId).hide();
              }

            }
          } else if (this.dataService.cmsType != 'nested tabular' && Object.keys(element.keyVal[0].listOfCmsDataDto[0].viewContent[this.staticHomeService.selectedLang][0].data[0]).length === 0) {
            this.dataService.noDataFound = true;
          } else {
            this.dataService.noDataFound = false;
          }
        }

        // if not key contacts page cms
        if (this.dataService.selectedTabMenuUrl != 'key-contacts') {
          this.dataService.fetchedDropdown = element.keyVal[0].listOfCmsDataDto[0].viewContent[this.staticHomeService.selectedLang];
          for (let i = 0; i < this.dataService.fetchedDropdown.length; i++) {
            this.dataService.fetchedDropdownDetails = this.dataService.fetchedDropdown[i].data;
            this.dataService.selectedSectionName = this.dataService.fetchedDropdown[i].section;
            if (this.dataService.selectedTabMenuUrl == 'aboutus') {
              this.dataService.aboutSchemeFileTitle = this.dataService.fetchedDropdownDetails[0].title;
            }
          }
          // if(this.dataService.shortDescription ==undefined)
          this.dataService.shortDescription = element.keyVal[0].listOfCmsDataDto[0].viewType;

          for (let i = 0; i < element.keyVal[0].listOfQuestionModel.length; i++) {
            element.keyVal[0].listOfQuestionModel[i].value = "";

            if (element.keyVal[0].listOfQuestionModel[i].label == "Select level")
              this.dataService.levelOptions = element.keyVal[0].listOfQuestionModel[i].options;

            if (element.keyVal[0].listOfQuestionModel[i].label == "Page Description") {
              element.keyVal[0].listOfQuestionModel[i].value = this.dataService.shortDescription;
            }
            if (this.dataService.cmsType == 'aboutus') {
              this.dataService.headingTitle = menu.title;
              if (element.keyVal[0].listOfQuestionModel[i].label == "File Name") {
                element.keyVal[0].listOfQuestionModel[i].value = this.dataService.aboutSchemeFileTitle;
              }
              if (element.keyVal[0].listOfQuestionModel[i].label == "Upload Image") {
                this.dataService.imageUploadedFilepath = this.dataService.fetchedDropdownDetails[0].url;
                this.dataService.imageUploadedFile = this.dataService.fetchedDropdownDetails[0].imageName != undefined ? this.dataService.fetchedDropdownDetails[0].imageName.substring(this.dataService.fetchedDropdownDetails[0].imageName.lastIndexOf('/') + 1) : this.dataService.fetchedDropdownDetails[0].imageName;
                if (this.dataService.imageUploadedFile == undefined) {
                  this.dataService.imageUploadedFile = this.dataService.fetchedDropdownDetails[0].imageName;
                }
                // this.dataService.imageUploadedFilepath = this.dataService.fetchedDropdownDetails[0].imageName;
                this.dataService.imageFileName = this.dataService.fetchedDropdownDetails[0].imageName;
              }
              if (this.dataService.selectedMenuLists.listOfQuestionModel[i].label == "Upload File") {
                // this.dataService.uploadedFile = this.dataService.fetchedDropdownDetails[0].fileName;
                this.dataService.uploadedFile = this.dataService.fetchedDropdownDetails[0].fileName != undefined ? this.dataService.fetchedDropdownDetails[0].fileName.substring(this.dataService.fetchedDropdownDetails[0].fileName.lastIndexOf('/') + 1) : this.dataService.fetchedDropdownDetails[0].fileName;
                this.dataService.fileSize = this.dataService.fetchedDropdownDetails[0].size;
                if (this.dataService.uploadedFile == undefined) {
                  this.dataService.uploadedFile = this.dataService.fetchedDropdownDetails[0].fileName;
                }
                this.dataService.uploadedFilepath = this.dataService.fetchedDropdownDetails[0].fileName;
                this.dataService.fileName = this.dataService.fetchedDropdownDetails[0].fileName;
              }
            }
          }
          this.dataService.removeColumnList = [];
          if (this.dataService.selectedTabMenuUrl == 'letters' || this.dataService.selectedTabMenuUrl == 'sanctionorders') {
            this.dataService.withChildren = false;
            this.dataService.tableHeaderData = 'Time Period';
            this.dataService.removeColumnList = ['year', 'quarter', 'fileName', 'createdDate', 'isLive', 'size', 'order', 'flag', 'isNew', 'caption', 'imageName', 'content', 'hashKey', 'fileTitle', 'link','url','file','areaId','isHome','areaLevel','isPriority','parentAreaId','parentAreaName'];
          } else if (this.dataService.selectedTabMenuUrl == 'documents') {
            this.dataService.withChildren = false;
            this.dataService.tableHeaderData = 'Title';
            this.dataService.removeColumnList = ['year', 'quarter', 'areaId', 'areaLevel', 'areaName', 'parentAreaId', 'parentAreaName', 'fileName', 'createdDate', 'isLive', 'size', 'order', 'flag', 'isNew', 'caption', 'imageName', 'content', 'hashKey', 'fileTitle', 'link'];
          } else if (this.dataService.selectedTabMenuUrl == 'photo-gallery') {
            this.dataService.withChildren = false;
            this.dataService.tableHeaderData = 'Album Name';
            this.dataService.removeColumnList = ['areaId', 'areaLevel', 'areaName', 'parentAreaId', 'parentAreaName', 'fileName', 'createdDate', 'isLive', 'size', 'imageName', 'title', 'flag', 'order', 'hashKey', 'isNew', 'publishedDate', 'content', 'fileTitle', 'link'];
            this.dataService.headerRemoveList = ['fileName', 'createdDate', 'isLive', 'size', 'imageName', 'title', 'flag', 'order', 'url', 'hashKey', 'isNew'];
          } else if (this.dataService.selectedTabMenuUrl == 'Innovative initiatives' || this.dataService.selectedTabMenuUrl == 'activities') {
            this.dataService.noChildren = false;
            this.dataService.removeColumnList = ['year', 'quarter', 'areaId', 'areaLevel', 'areaName', 'parentAreaId', 'parentAreaName', 'fileName', 'createdDate', 'isLive', 'size', 'imageName', 'order', 'flag', 'isNew', 'caption', 'hashKey', 'fileTitle', 'link', 'url', 'file', 'isHome', 'isPriority'];
          } else if (this.dataService.selectedTabMenuUrl == 'Exposure Visits') {
            this.dataService.tableHeaderData = 'Area of Exposure';
            this.dataService.withChildren = false;
            this.dataService.removeColumnList = ['year', 'quarter', 'areaId', 'areaLevel', 'areaName', 'parentAreaId', 'parentAreaName', 'fileName', 'createdDate', 'isLive', 'size', 'order', 'flag', 'isNew', 'caption', 'imageName', 'content', 'hashKey', 'fileTitle', 'link', 'url', 'file', 'isHome', 'isPriority'];
          } else if (this.dataService.selectedTabMenuUrl == 'Meeting') {
            this.dataService.tableHeaderData = 'Area Level';
            this.dataService.withChildren = false;
            this.dataService.removeColumnList = ['year', 'quarter', 'areaId', 'areaLevel', 'areaName', 'parentAreaId', 'parentAreaName', 'fileName', 'createdDate', 'isLive', 'size', 'order', 'flag', 'isNew', 'caption', 'imageName', 'content', 'hashKey', 'fileTitle', 'link', 'url', 'file', 'isHome', 'isPriority'];
          } else if (this.dataService.selectedTabMenuUrl == 'trainingmodule') {
            this.dataService.noChildren = false;
            this.dataService.removeColumnList = ['year', 'quarter', 'areaId', 'areaLevel', 'areaName', 'parentAreaId', 'parentAreaName', 'fileName', 'createdDate', 'isLive', 'size', 'order', 'flag', 'isNew', 'caption', 'imageName', 'content', 'hashKey', 'fileTitle', 'link', 'url', 'file', 'isHome', 'isPriority'];
          }
          else if (this.dataService.selectedTabMenuUrl == "Applications and Appeals") {
            this.dataService.noChildren = false;
            this.dataService.rtiHeaderList = ['Text Of RTI Application', 'Reply Of RTI Application', 'Text Of First Appeal Application (If Any)', 'Reply Of Appeal Application'];
            this.dataService.removeColumnList = ['year', 'quarter', 'areaId', 'areaLevel', 'areaName', 'parentAreaId', 'parentAreaName', 'a222_size', 'a224_size', 'a226_size', 'a228_size', 'a229', 'a230', 'createdDate', 'a222', 'a231', 'a224', 'a226', 'a228'];

          } else if (this.dataService.selectedTabMenuUrl == 'audio-gallery') {
            this.dataService.noChildren = false;
            this.dataService.removeColumnList = ['areaId', 'areaLevel', 'areaName', 'parentAreaId', 'parentAreaName', 'fileName', 'createdDate', 'isLive', 'size', 'imageName', 'order', 'flag', 'isNew', 'caption', 'hashKey', 'url', 'file', 'isHome', 'isPriority', 'fileTitle','content','link'];
          } else if (this.dataService.selectedTabMenuUrl == 'video-gallery') {
            this.dataService.noChildren = false;
            this.dataService.removeColumnList = ['areaId', 'areaLevel', 'areaName', 'parentAreaId', 'parentAreaName', 'fileName', 'createdDate', 'isLive', 'size', 'imageName', 'order', 'flag', 'isNew', 'caption', 'hashKey', 'url', 'file', 'isHome', 'isPriority', 'fileTitle','content','publishedDate'];
          }else if (this.dataService.selectedTabMenuUrl == 'Others') {
            this.dataService.tableHeaderData = 'Type';
            this.dataService.withChildren = false;
            this.dataService.removeColumnList = [ 'quarter', 'areaId', 'areaLevel', 'areaName', 'parentAreaId', 'parentAreaName', 'fileName', 'createdDate', 'isLive', 'size', 'order', 'flag', 'isNew', 'caption', 'imageName', 'content', 'hashKey', 'fileTitle', 'link', 'url', 'file', 'isHome', 'isPriority'];
          }
          else {
            this.dataService.noChildren = false;
            this.dataService.removeColumnList = ['content','year', 'quarter','areaId', 'areaLevel', 'areaName', 'parentAreaId', 'parentAreaName', 'fileName', 'createdDate', 'isLive', 'size', 'imageName', 'order', 'flag', 'isNew', 'caption', 'hashKey', 'link', 'url', 'file', 'isHome', 'isPriority', 'fileTitle'];
          }
        }
        else {
          this.dataService.removeColumnList = ['keyContacts'];
        }

      }
      else if (this.dataService.selectedTabMenuUrl == undefined) {
        if (element.type == menu.title) {
          this.dataService.tableData = [];
          this.dataService.showNoCmsData = false;
          this.dataService.selectedMenuLists = element.keyVal[0];
          this.dataService.uploadedFile = "";
          this.dataService.fileName = "";
          // if(this.dataService.shortDescription ==undefined)
          this.dataService.shortDescription = element.keyVal[0].listOfCmsDataDto[0].viewType;
          for (let i = 0; i < element.keyVal[0].listOfQuestionModel.length; i++) {
            element.keyVal[0].listOfQuestionModel[i].value = null;

            if (element.keyVal[0].listOfQuestionModel[i].label == "Priority") {
              this.dataService.radioOptions = element.keyVal[0].listOfQuestionModel[i].options;
              for (let r = 0; r < this.dataService.radioOptions.length; r++) {
                if (this.dataService.radioOptions[r].value == "No")
                  element.keyVal[0].listOfQuestionModel[i].value = this.dataService.radioOptions[r].value;
                this.dataService.isPriority = false;
              }

            }

            if (element.keyVal[0].listOfQuestionModel[i].label == "Page Description") {
              element.keyVal[0].listOfQuestionModel[i].value = this.dataService.shortDescription;
            }
          }
          for (let i = 0; i < element.keyVal[0].listOfCmsDataDto.length; i++) {
            this.dataService.viewName = element.keyVal[0].listOfCmsDataDto[i].viewName;
            this.dataService.cmsType = element.keyVal[0].listOfCmsDataDto[i].cmsType;
          }
          this.dataService.fetchedDropdown = element.keyVal[0].listOfCmsDataDto[0].viewContent[this.staticHomeService.selectedLang];

          for (let i = 0; i < this.dataService.fetchedDropdown.length; i++) {
            if (this.dataService.fetchedDropdown[i].section == menu.title 
                || this.dataService.fetchedDropdown[i].key == menu.title) { //key equal in case of banner
              this.dataService.dataOfHomePage = this.dataService.fetchedDropdown[i].data;
              if (Object.keys(this.dataService.dataOfHomePage[0]).length === 0) {
                this.dataService.noDataFound = true;
              } else {
                this.dataService.noDataFound = false;
              }
              this.dataService.isNew = this.dataService.dataOfHomePage[i].isNew;
              this.dataService.selectedSectionName = this.dataService.fetchedDropdown[i].section;
            }
          }
          this.dataService.removeColumnList = [];
          this.dataService.noChildren = false;
          if (element.type == "Banners") {
            this.dataService.removeColumnList = ['file','year', 'quarter','areaId', 'areaLevel', 'areaName', 'parentAreaId', 'parentAreaName', 'createdDate', 'isLive', 'size', 'order', 'flag', 'isNew', 'caption', 'url', 'isLive', 'publishedDate', 'fileName', 'hashKey', 'content', 'title', 'fileTitle', 'link','isPriority', 'isHome'];
          }
          else if (element.type == "Achievement and Progress") {
            this.dataService.removeColumnList = ['file','year', 'quarter','areaId', 'areaLevel', 'areaName', 'parentAreaId', 'parentAreaName', 'createdDate', 'isLive', 'size', 'order', 'flag', 'isNew', 'caption', 'url', 'isLive', 'fileName', 'hashKey', 'fileTitle', 'link', 'imageName','isPriority', 'isHome'];
          }
          else {
            this.dataService.removeColumnList = ['file','year', 'quarter','areaId', 'areaLevel', 'areaName', 'parentAreaId', 'parentAreaName', 'createdDate', 'isLive', 'size', 'order', 'flag', 'isNew', 'caption', 'imageName', 'hashKey', 'isPriority', 'isHome', 'fileName', 'url'];
          }
        }
      }
    });
  }

  getInnerPageQuestions() {
    let keyVal: any;
    this.apiService.getResourceQuestionList().subscribe(res => {
      this.dataService.questions = res;
      this.dataService.allMenuContents = Object.keys(this.dataService.questions).map(key => ({
        type: key,
        keyVal: this.dataService.questions[key]
      }));
    })
  }


  onDrag(args) {
    let [e, el] = args;
    // do something
  }
  onDrop(args) {
    let [e, el] = args;
    // do something
  }

  onOver(args) {
    let [e, el, container] = args;
    // do something
    $(el).stopPropagation();
  }

  onOut(args) {
    let [e, el, container] = args;
    // do something
  }
  alertDblClick(msg, event) {
    event.stopPropagation();
    console.log(msg);

  }

}
