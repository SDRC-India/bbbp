import { HttpClient, HttpEvent, HttpRequest } from '@angular/common/http';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { document } from 'angular-bootstrap-md/utils/facade/browser';
import { IMyDateModel, IMyDpOptions, MyDatePicker } from 'mydatepicker';
import { DragulaService } from 'ng2-dragula';
import { Observable } from 'rxjs';

import { DomSanitizer } from '../../../../../node_modules/@angular/platform-browser';
import { Constants } from '../../../constants';
import { StaticHomeService } from '../../../service/static-home.service';
import { StaticPageService } from '../../static-page/services/static-page.service';
import { ApiService } from '../services/api.service';
import { DataService } from '../services/data.service';

declare var $: any;

@Component({
  selector: 'app-content-section',
  templateUrl: './content-section.component.html',
  styleUrls: ['./content-section.component.scss']
})
export class ContentSectionComponent implements OnInit {
  @ViewChild('myInput') myInput: ElementRef;
  // apiService: ApiService;
  dataService: DataService;
  staticHomeService: StaticHomeService;
  staticService: StaticPageService;
  warningMsg: any;
  districtTableData: any;
  
  gateway=Constants.HOME_URL+Constants.CMS_URL;
  video: string = "https://www.youtube.com/embed/CD-E-LDc384"
  // file: any = [];
  message: any;
  public mytime: Date = new Date();
  currentYear: any = this.mytime.getUTCFullYear();
  currentDate: any = this.mytime.getUTCDate() + 1;
  currentMonth: any = this.mytime.getUTCMonth() + 1; //months from 1-12
  // public selDate: IMyDate = {year: 0, month: 0, day: 0};
  public selDate: string = '';
  public myDatePickerOptions: IMyDpOptions = {
    disableSince: { year: this.currentYear, month: this.currentMonth, day: this.currentDate },
    showTodayBtn: true,
    inline: false,
    width: '37%',
    editableDateField: false,
    openSelectorOnInputClick: true,
    dateFormat: 'dd-mm-yyyy',
    yearSelector: false,
    showClearDateBtn: true,
    markCurrentDay: true
  };
  field: any;
  val: any;
  @ViewChild('mydp') mydp: MyDatePicker;
  url: any;
  constructor(private apiService: ApiService, private dataServiceProvider: DataService, private staticHomeServiceProvider: StaticHomeService, private dragulaService: DragulaService,
    private http: HttpClient, private staticServiceProvider: StaticPageService,
    private sanitizer: DomSanitizer) {
    // this.apiService = apiServiceProvider;
    this.dataService = dataServiceProvider;
    this.staticHomeService = staticHomeServiceProvider;
    this.staticService = staticServiceProvider;

  }
  ngOnInit() {
    this.dataService.a = 1;
    this.dataService.q = 1;
    this.dataService.m = 1;
    this.dataService.s = 1;
    this.dataService.sc = 1;
    this.dataService.d = 1;
    this.dataService.dc = 1;
    this.dataService.k = 1;
    this.getLandingPageDataOfCms();
  }
  // landing page api call
  getLandingPageDataOfCms() {
    this.apiService.getCmsLandingPageData().subscribe(res => {
      this.dataService.landingPagedata = res;
      this.dataService.landingPageLeftBlockData = this.dataService.landingPagedata.cMSRecentContent;
      this.dataService.landingPageRightBlockData = this.dataService.landingPagedata.cMSRecentActivity;
    });
  }


  //  preiew pdf files for landing page
  openPDF(category) {
    this.url = this.sanitizer.bypassSecurityTrustResourceUrl(Constants.HOME_URL + 'cms/downloadCmsDoc?fileName= ' + category + '&inline=' + true);
    $("#myModal").modal("show");
  }

  // editing preview content value

  // editing page description with saving
  editPageDescription() {
    this.dataService.ifEditingDescription = null;
    $("#editCmsPageDescription").css("background", "#fff");
  }

  editingPageDescription(editPagedescription) {
    for (let i = 0; i < editPagedescription.length; i++) {
      if (editPagedescription[i].label == "Page Description") {
        editPagedescription[i].value = document.getElementById('editCmsPageDescription').value.trim();
        this.dataService.shortDescription = editPagedescription[i].value;
        if (this.dataService.shortDescription == "") {
          this.dataService.errorFound = "Page Description should not be blank.";
          $("#error").modal('show');
          return false;
        }
        break;
      }
    }
    this.http.get(Constants.HOME_URL + Constants.CMS_URL + 'editCmsDescription?viewName=' + this.dataService.viewName + '&description=' + this.dataService.shortDescription + '&pageLanguage=' + this.staticHomeService.selectedLang).subscribe((data) => {
      this.dataService.ifEditingDescription = true;
      $("#editCmsPageDescription").css("background", "#ddd");
      $("#pageDescriptionSuccess").modal('show');
    }, err => {
      $("#error").modal('show');
    });
  }

  // set type by checkbox

  setType(checkfFeld, formModel) {
    let checkBox = document.getElementById(checkfFeld.columnName);
    if (checkBox.checked == true) {
      checkfFeld.value = false;
      $("#publish-button").attr("disabled", true);
      if (checkfFeld.label == 'File') {
        let dependent1 = checkfFeld.dependentColumn.split(',')[1];
        let dependent2 = checkfFeld.dependentColumn.split(",", 1);
        $("#div" + dependent1).show();
        $("#div" + dependent2[0]).show();
      } else {
        $("#div" + checkfFeld.dependentColumn).show();
      }

    } else {
      for (let index = 0; index < formModel.length; index++) {
        if (formModel[index].label == 'Content' || formModel[index].label == 'Upload File'
          || formModel[index].label == 'File Name' || formModel[index].label == 'Upload Image'
          || formModel[index].label == 'Link') {
          formModel[index].value = "";
        }
      }
      checkfFeld.value = null;
    }
  }

  getObjectByColumnName(dependentColumnName, qArray) {
    for (let i = 0; i < qArray.length; i++) {
      const element = qArray[i];
      if (dependentColumnName == element.columnName) {
        return element;
      }
    }
  }

  // set priority by radio buttons
  setPriority(prioOrityField, options, event) {
    let radioBtn = document.getElementById(prioOrityField.columnName);
    if (options.value == "Yes") {
      this.dataService.isPriority = true;
      prioOrityField.value = true;
    } else {
      this.dataService.isPriority = false;
      prioOrityField.value = false;
    }
  }

  // set isHome priority in whatsnew page


  // editing row data of 1st table
  editRowOfTimeperiod(selectedOption, sectionIndex, rowData) {
    this.dataService.a = 1;
    this.dataService.m = 1;
    this.dataService.k = 1;
    this.dataService.searchTextsOfDependentTable = "";
    this.selDate = undefined;
    this.dataService.uploadedFile = "";
    this.dataService.imageUploadedFile = "";
    this.dataService.caption = "";
    for (let r = 0; r < rowData.length; r++) {
      if (rowData[r].label == "Timeperiod" || rowData[r].label == "Document Type" || rowData[r].label == "Album Name") {
        rowData[r].value = selectedOption;
        this.dataService.showAddOption = false;
        this.dataService.showEditOption = true;
        this.dataService.showEditCallOption = false;
        $("#" + rowData[r].columnName).attr("disabled", true);
        $("#" + rowData[r].columnName).css("background", "#ddd");
      }
      if (rowData[r].label == "Title" || rowData[r].label == "Publish Date"
        || rowData[r].label == "Upload File" || rowData[r].label == "Upload Image"
        || rowData[r].label == "Caption" || rowData[r].label == "Select District"
        || rowData[r].label == "Select State" || rowData[r].label == "Year" || rowData[r].label == "Quarter") {
        rowData[r].value = undefined;
      }
    }
    this.dataService.indexOfSection = sectionIndex;
    this.dataService.selectedRow = selectedOption;
    this.dataService.selectedSectionName = selectedOption;
    $("#publish-button").removeAttr("disabled");
    $(".selectedTableInfo").show();

    this.dataService.tableData = this.dataService.fetchedDropdown.filter(d => d.section == selectedOption)[0].data;
    // if (this.dataService.indexOfSection > 0) {
    if (Object.keys(this.dataService.tableData[0]).length === 0) {
      this.dataService.noDataFound = true;
      $("#hideTableIfNoData").hide();
    } else {
      $("#hideTableIfNoData").show();
      this.dataService.noDataFound = false;
    }
    // }
    $('html,body').animate({
      scrollTop: $('#selectedRowDataInTable').offset().top - 250
    }, 'slow');
    return false;
  }


  // editing existed section into 1st table of (sanction order, letters, documents, photo gallery)
  editSection(editFielddata) {
    for (let e = 0; e < editFielddata.length; e++) {
      if (editFielddata[e].label == "Document Type" || editFielddata[e].label == "Timeperiod" || editFielddata[e].label == "Album Name") {
        $("#" + editFielddata[e].columnName).removeAttr("disabled");
        $("#" + editFielddata[e].columnName).css("background", "#fff");
        $("#publish-button").attr("disabled", true);
        this.dataService.showAddOption = false;
        this.dataService.showEditOption = false;
        this.dataService.showEditCallOption = true;
      }
    }
  }
  editNewSections(editField) {
    this.dataService.isUpdate = true;
    this.dataService.pageLanguage = this.staticHomeService.selectedLang;
    this.dataService.languageIndex = this.dataService.indexOfSection;
    this.dataService.showAddOption = false;
    for (let e = 0; e < editField.length; e++) {
      if (editField[e].controlType == 'textbox' && editField[e].label == "Document Type" || editField[e].label == "Timeperiod" || editField[e].label == "Album Name") {
        if (editField[e].value.trim() == "") {
          this.dataService.errorFound = "Please " + editField[e].section + ".";
          $("#error").modal('show');
          return false;
        } else {
          if (editField[e].label == "Document Type"
            || editField[e].label == "Timeperiod"
            || editField[e].label == "Album Name") {
            this.dataService.newSectionName = editField[e].value;
          }
          this.addSections(editField);
        }
      }
    }
  }


  // adding new section into 1st table of (sanction order, letters, documents, photo gallery)
  addNewSections(sectionField) {
    this.dataService.isUpdate = false;
    this.dataService.pageLanguage = this.staticHomeService.selectedLang;

    if (Object.keys(this.dataService.fetchedDropdown[0]).length === 0) {
      this.dataService.languageIndex = 0;
    } else {
      this.dataService.languageIndex = this.dataService.fetchedDropdown.length;
    }

    for (let s = 0; s < sectionField.length; s++) {
      if (sectionField[s].controlType == 'textbox' && sectionField[s].label == "Document Type" || sectionField[s].label == "Timeperiod" || sectionField[s].label == "Album Name") {
        if (sectionField[s].value.trim() == "") {
          this.dataService.errorFound = "Please " + sectionField[s].section + ".";
          $("#error").modal('show');
          return false;
        } else {
          if (sectionField[s].label == "Document Type"
            || sectionField[s].label == "Timeperiod"
            || sectionField[s].label == "Album Name") {
            this.dataService.newSectionName = sectionField[s].value;
          }
          this.addSections(sectionField);
        }
      }
    }
  }
  addSections(sectionField) {
    this.http.get(Constants.HOME_URL + Constants.CMS_URL + 'sectionAddCmsContent?viewName=' + this.dataService.viewName + '&languageIndex=' + this.dataService.languageIndex
      + '&section=' + this.dataService.newSectionName.trim() + '&pageLanguage=' + this.dataService.pageLanguage + '&isUpdate=' + this.dataService.isUpdate).subscribe((data) => {
        // $("#" + sectionField.columnName).attr("disabled", true);
        this.dataService.showEditOption = true;
        this.dataService.showEditCallOption = false;
        // for (let i = 0; i < this.dataService.selectedMenuLists.listOfQuestionModel.length; i++) {
        //   if (this.dataService.selectedMenuLists.listOfQuestionModel[i].label == "Timeperiod"
        //     || this.dataService.selectedMenuLists.listOfQuestionModel[i].label == "Document Type"
        //     || this.dataService.selectedMenuLists.listOfQuestionModel[i].label == "Album Name") {
        //     $("#" + this.dataService.selectedMenuLists.listOfQuestionModel[i].columnName).attr("disabled", true);
        //     $("#" + this.dataService.selectedMenuLists.listOfQuestionModel[i].columnName).css("background", "#ddd");
        //   }
        // }
        this.dataService.selectedRowAdded = '';
        this.dataService.successMsg = this.dataService.newSectionName + " added successfully. "
        $("#addNewSection").modal('show');
        this.cancelOrders(sectionField);
      }, err => {
        this.dataService.errorFound = "Some error found"
        $("#error").modal('show');
      });
  }


  // reset fields
  cancelOrders(fieldDataCancel) {
    this.dataService.showEditOption = false;

    if (this.dataService.cmsType == 'nested tabular')
      this.dataService.showAddOption = true;

    this.dataService.showEditCallOption = false;
    this.dataService.tableData = [];
    this.dataService.file = [];
    this.dataService.uploadedFile = "";
    this.dataService.uploadedFilepath = "";
    this.dataService.fileName = "";
    this.dataService.imageUploadedFile = '';
    this.dataService.imageUploadedFilepath = '';
    this.dataService.imageFileName = "";
    this.dataService.uploadedAudio = "";
    this.selDate = "";
    this.selDate = undefined;
    this.dataService.indexOfEachRowFirst = undefined;
    this.dataService.indexOfEachRowSecond = undefined;
    this.dataService.indexOfSection = undefined;
    this.dataService.noDataFound = false;
    this.dataService.selectedFileName = undefined;
    this.dataService.selectedImageName = undefined;
    this.dataService.caption = "";
    this.dataService.isPriority = undefined;
    this.dataService.submitModel = {};
    this.dataService.editClicked = false;
    this.dataService.selectedFilesToUpload = {};
    if (this.dataService.selectedTabMenuUrl == 'aboutus') {
      document.getElementById('contentVal').value = "";
    }

    for (let i = 0; i < fieldDataCancel.length; i++) {
      if (fieldDataCancel[i].label == "Timeperiod" || fieldDataCancel[i].label == "Title"
        || fieldDataCancel[i].label == "Publish Date" || fieldDataCancel[i].label == "Document Type"
        || fieldDataCancel[i].label == "Album Name" || fieldDataCancel[i].label == "Link"
        || fieldDataCancel[i].label == "File Name" || fieldDataCancel[i].label == "Content"
        || fieldDataCancel[i].label == "Upload File" || fieldDataCancel[i].label == "Caption"
        || fieldDataCancel[i].label == "Upload Image" || fieldDataCancel[i].label == "Upload Audio"
        || fieldDataCancel[i].label == "Select District" || fieldDataCancel[i].label == "Select State"
        || fieldDataCancel[i].label == "Year"
        || fieldDataCancel[i].label == "Quarter") {
        fieldDataCancel[i].value = undefined;

        if (fieldDataCancel[i].label == "Document Type"
          || fieldDataCancel[i].label == "Timeperiod" || fieldDataCancel[i].label == "Album Name") {
          $("#" + fieldDataCancel[i].columnName).removeAttr("disabled");
          $("#" + fieldDataCancel[i].columnName).css("background", "#fff");
          $("#publish-button").attr("disabled", true);
          this.dataService.showEditOption = false;
          this.dataService.showEditCallOption = false;
          this.dataService.selectedRow = '';
          fieldDataCancel[i].value = "";
        }
      }
      if (fieldDataCancel[i].label == "Content Type"
        || fieldDataCancel[i].label == "Image"
        || fieldDataCancel[i].label == "Link Type") {
        let checkBoxId = document.getElementById(fieldDataCancel[i].columnName);
        let checkBoxFields = fieldDataCancel[i].dependentColumn;
        if (checkBoxId.checked == true) {
          $("#div" + checkBoxFields).hide();
          checkBoxId.checked = false;
        }
      }
      if (fieldDataCancel[i].label == "File") {
        let dependent1 = fieldDataCancel[i].dependentColumn.split(",", 1);
        let dependent2 = fieldDataCancel[i].dependentColumn.split(",")[1];
        let checkBoxId = document.getElementById(fieldDataCancel[i].columnName);
        if (checkBoxId.checked == true) {
          $("#div" + dependent1[0]).hide();
          $("#div" + dependent2).hide();
          checkBoxId.checked = false;
        }
      }

      // if (fieldDataCancel[i].label == "Priority") {
      //   var radioButton = document.getElementById(fieldDataCancel[i].columnName +
      //     '_' + (fieldDataCancel.isPriority ? 'Yes' : 'No'));
      //   radioButton.checked = false;
      // }
      if (fieldDataCancel[i].label == "Priority") {
        for (let r = 0; r < this.dataService.radioOptions.length; r++) {
          if (this.dataService.radioOptions[r].value == "No") {
            fieldDataCancel[i].value = this.dataService.radioOptions[r].value;
            this.dataService.isPriority = false;
          }
        }
      }
    }
  }
  newValue(fieldvalue) {
    for (let i = 0; i < fieldvalue.length; i++) {
      if (fieldvalue[i].label == "Title") {
        this.dataService.title = fieldvalue[i].value;
      }
      if (fieldvalue[i].label == "File Name") {
        this.dataService.fileName = fieldvalue[i].value;

        if (this.dataService.cmsType == 'news') {
          this.dataService.fileTitle = fieldvalue[i].value;
          $("#publish-button").removeAttr("disabled");
        }
      }
      if (fieldvalue[i].label == "Content") {
        this.dataService.content = fieldvalue[i].value;
      }
      if (fieldvalue[i].label == "Caption") {
        this.dataService.title = fieldvalue[i].value;
        this.dataService.caption = fieldvalue[i].value;
      }
      if (fieldvalue[i].label == "Link") {
        this.dataService.url = fieldvalue[i].value;
      }
      if (fieldvalue[i].label == "Name") {
        this.dataService.kcName = fieldvalue[i].value;
      }
      if (fieldvalue[i].label == "Address") {
        this.dataService.kcAddress = fieldvalue[i].value;
      }
      if (fieldvalue[i].label == "E-mail") {
        this.dataService.kcEmail = fieldvalue[i].value;
      }
      if (fieldvalue[i].label == "Phone No. ") {
        this.dataService.kcPhoneNo = fieldvalue[i].value;
      }
      if (fieldvalue[i].label == "Department") {
        this.dataService.kcDepartment = fieldvalue[i].value;
      }
      if (fieldvalue[i].label == "Designation") {
        this.dataService.kcDesignation = fieldvalue[i].value;
      }
      if (fieldvalue[i].label == "District Name") {
        this.dataService.kcDistrictName = fieldvalue[i].value;
      }
      if (fieldvalue[i].label == "State Name") {
        this.dataService.kcStateName = fieldvalue[i].value;
      }
    }
  }
  pastedVal(fieldvalue) {
    for (let i = 0; i < fieldvalue.length; i++) {
      if (fieldvalue[i].label == "Title") {
        this.dataService.title = fieldvalue[i].value;
      }
      if (fieldvalue[i].label == "File Name") {
        this.dataService.fileName = fieldvalue[i].value;
      }
      if (fieldvalue[i].label == "Content") {
        this.dataService.content = fieldvalue[i].value;
      }
      if (fieldvalue[i].label == "Caption") {
        this.dataService.title = fieldvalue[i].value;
        this.dataService.caption = fieldvalue[i].value;
      }
      if (fieldvalue[i].label == "Link") {
        this.dataService.url = fieldvalue[i].value;
      }
      if (fieldvalue[i].label == "Name") {
        this.dataService.kcName = fieldvalue[i].value;
      }
      if (fieldvalue[i].label == "Address") {
        this.dataService.kcAddress = fieldvalue[i].value;
      }
      if (fieldvalue[i].label == "E-mail") {
        this.dataService.kcEmail = fieldvalue[i].value;
      }
      if (fieldvalue[i].label == "Phone No. ") {
        this.dataService.kcPhoneNo = fieldvalue[i].value;
      }
      if (fieldvalue[i].label == "Department") {
        this.dataService.kcDepartment = fieldvalue[i].value;
      }
      if (fieldvalue[i].label == "Designation") {
        this.dataService.kcDesignation = fieldvalue[i].value;
      }
    }
  }

  updateItem(item) {
    this.dataService.allMenuContents.map((todo, i) => {
      if (todo.type == item.type) {
        this.dataService.allMenuContents[i] = item;
      }
    });
  }
  refreshEditedData(selectedData) {
    this.dataService.a = 1;
    this.dataService.q = 1;
    this.dataService.m = 1;
    this.dataService.s = 1;
    this.dataService.sc = 1;
    this.dataService.d = 1;
    this.dataService.dc = 1;
    this.dataService.k = 1;
    this.dataService.selectedFilesToUpload = {};
    this.dataService.submitModel = {};
    this.apiService.getResourceQuestionListByViewName(this.dataService.viewName).subscribe(res => {
      this.dataService.questions = res;
      this.dataService.eachMenuContents = Object.keys(this.dataService.questions).map(key => ({
        type: key,
        keyVal: this.dataService.questions[key]
      }));
      this.updateItem(this.dataService.eachMenuContents[0]);
      this.dataService.eachMenuContents.forEach(element => {
        if (element.type == this.dataService.selectedTabMenuUrl) {
          for (let i = 0; i < selectedData.length; i++) {
            if (selectedData[i].dependecy == true) {
              this.dataService.languageIndex = this.dataService.indexOfSection;
            }
            if (selectedData[i].label == "Timeperiod"
              || selectedData[i].label == "Document Type"
              || selectedData[i].label == "Album Name") {
              this.dataService.selectedRow = selectedData[i].value;
            }
            if (selectedData[i].dependecy == false
              && selectedData[i].dependentCondition[0] != 'hasChildren'
              && this.dataService.languageIndex == 0) {
              this.dataService.selectedMenuLists.listOfCmsDataDto[0].viewContent[this.staticHomeService.selectedLang][0].data = element.keyVal[0].listOfCmsDataDto[0].viewContent[this.staticHomeService.selectedLang][0].data;
              if (Object.keys(element.keyVal[0].listOfCmsDataDto[0].viewContent[this.staticHomeService.selectedLang][0].data[0]).length === 0) {
                this.dataService.noDataFound = true;
                return false;
              } else {
                this.dataService.noDataFound = false;
              }
            }
            else if (selectedData[i].dependecy == true
              && this.dataService.languageIndex >= 0) {
              this.dataService.fetchedDropdown = element.keyVal[0].listOfCmsDataDto[0].viewContent[this.staticHomeService.selectedLang];
              this.dataService.noDataFound = false;
              if (this.dataService.languageIndex > 0) {
                if (Object.keys(this.dataService.tableData[0]).length === 0) {
                  $(".selectedTableInfo").hide();
                }
              }
              if (this.dataService.languageIndex >= 0) {
                this.dataService.tableData = this.dataService.fetchedDropdown.filter(d => d.section == this.dataService.selectedRow)[0].data;
                $('#hideTableIfNoData').show();
                if (Object.keys(this.dataService.tableData[0]).length > 0) {
                  $(".selectedTableInfo").show();
                  for (let i = 0; i < this.dataService.fetchedDropdown.length; i++) {
                    this.dataService.tableData = this.dataService.fetchedDropdown.filter(d => d.section == this.dataService.selectedRow)[0].data;
                    $('html,body').animate({
                      scrollTop: $('#selectedRowDataInTable').offset().top - 250
                    }, 'slow');
                    return false;
                  }
                }
              }
            }
          }
        }
        else if (this.dataService.selectedTabMenuUrl == undefined) {
          if (element.type == this.dataService.selectedTabMenu.title) {
            this.dataService.fetchedDropdown = element.keyVal[0].listOfCmsDataDto[0].viewContent[this.staticHomeService.selectedLang];
            for (let i = 0; i < this.dataService.fetchedDropdown.length; i++) {
              if (this.dataService.fetchedDropdown[i].section == this.dataService.selectedTabMenu.title) {
                this.dataService.dataOfHomePage = element.keyVal[0].listOfCmsDataDto[0].viewContent[this.staticHomeService.selectedLang][0].data;
                $('html,body').animate({
                  scrollTop: $('#selectedRowDataInTable').offset().top - 250
                }, 'slow');
                return false;
              }
            }
            this.dataService.noChildren = false;
          }
        }
      });
    })
    this.dataService.uploadedFile = "";
    this.dataService.imageUploadedFile = "";
    this.dataService.uploadedAudio = "";
    this.dataService.selectedAudioName = "";
    this.dataService.selectedImageName = "";
    this.dataService.selectedFileName = "";
    this.dataService.fileName = "";
    this.selDate = undefined;
    this.dataService.indexOfEachRowFirst = undefined;
    this.dataService.indexOfEachRowSecond = undefined;
    this.dataService.file = [];
    for (let i = 0; i < selectedData.length; i++) {
      if (selectedData[i].label == "Title" || selectedData[i].label == "Publish Date"
        || selectedData[i].label == "Link" || selectedData[i].label == "File Name"
        || selectedData[i].label == "Content" || selectedData[i].label == "Upload File"
        || selectedData[i].label == "Caption" || selectedData[i].label == "Description"
        || selectedData[i].label == "Upload Image" || selectedData[i].label == "Upload Audio"
        || selectedData[i].label == "URL" || selectedData[i].label == "Select District"
        || selectedData[i].label == "Select State" || selectedData[i].label == "Year"
        || selectedData[i].label == "Quarter") {
        selectedData[i].value = undefined;

      }
      if (selectedData[i].label == "File") {
        let checkBoxId = document.getElementById(selectedData[i].columnName);
        let dependent1 = selectedData[i].dependentColumn.split(",")[1];
        let dependent2 = selectedData[i].dependentColumn.split(",", 1);
        if (checkBoxId.checked == true) {
          checkBoxId.checked = false;
          $("#div" + dependent1).hide();
          $("#div" + dependent2[0]).hide();
        }
      }
      if (selectedData[i].label == "Content Type"
        || selectedData[i].label == "Image"
        || selectedData[i].label == "Link Type") {
        let checkBoxId = document.getElementById(selectedData[i].columnName);
        let checkBoxFields = selectedData[i].dependentColumn;
        if (checkBoxId.checked == true) {
          checkBoxId.checked = false;
          $("#div" + checkBoxFields).hide();
        }
      }
      if (selectedData[i].label == "Priority") {
        // var radioButton = document.getElementById(selectedData[i].columnName +
        //   '_' + (selectedData.isPriority ? 'Yes' : 'No'));
        for (let r = 0; r < this.dataService.radioOptions.length; r++) {
          if (this.dataService.radioOptions[r].value == "No") {
            selectedData[i].value = this.dataService.radioOptions[r].value;
            this.dataService.isPriority = false;
          }

        }
      }
    }
  }

  refreshAddedData() {
    this.dataService.selectedFilesToUpload = {};
    this.dataService.submitModel = {};
    this.apiService.getResourceQuestionListByViewName(this.dataService.viewName).subscribe(res => {
      this.dataService.questions = res;
      this.dataService.eachMenuContents = Object.keys(this.dataService.questions).map(key => ({
        type: key,
        keyVal: this.dataService.questions[key]
      }));
      this.updateItem(this.dataService.eachMenuContents[0]);
      this.dataService.eachMenuContents.forEach(element => {
        if (element.type == this.dataService.selectedTabMenuUrl) {
          for (let i = 0; i < this.dataService.selectedMenuLists.listOfQuestionModel.length; i++) {
            if (this.dataService.selectedMenuLists.listOfQuestionModel[i].dependecy == false
              && this.dataService.selectedMenuLists.listOfQuestionModel[i].dependentCondition[0] != 'hasChildren'
              && this.dataService.languageIndex == 0) {
              this.dataService.selectedMenuLists.listOfCmsDataDto[0].viewContent[this.staticHomeService.selectedLang][0].data = this.dataService.questions[element.type][0].listOfCmsDataDto[0].viewContent[this.staticHomeService.selectedLang][0].data;
            }
            if (this.dataService.selectedMenuLists.listOfQuestionModel[i].label == "Timeperiod"
              || this.dataService.selectedMenuLists.listOfQuestionModel[i].label == "Document Type"
              || this.dataService.selectedMenuLists.listOfQuestionModel[i].label == "Album Name") {
              this.dataService.selectedRow = this.dataService.selectedRowAdded;
            }
            if (this.dataService.selectedMenuLists.listOfQuestionModel[i].dependecy == false
              && this.dataService.selectedMenuLists.listOfQuestionModel[i].dependentCondition[0] != 'hasChildren'
              && this.dataService.languageIndex == 0) {
              this.dataService.selectedMenuLists.listOfCmsDataDto[0].viewContent[this.staticHomeService.selectedLang][0].data = this.dataService.questions[element.type][0].listOfCmsDataDto[0].viewContent[this.staticHomeService.selectedLang][0].data;
            }
            else if (this.dataService.selectedMenuLists.listOfQuestionModel[i].dependecy == true
              && this.dataService.languageIndex >= 0) {
              this.dataService.fetchedDropdown = this.dataService.questions[element.type][0].listOfCmsDataDto[0].viewContent[this.staticHomeService.selectedLang];
              this.dataService.noDataFound = false;
              if (this.dataService.languageIndex > 0) {
                if (Object.keys(this.dataService.tableData[0]).length === 0) {
                  $(".selectedTableInfo").hide();
                }
              }
              if (this.dataService.languageIndex >= 0) {
                if (Object.keys(this.dataService.tableData[0]).length > 0) {
                  $(".selectedTableInfo").show();
                  for (let i = 0; i < this.dataService.fetchedDropdown.length; i++) {
                    this.dataService.tableData = this.dataService.fetchedDropdown.filter(d => d.section == this.dataService.selectedRow)[0].data;
                    $('html,body').animate({
                      scrollTop: $('#selectedRowDataInTable').offset().top - 250
                    }, 'slow');
                    return false;
                  }
                }
              }
            }
          }
        }
      });
    })
  }

  refreshDeletedData() {
    this.dataService.submitModel = {};
    this.dataService.selectedFilesToUpload = {};
    this.apiService.getResourceQuestionListByViewName(this.dataService.viewName).subscribe(res => {
      this.dataService.questions = res;
      this.dataService.eachMenuContents = Object.keys(this.dataService.questions).map(key => ({
        type: key,
        keyVal: this.dataService.questions[key]
      }));
      this.updateItem(this.dataService.eachMenuContents[0]);
      this.dataService.eachMenuContents.forEach(element => {
        if (element.type == this.dataService.selectedTabMenuUrl) {
          for (let i = 0; i < this.dataService.selectedMenuLists.listOfQuestionModel.length; i++) {
            if (this.dataService.selectedMenuLists.listOfQuestionModel[i].dependecy == false
              && this.dataService.selectedMenuLists.listOfQuestionModel[i].dependentCondition[0] != 'hasChildren'
              && this.dataService.languageIndex == 0) {
              this.dataService.selectedMenuLists.listOfCmsDataDto[0].viewContent[this.staticHomeService.selectedLang][0].data = this.dataService.questions[element.type][0].listOfCmsDataDto[0].viewContent[this.staticHomeService.selectedLang][0].data;
            }
            else if (this.dataService.selectedMenuLists.listOfQuestionModel[i].dependecy == true
              && this.dataService.languageIndex >= 0) {
              this.dataService.fetchedDropdown = this.dataService.questions[element.type][0].listOfCmsDataDto[0].viewContent[this.staticHomeService.selectedLang];
              this.dataService.noDataFound = false;
              $('#hideTableIfNoData').hide();
              if (this.dataService.languageIndex > 0) {
                if (Object.keys(this.dataService.tableData[0]).length === 0) {
                  $(".selectedTableInfo").hide();
                }
              }
              if (this.dataService.languageIndex >= 0) {
                if (Object.keys(this.dataService.tableData[0]).length > 0) {
                  $(".selectedTableInfo").show();
                  for (let i = 0; i < this.dataService.fetchedDropdown.length; i++) {
                    this.dataService.tableData = this.dataService.fetchedDropdown.filter(d => d.section == this.dataService.selectedRow)[0].data;
                    $('html,body').animate({
                      scrollTop: $('#selectedRowDataInTable').offset().top - 250
                    }, 'slow');
                    return false;
                  }
                }
              }
            }
          }
        }
      });
    })
  }

  // show fetched data of about scheme
  resetPreviewFetchedData(previewData) {
    this.dataService.isImageSelected = false;
    this.apiService.getResourceQuestionListByViewName(this.dataService.viewName).subscribe(res => {
      this.dataService.questions = res;
      this.dataService.eachMenuContents = Object.keys(this.dataService.questions).map(key => ({
        type: key,
        keyVal: this.dataService.questions[key]
      }));
      this.updateItem(this.dataService.eachMenuContents[0]);
      this.dataService.eachMenuContents.forEach(element => {
        if (element.type == this.dataService.selectedTabMenuUrl) {
          if (this.dataService.cmsType! == 'aboutus') {
            for (let i = 0; i < previewData.length; i++) {
              if (previewData[i].dependecy == false
                && previewData[i].dependentCondition[0] != 'hasChildren'
                && this.dataService.languageIndex == 0) {
                this.dataService.contentVal = this.dataService.questions[element.type][0].listOfCmsDataDto[0].viewContent[this.staticHomeService.selectedLang][0].data[0].content;
              }
              if (previewData[i].label == "Upload Image") {
                this.dataService.imageUploadedFilepath = this.dataService.questions[element.type][0].listOfCmsDataDto[0].viewContent[this.staticHomeService.selectedLang][0].data[0].url;
                let imageFileFullName = this.dataService.questions[element.type][0].listOfCmsDataDto[0].viewContent[this.staticHomeService.selectedLang][0].data[0].imageName;
                this.dataService.imageUploadedFile = imageFileFullName != undefined ? imageFileFullName.substring(imageFileFullName.lastIndexOf('/') + 1) : imageFileFullName;
                if (this.dataService.imageUploadedFile == "") {
                  this.dataService.imageUploadedFile = this.dataService.questions[element.type][0].listOfCmsDataDto[0].viewContent[this.staticHomeService.selectedLang][0].data[0].imageName;
                }
                // this.dataService.imageUploadedFilepath = this.dataService.fetchedDropdownDetails[0].imageName;
                this.dataService.imageFileName = this.dataService.questions[element.type][0].listOfCmsDataDto[0].viewContent[this.staticHomeService.selectedLang][0].data[0].imageName;
                // this.dataService.file = [];
                previewData[i].value = null;
                this.dataService.selectedImageName = undefined;
              }
              if (previewData[i].label == "Upload File") {
                // this.dataService.uploadedFile = this.dataService.fetchedDropdownDetails[0].fileName;
                let fileFullName = this.dataService.questions[element.type][0].listOfCmsDataDto[0].viewContent[this.staticHomeService.selectedLang][0].data[0].fileName
                this.dataService.uploadedFile = fileFullName.substring(fileFullName.lastIndexOf('/') + 1);
                if (this.dataService.uploadedFile == "") {
                  this.dataService.uploadedFile = this.dataService.questions[element.type][0].listOfCmsDataDto[0].viewContent[this.staticHomeService.selectedLang][0].data[0].fileName;
                }
                previewData[i].value = null;
                this.dataService.selectedFileName = undefined;
                this.dataService.uploadedFilepath = this.dataService.questions[element.type][0].listOfCmsDataDto[0].viewContent[this.staticHomeService.selectedLang][0].data[0].fileName;
                this.dataService.fileName = this.dataService.questions[element.type][0].listOfCmsDataDto[0].viewContent[this.staticHomeService.selectedLang][0].data[0].fileName;
              }
              if (previewData[i].label == "File Name") {
                previewData[i].value = this.dataService.questions[element.type][0].listOfCmsDataDto[0].viewContent[this.staticHomeService.selectedLang][0].data[0].title;
                // this.dataService.file = [];
              }
            }
          }
        }
      });
    })
  }

  
  // file Upload
  onFileChange(event: any, acceptFileType, acceptFileSize, field) {

    // this.dataService.editClicked = false;

    if (acceptFileType.includes('application/pdf')) {
      this.dataService.acceptPdfType = acceptFileType;
      this.dataService.errorFilemsg = this.dataService.acceptPdfType.split('/')[1];
      this.dataService.acceptImageType = undefined;
      this.dataService.acceptAudioType = undefined;
    } else if (acceptFileType.includes('image/jpg,image/jpeg,image/png')) {
      this.dataService.acceptPdfType = undefined;
      this.dataService.acceptAudioType = undefined;
      this.dataService.acceptImageType = acceptFileType;
      this.dataService.errorImagejpg = this.dataService.acceptImageType.split('image/')[1];
      this.dataService.errorImageJpeg = this.dataService.acceptImageType.split('image/')[2];
      this.dataService.errorImagePng = this.dataService.acceptImageType.split('/')[3];
      this.dataService.errorImageMsg = this.dataService.errorImagejpg + this.dataService.errorImageJpeg + this.dataService.errorImagePng
    }
    else if (acceptFileType.includes('audio/mp3')) {
      this.dataService.acceptAudioType = acceptFileType;
      this.dataService.acceptPdfType = undefined;
      this.dataService.acceptImageType = undefined;
      this.dataService.errorAudioMsg = this.dataService.acceptAudioType.split('/')[1];
    }

    let files: any = event.target.files;
    if (files.length == 0) {
      if (this.dataService.acceptPdfType) {
        for (var i = 0; i < this.dataService.file.length; i++) {
          if (this.dataService.file[i].name == this.dataService.selectedFileName) {
            this.dataService.file.splice(i, 1);
            break;
          }
        }
        this.dataService.selectedFileName = undefined;
      } else if (this.dataService.acceptImageType) {
        this.dataService.imageUploadedFilepath = "";
        for (var i = 0; i < this.dataService.file.length; i++) {
          if (this.dataService.file[i].name == this.dataService.selectedImageName) {
            this.dataService.file.splice(i, 1);
            break;
          }
        }
        this.dataService.selectedImageName = undefined;
      }
    }
    //  if file type not matched
    for (let i = 0; i < files.length; i++) {
      let fileExtension = files[i].name.split('.').pop().toLowerCase();
      if (!(acceptFileType.includes(files[i].type) && acceptFileType.includes(fileExtension))) {
        field.value = null;
        if (acceptFileType == this.dataService.acceptImageType) {
          this.dataService.errorFound = "Please upload file in '" + this.dataService.errorImageMsg.toUpperCase() + "' format only.";
          $("#error").modal('show');
        } else if (acceptFileType == this.dataService.acceptPdfType) {
          this.dataService.errorFound = "Please upload file in '" + this.dataService.errorFilemsg.toUpperCase() + "' format only.";
          $("#error").modal('show');
        } else if (acceptFileType == this.dataService.acceptAudioType) {
          this.dataService.errorFound = "Please upload file in '" + this.dataService.errorAudioMsg.toUpperCase() + "' format only.";
          $("#error").modal('show');
        }
        event.target.value = null;
      }
      //  if file type matched
      else {
        if (acceptFileType.includes(this.dataService.acceptImageType)) {
          this.dataService.selectedImageName = event.target.files[i].name;
          for (let j = 0; j < this.dataService.selectedMenuLists.listOfQuestionModel.length; j++) {
            if (this.dataService.selectedMenuLists.listOfQuestionModel[j].label == "Upload Image") {
              if (event.target.files && event.target.files[i]) {
                var reader = new FileReader();
                reader.onload = (event: ProgressEvent) => {
                  this.dataService.isImageSelected = true;
                  this.dataService.imageUploadedFilepath = (<FileReader>event.target).result;
                }
                reader.readAsDataURL(event.target.files[i]);
                if (!this.dataService.file.length || !this.dataService.file.filter(d => acceptFileType.includes(d.type)).length) {
                  this.dataService.file.push(event.target.files[i]);
                  if ((event.target.files[i].size).toString().length <= 6) {
                    this.dataService.fileSize = ((event.target.files[i].size) / 1024).toFixed(2) + ' KB';
                    // return false;
                  } else if ((event.target.files[i].size).toString().length >= 7) {
                    this.dataService.fileSize = ((event.target.files[i].size) / 1048576).toFixed(2) + ' MB';
                    // return false;
                  }
                }
                else {
                  for (let j = 0; j < this.dataService.file.length; j++) {
                    if (acceptFileType.includes(this.dataService.file[j].type)) {
                      this.dataService.file.splice(j, 1, event.target.files[i]);
                    }
                  }
                }
              }
            }
          }
        } else if (acceptFileType.includes(this.dataService.acceptPdfType)) {


          //for rti cms
          if (this.dataService.selectedTabMenuUrl == 'Applications and Appeals') {
            if (((event.target.files[i].size) / 1048576) > parseInt(acceptFileSize)) {
              field.value = null;
              this.dataService.errorFound = "Uploaded file can not be more than " + acceptFileSize + " MB."
              $("#error").modal('show');
              event.target.value = null;
              delete this.dataService.selectedFilesToUpload[field.columnName];
              delete this.dataService.submitModel[field.columnName + '_size'];
              return false;
            } else {
              this.dataService.selectedFilesToUpload[field.columnName] = event.target.files[i];
              if ((event.target.files[i].size).toString().length <= 6) {
                this.dataService.submitModel[field.columnName + '_size'] = ((event.target.files[i].size) / 1024).toFixed(2) + ' KB';

              } else if ((event.target.files[i].size).toString().length >= 7) {
                this.dataService.submitModel[field.columnName + '_size'] = ((event.target.files[i].size) / 1048576).toFixed(2) + ' MB';

              }
            }
          }


          for (let k = 0; k < this.dataService.selectedMenuLists.listOfQuestionModel.length; k++) {
            if (this.dataService.selectedMenuLists.listOfQuestionModel[k].label == "Upload File") {
              if (!this.dataService.file.length || !this.dataService.file.filter(d => acceptFileType.includes(d.type)).length) {
                if (((event.target.files[i].size) / 1048576) > parseInt(acceptFileSize)) {
                  field.value = null;
                  this.dataService.errorFound = "Uploaded file can not be more than " + acceptFileSize + " MB."
                  $("#error").modal('show');
                  event.target.value = null;
                  return false;
                } else {
                  this.dataService.selectedFileName = event.target.files[i].name;
                  this.dataService.uploadedFilepath = this.dataService.selectedFileName;
                  this.dataService.file.push(event.target.files[i]);
                  if (acceptFileType.includes(event.target.files[i].type)) {
                    if ((event.target.files[i].size).toString().length <= 6) {
                      this.dataService.fileSize = ((event.target.files[i].size) / 1024).toFixed(2) + ' KB';
                      // return false;
                    } else if ((event.target.files[i].size).toString().length >= 7) {
                      this.dataService.fileSize = ((event.target.files[i].size) / 1048576).toFixed(2) + ' MB';
                      // return false;
                    }
                  }
                }
              } else {
                for (let j = 0; j < this.dataService.file.length; j++) {
                  if (acceptFileType.includes(this.dataService.file[j].type)) {
                    this.dataService.file.splice(j, 1, event.target.files[i]);
                    if (((event.target.files[i].size) / 1048576) > parseInt(acceptFileSize)) {
                      this.dataService.errorFound = "Uploaded file can not be more than " + acceptFileSize + " MB."
                      $("#error").modal('show');
                      event.target.value = null;
                      return false;
                    }
                  }
                }
              }
            }
          }
        } else if (acceptFileType.includes(this.dataService.acceptAudioType)) {

          for (let k = 0; k < this.dataService.selectedMenuLists.listOfQuestionModel.length; k++) {
            if (this.dataService.selectedMenuLists.listOfQuestionModel[k].label == "Upload Audio") {
              if (!this.dataService.file.length || !this.dataService.file.filter(d => acceptFileType.includes(d.type)).length) {
                if (((event.target.files[i].size) / 1048576) > parseInt(acceptFileSize)) {
                  field.value = null;
                  this.dataService.errorFound = "Uploaded file can not be more than " + acceptFileSize + " MB."
                  $("#error").modal('show');
                  event.target.value = null;
                  return false;
                } else {
                  this.dataService.selectedAudioName = event.target.files[i].name;
                  this.dataService.uploadedAudiopath = this.dataService.selectedAudioName;
                  this.dataService.file.push(event.target.files[i]);
                  if (acceptFileType.includes(event.target.files[i].type)) {
                    if ((event.target.files[i].size).toString().length <= 6) {
                      this.dataService.fileSize = ((event.target.files[i].size) / 1024).toFixed(2) + ' KB';
                      // return false;
                    } else if ((event.target.files[i].size).toString().length >= 7) {
                      this.dataService.fileSize = ((event.target.files[i].size) / 1048576).toFixed(2) + ' MB';
                      // return false;
                    }
                  }
                }
              } else {
                for (let j = 0; j < this.dataService.file.length; j++) {
                  if (acceptFileType.includes(this.dataService.file[j].type)) {
                    this.dataService.file.splice(j, 1, event.target.files[i]);
                    if (((event.target.files[i].size) / 1048576) > parseInt(acceptFileSize)) {
                      this.dataService.errorFound = "Uploaded file can not be more than " + acceptFileSize + " MB."
                      $("#error").modal('show');
                      event.target.value = null;
                      return false;
                    }
                  }
                }
              }
            }
          }
        }
      }
      if (this.dataService.selectedFileName != "" && this.dataService.selectedFileName != undefined) {
        this.dataService.uploadedFile = "";
        this.dataService.uploadedFilepath = "";
        this.dataService.fileName = "";
      }
      if (this.dataService.selectedImageName != "" && this.dataService.selectedImageName != undefined) {
        this.dataService.imageUploadedFile = "";
      }
      if (this.dataService.selectedAudioName != "" && this.dataService.selectedAudioName != undefined) {
        this.dataService.uploadedAudio = "";
      }
    }

    console.log(this.dataService.file);
  }
  onDateChanged(event: IMyDateModel) {
    this.selDate = event.formatted;
    this.dataService.publishedDate = this.selDate;
  }
  onToggleSelector(event: any) {
    event.stopPropagation();
    this.mydp.openBtnClicked();
  }

  // Calling this function clears the selected date
  onClearDate(event: any) {
    event.stopPropagation();
    this.mydp.clearDate();
  }

  // download fetched file
  downloadFetchedFileCms(fileName) {

    if (this.dataService.selectedTabMenu && this.dataService.selectedTabMenu.title == 'Banners') {
      window.open(fileName, '_blank');
    }
    else {
      window.location.href = Constants.HOME_URL + 'cms/downloadCmsDoc?fileName=' + fileName;
    }

  }
  editRowOfFetchedData(model, index) {
    this.dataService.indexOfEachRowFirst = index;
    $("#publish-button").removeAttr("disabled");
    for (let i = 0; i < this.dataService.selectedMenuLists.listOfQuestionModel.length; i++) {
      if (this.dataService.selectedMenuLists.listOfQuestionModel[i].label == "Upload File") {
        this.dataService.selectedMenuLists.listOfQuestionModel[i].value = "";
        this.dataService.selectedMenuLists.listOfQuestionModel[i].value = undefined;
      }
    }
    for (let i = 0; i < this.dataService.selectedMenuLists.listOfQuestionModel.length; i++) {
      if (this.dataService.selectedMenuLists.listOfQuestionModel[i].label == "Title") {
        this.dataService.selectedMenuLists.listOfQuestionModel[i].value = model.title;
        this.dataService.title = model.title;
      }
      if (this.dataService.selectedMenuLists.listOfQuestionModel[i].label == "Publish Date") {
        this.selDate = model.publishedDate;
        this.dataService.publishedDate = this.selDate;
      }
      if (this.dataService.selectedMenuLists.listOfQuestionModel[i].label == "Upload File") {
        this.dataService.uploadedFilepath = model.fileName;
        this.dataService.uploadedFile = model.fileName != undefined ? model.fileName.substring(model.fileName.lastIndexOf('/') + 1) : model.fileName;
        if (this.dataService.uploadedFile == undefined) {
          this.dataService.uploadedFile = model.fileName;
        }
        this.dataService.fileName = model.fileName;
      }
      if (this.dataService.selectedMenuLists.listOfQuestionModel[i].label == "Upload Audio") {
        this.dataService.uploadedAudiopath = model.fileName;
        this.dataService.uploadedAudio = model.fileName != undefined ? model.fileName.substring(model.fileName.lastIndexOf('/') + 1) : model.fileName;
        if (this.dataService.uploadedAudio == undefined) {
          this.dataService.uploadedAudio = model.fileName;
        }
        this.dataService.uploadedAudioName = model.fileName;
      }
      if (this.dataService.selectedMenuLists.listOfQuestionModel[i].label == "Upload Image") {
        this.dataService.imageUploadedFilepath = model.imageName;
        this.dataService.imageUploadedFile = model.imageName != undefined ? model.imageName.substring(model.imageName.lastIndexOf('/') + 1) : model.imageName;
        if (this.dataService.imageUploadedFile == undefined) {
          this.dataService.imageUploadedFile = model.imageName;
        }

        this.dataService.imageFileName = model.imageName;
      }
      if (this.dataService.selectedMenuLists.listOfQuestionModel[i].label == "Caption") {
        this.dataService.selectedMenuLists.listOfQuestionModel[i].value = model.title;
        this.dataService.caption = model.title;
        this.dataService.title = model.title;
      }
      if (this.dataService.selectedMenuLists.listOfQuestionModel[i].label == "Select District" && model.parentAreaName != null) {
        this.dataService.selectedMenuLists.listOfQuestionModel[i].key = model.areaId;
        this.dataService.selectedMenuLists.listOfQuestionModel[i].value = model.areaName;
      }
      if (this.dataService.selectedMenuLists.listOfQuestionModel[i].label == "Select State") {
        this.dataService.dataModel.areaId = model.areaId;
        this.dataService.dataModel.areaName = model.areaName;
        this.dataService.dataModel.parentAreaId = model.parentAreaId;
        this.dataService.dataModel.areaLevel = model.areaLevel;
        this.dataService.dataModel.parentAreaName = model.parentAreaName;


        this.dataService.selectedMenuLists.listOfQuestionModel[i].key = model.parentAreaId;
        this.dataService.selectedMenuLists.listOfQuestionModel[i].value = model.parentAreaName != null ?
          model.parentAreaName : model.areaName;
      }
      if (this.dataService.selectedMenuLists.listOfQuestionModel[i].label == "Year") {
        this.dataService.selectedMenuLists.listOfQuestionModel[i].value = model.year;
      }
      if (this.dataService.selectedMenuLists.listOfQuestionModel[i].label == "Quarter") {
        this.dataService.selectedMenuLists.listOfQuestionModel[i].value = model.quarter;
      }

    }
    $('html,body').animate({
      scrollTop: $('#selectedRowDataInField').offset().top - 250
    }, 'slow');
    return false;
  }

  deleteRowTableData(selectedRowData, indexofRowForDelete) {
    this.dataService.indexOfRowForDelete = indexofRowForDelete;
    this.dataService.whatsnewTitle = selectedRowData.title;
    this.dataService.hashKeyVal = selectedRowData.hashKey;
    this.warningMsg = "Are you sure you want to delete this record ?"
    this.dataService.selectedRowDataFirstType = selectedRowData;
    $("#confirmRejectModal").modal("show");
  }

  deleteRowOfFetchedData(indexofrowFetchedDataDelete) {
    this.dataService.indexOfRowForFetchedDataDelete = indexofrowFetchedDataDelete;
    this.warningMsg = "Are you sure you want to delete this record ?"
    $("#confirmRejectModal").modal("show");
  }

  deleteRowOfTimePeriod(indexOfSections, sectionName) {
    this.dataService.sectionsSelectedIndex = indexOfSections;
    this.warningMsg = "Are you sure you want to delete '" + sectionName + "'?"
    $("#confirmDeleteModal").modal("show");
  }
  deleteSelectedRowDataOfSection(fieldDataCancel) {
    this.dataService.languageIndex = this.dataService.sectionsSelectedIndex;
    this.dataService.pageLanguage = this.staticHomeService.selectedLang;
    if (this.dataService.fetchedDropdown.length == 1) {
      this.dataService.errorFound = "Minimum 1 record is required."
      $("#error").modal('show');
    } else {
      this.http.get(Constants.HOME_URL + Constants.CMS_URL + 'deleteCmsSectionContent?viewName=' + this.dataService.viewName + '&languageIndex=' + this.dataService.languageIndex
        + '&pageLanguage=' + this.dataService.pageLanguage + '&tableSize=' + this.dataService.fetchedDropdown.length).subscribe((data) => {
          this.dataService.successMsg = "Data deleted successfully."
          this.dataService.selectedRow = "";
          $("#deleteRowSectionOfFirstTable").modal('show');
          this.cancelOrders(fieldDataCancel);
        }, err => {
          $("#error").modal('show');
        });
    }

  }


  // deleting row data 
  deleteSelectedRowData() {

    if (this.dataService.cmsType == 'nested tabular') {
      this.dataService.languageIndex = this.dataService.indexOfSection;
    } else {
      this.dataService.languageIndex = 0;
    }
    // if (this.dataService.languageIndex == undefined) {
    //   this.dataService.languageIndex = 0;
    // }
    for (let i = 0; i < this.dataService.selectedMenuLists.listOfQuestionModel.length; i++) {
      if (this.dataService.selectedMenuLists.listOfQuestionModel[i].dependecy == false
        && this.dataService.selectedMenuLists.listOfQuestionModel[i].dependentCondition[0] != 'hasChildren') {
        this.dataService.dataIndex = this.dataService.indexOfEachRowSecond;
      }
      if ((this.dataService.selectedMenuLists.listOfQuestionModel[i].dependecy == true)) {
        this.dataService.dataIndex = this.dataService.indexOfEachRowFirst;
      }
      if (this.dataService.dataIndex == undefined && this.dataService.selectedMenuLists.listOfQuestionModel[i].dependecy == false
        && this.dataService.selectedMenuLists.listOfQuestionModel[i].dependentCondition[0] != 'hasChildren') {
        this.dataService.dataIndex = this.dataService.indexOfRowForDelete;
      }
      else if (this.dataService.dataIndex == undefined && this.dataService.selectedMenuLists.listOfQuestionModel[i].dependecy == true) {
        this.dataService.dataIndex = this.dataService.indexOfRowForFetchedDataDelete;
      }
    }
    this.dataService.isUpdate = true;
    this.dataService.pageLanguage = this.staticHomeService.selectedLang;
    if (this.dataService.selectedTabMenuUrl != undefined) {

      if ((this.dataService.fetchedDropdown.length == 1 && this.dataService.cmsType == 'nested tabular' &&
        this.dataService.selectedMenuLists.listOfCmsDataDto[0].viewContent[this.staticHomeService.selectedLang][0].data.length == 1)
        || (this.dataService.languageIndex == 0 &&
          this.dataService.selectedMenuLists.listOfCmsDataDto[0].viewContent[this.staticHomeService.selectedLang][0].data.length == 1)) {
        this.dataService.errorFound = "Minimum 1 record is required."
        $("#error").modal('show');
      } else {

        this.http.get(Constants.HOME_URL + Constants.CMS_URL + 'deleteCmsContent?viewName=' + this.dataService.viewName + '&languageIndex=' + this.dataService.languageIndex
          + '&dataIndex=' + this.dataService.dataIndex + '&pageLanguage=' + this.dataService.pageLanguage + '&tableSize=' + (
            this.dataService.cmsType == 'nested tabular' ? this.dataService.tableData.length :
              this.dataService.selectedMenuLists.listOfCmsDataDto[0].viewContent[this.staticHomeService.selectedLang][0].data.length)).subscribe((data) => {
                this.dataService.successMsg = "Record deleted successfully."
                $("#deleteRowSection").modal('show');
              }, err => {
                $("#error").modal('show');
              });
      }

    } else {
      if ((this.dataService.languageIndex == 0 &&
        this.dataService.dataOfHomePage.length == 1) ||
        (this.dataService.viewName == 'Achievement and Progress' && this.dataService.languageIndex == 0 &&
          this.dataService.dataOfHomePage.length <= 2) ||
        (this.dataService.viewName == 'Home' && this.dataService.languageIndex == 0 &&
          this.dataService.dataOfHomePage.length <= 3)) {

        if (this.dataService.viewName == 'Home') {
          this.dataService.errorFound = "Minimum 3 images are required in banner."
        } else if (this.dataService.viewName == 'Achievement and Progress') {
          this.dataService.errorFound = "Minimum 2 records are mandatory."
        } else {
          this.dataService.errorFound = "Minimum 1 record is required."
        }


        $("#error").modal('show');
      } else {
        this.http.get(Constants.HOME_URL + Constants.CMS_URL + 'deleteCmsContent?viewName=' + this.dataService.viewName + '&languageIndex=' + this.dataService.languageIndex
          + '&dataIndex=' + this.dataService.dataIndex + '&pageLanguage=' + this.dataService.pageLanguage + '&oldHashkey=' + this.dataService.hashKeyVal + '&tableSize=' + this.dataService.dataOfHomePage.length).subscribe((data) => {
            this.dataService.successMsg = "Record deleted successfully."
            $("#deleteRowSection").modal('show');
          }, err => {
            $("#error").modal('show');
          });
      }

    }

  }

  // editing row data of 2nd table
  editRowTableData(selectedRowData, indexOfRow, file) {

    this.dataService.indexOfEachRowSecond = indexOfRow;
    if (this.dataService.selectedTabMenuUrl == 'Applications and Appeals') {
      this.dataService.editClicked = true;

      for (let i = 0; i < this.dataService.selectedMenuLists.listOfQuestionModel.length; i++) {
        if (this.dataService.selectedMenuLists.listOfQuestionModel[i].label == "Upload File") {
          this.dataService.selectedMenuLists.listOfQuestionModel[i].value = undefined;
        }

      }
      for (let i = 0; i < this.dataService.selectedMenuLists.listOfQuestionModel.length; i++) {


        this.dataService.selectedMenuLists.listOfQuestionModel[i].value =
          selectedRowData[this.dataService.selectedMenuLists.listOfQuestionModel[i].columnName];
      }

    } else {


      this.dataService.whatsnewTitle = selectedRowData.title;
      this.dataService.hashKeyVal = selectedRowData.hashKey;
      // this.upload();
      for (let i = 0; i < this.dataService.selectedMenuLists.listOfQuestionModel.length; i++) {
        if (this.dataService.selectedMenuLists.listOfQuestionModel[i].label == "Upload File") {
          this.dataService.selectedMenuLists.listOfQuestionModel[i].value = undefined;
        }

      }
      for (let i = 0; i < this.dataService.selectedMenuLists.listOfQuestionModel.length; i++) {
        var checkBox = document.getElementById(this.dataService.selectedMenuLists.listOfQuestionModel[i].dependentColumn);
        let elem = this.getObjectByColumnName(this.dataService.selectedMenuLists.listOfQuestionModel[i].dependentColumn,
          this.dataService.selectedMenuLists.listOfQuestionModel)
        if (this.dataService.selectedMenuLists.listOfQuestionModel[i].label == "Title") {
          this.dataService.selectedMenuLists.listOfQuestionModel[i].value = selectedRowData.title;
          this.dataService.title = selectedRowData.title;
        }
        if (this.dataService.selectedMenuLists.listOfQuestionModel[i].label == "File Name") {
          if (checkBox != null && elem != undefined && (selectedRowData.fileTitle != '' && selectedRowData.fileTitle != undefined)) {
            checkBox.checked = true;
            elem.value = false;
            let checkBoxFields = this.dataService.selectedMenuLists.listOfQuestionModel[i].columnName;
            $("#div" + checkBoxFields).show();
          } else if (checkBox != null && elem != undefined) {
            checkBox.checked = false;
            elem.value = null;
            let checkBoxFields = this.dataService.selectedMenuLists.listOfQuestionModel[i].columnName;
            $("#div" + checkBoxFields).hide();
          }
          this.dataService.selectedMenuLists.listOfQuestionModel[i].value = selectedRowData.fileTitle;
          this.dataService.fileTitle = selectedRowData.fileTitle;
        }

        if (this.dataService.selectedMenuLists.listOfQuestionModel[i].label == "Link") {
          if (checkBox != null && elem != undefined && selectedRowData.link != '' && selectedRowData.link != undefined) {
            checkBox.checked = true;
            elem.value = false;
            let checkBoxFields = this.dataService.selectedMenuLists.listOfQuestionModel[i].columnName;
            $("#div" + checkBoxFields).show();
          } else if (checkBox != null && elem != undefined) {
            checkBox.checked = false;
            elem.value = null;
            let checkBoxFields = this.dataService.selectedMenuLists.listOfQuestionModel[i].columnName;
            $("#div" + checkBoxFields).hide();
          }
          this.dataService.selectedMenuLists.listOfQuestionModel[i].value = selectedRowData.link;
          this.dataService.url = selectedRowData.link;
        }
        if (this.dataService.selectedMenuLists.listOfQuestionModel[i].label == "Content") {
          if (checkBox != null && elem != undefined && (selectedRowData.content != '' && selectedRowData.content != undefined)) {
            checkBox.checked = true;
            elem.value = false;
            let checkBoxFields = this.dataService.selectedMenuLists.listOfQuestionModel[i].columnName;
            $("#div" + checkBoxFields).show();
          } else if (checkBox != null && elem != undefined) {
            checkBox.checked = false;
            elem.value = null;
            let checkBoxFields = this.dataService.selectedMenuLists.listOfQuestionModel[i].columnName;
            $("#div" + checkBoxFields).hide();
          }
          this.dataService.selectedMenuLists.listOfQuestionModel[i].value = selectedRowData.content;
          this.dataService.content = selectedRowData.content;
        }
        if (this.dataService.selectedMenuLists.listOfQuestionModel[i].label == "Upload File") {

          if (checkBox != null && elem != undefined && selectedRowData.fileTitle != '' && selectedRowData.fileTitle != undefined) {
            checkBox.checked = true;
            elem.value = false;
            let checkBoxFields = this.dataService.selectedMenuLists.listOfQuestionModel[i].columnName;
            $("#div" + checkBoxFields).show();
          } else if (checkBox != null && elem != undefined) {
            checkBox.checked = false;
            elem.value = null;
            let checkBoxFields = this.dataService.selectedMenuLists.listOfQuestionModel[i].columnName;
            $("#div" + checkBoxFields).hide();
          }
          this.dataService.uploadedFile = selectedRowData.fileName != undefined ?
            selectedRowData.fileName.substring(selectedRowData.fileName.lastIndexOf('/') + 1) : selectedRowData.fileName;
          if (this.dataService.uploadedFile == undefined) {
            this.dataService.uploadedFile = selectedRowData.fileName;
          }
          this.dataService.uploadedFilepath = selectedRowData.fileName;
          this.dataService.fileName = selectedRowData.fileName;
        }
        if (this.dataService.selectedMenuLists.listOfQuestionModel[i].label == "Upload Image") {
          if (checkBox != null && elem != undefined && selectedRowData.imageName != '' && selectedRowData.imageName != undefined) {
            checkBox.checked = true;
            elem.value = false;
            let checkBoxFields = this.dataService.selectedMenuLists.listOfQuestionModel[i].columnName;
            $("#div" + checkBoxFields).show();
          } else if (checkBox != null && elem != undefined) {
            checkBox.checked = false;
            elem.value = null;
            let checkBoxFields = this.dataService.selectedMenuLists.listOfQuestionModel[i].columnName;
            $("#div" + checkBoxFields).hide();
          }
          this.dataService.imageUploadedFile = selectedRowData.imageName != undefined ?
            selectedRowData.imageName.substring(selectedRowData.imageName.lastIndexOf('/') + 1) : selectedRowData.imageName;
          if (this.dataService.imageUploadedFile == undefined) {
            this.dataService.imageUploadedFile = selectedRowData.imageName;
          }
          this.dataService.imageUploadedFilepath = selectedRowData.imageName;
          this.dataService.fileName = selectedRowData.imageName;
          this.dataService.imageFileName = selectedRowData.imageName;
        }
        if (this.dataService.selectedMenuLists.listOfQuestionModel[i].label == "Upload Audio") {
          this.dataService.uploadedAudio = selectedRowData.fileName != undefined ?
            selectedRowData.fileName.substring(selectedRowData.fileName.lastIndexOf('/') + 1) : selectedRowData.fileName;
          if (this.dataService.uploadedAudio == undefined) {
            this.dataService.uploadedAudio = selectedRowData.fileName;
          }
          this.dataService.uploadedAudiopath = selectedRowData.fileName;
          this.dataService.uploadedAudioName = selectedRowData.fileName;
        }
        if (this.dataService.selectedMenuLists.listOfQuestionModel[i].label == "Priority") {
          var radioButton = document.getElementById(this.dataService.selectedMenuLists.listOfQuestionModel[i].columnName +
            '_' + (selectedRowData.isPriority ? 'Yes' : 'No'));

          if (selectedRowData.isPriority == true) {
            this.dataService.isPriority = true;
          } else {
            this.dataService.isPriority = false;
          }
          radioButton.checked = true;
        }
        if (this.dataService.selectedMenuLists.listOfQuestionModel[i].label == "Publish Date"
          && selectedRowData.publishedDate != undefined) {
          this.dataService.day = selectedRowData.publishedDate.split("-")[0];
          this.dataService.month = selectedRowData.publishedDate.split("-")[1];
          this.dataService.year = selectedRowData.publishedDate.split("-")[2];
          // this.selDate = {year: parseInt(this.dataService.year), month:  parseInt(this.dataService.month), day:  parseInt(this.dataService.day)};
          this.selDate = selectedRowData.publishedDate;
          this.dataService.publishedDate = selectedRowData.publishedDate;
        }
        if (this.dataService.selectedMenuLists.listOfQuestionModel[i].label == "Year") {
          this.dataService.selectedMenuLists.listOfQuestionModel[i].value = selectedRowData.year;
        }
        if (this.dataService.selectedMenuLists.listOfQuestionModel[i].label == "Quarter") {
          this.dataService.selectedMenuLists.listOfQuestionModel[i].value = selectedRowData.quarter;
        }
      }
    }
    $('html,body').animate({
      scrollTop: $('#selectedRowDataInField').offset().top - 250
    }, 'slow');
    return false;
  }

  previewData(validator) {
    if (this.dataService.imageUploadedFilepath == "") {
      this.dataService.selectedImageName = "";
      this.dataService.imageFileName = "";
      $(".about-us-image").css({ "display": "none" });
    } else {
      $(".about-us-image").css({ "display": "block" });
    }
    this.dataService.contentVal = document.getElementById('contentVal').value;
    for (let i = 0; i < validator.length; i++) {
      if (validator[i].label == "File Name") {
        this.dataService.aboutSchemeFileTitle = this.dataService.selectedMenuLists.listOfQuestionModel[i].value;
      }
    }
    // validation mandatory field checking
    if (this.dataService.contentVal != undefined && this.dataService.contentVal.trim() == "") {
      $('#previewPage').modal('hide');
      this.dataService.errorFound = "Content field should not be blank."
      $("#error").modal('show');
    }
    else if (this.dataService.selectedFileName == undefined && (this.dataService.aboutSchemeFileTitle != undefined && this.dataService.aboutSchemeFileTitle.trim() != "") && this.dataService.uploadedFile == "") {
      $('#previewPage').modal('hide');
      this.dataService.errorFound = "Upload File."
      $("#error").modal('show');
    }
    else if (this.dataService.selectedFileName == undefined && (this.dataService.aboutSchemeFileTitle != undefined && this.dataService.aboutSchemeFileTitle.trim() == "") && this.dataService.uploadedFile != "") {
      $('#previewPage').modal('hide');
      this.dataService.errorFound = "Enter File Name."
      $("#error").modal('show');
    }
    else if (this.dataService.selectedFileName != undefined && ((this.dataService.aboutSchemeFileTitle != undefined && this.dataService.aboutSchemeFileTitle.trim() == "") || this.dataService.aboutSchemeFileTitle == null) && this.dataService.uploadedFile == "") {
      $('#previewPage').modal('hide');
      this.dataService.errorFound = "Enter File Name."
      $("#error").modal('show');
    }
    else {
      if (this.dataService.contentVal != "") {
        $('#previewPage').modal('show');
        $("#error").modal('hide');
      }
      if (this.dataService.selectedFileName == undefined && (this.dataService.aboutSchemeFileTitle == "" || this.dataService.aboutSchemeFileTitle == null) && this.dataService.contentVal != "" && this.dataService.uploadedFile == "") {
        $("#error").modal('hide');
        $('#previewPage').modal('show');
      }
      if (this.dataService.selectedFileName == undefined && this.dataService.aboutSchemeFileTitle != "" && this.dataService.contentVal != "" && this.dataService.uploadedFile == "") {
        $("#error").modal('hide');
        $('#previewPage').modal('show');
      }
    }
  }
  selectDropdownState(selectedOption, field, index) {

    this.dataService.selectedMenuLists.listOfQuestionModel[index].value = selectedOption.value;
    this.dataService.selectedMenuLists.listOfQuestionModel[index].key = selectedOption.key;
    let dependentColumn = this.getObjectByColumnName(field.dependentColumn, this.dataService.selectedMenuLists.listOfQuestionModel)
    if (field.metaData === 'year' || field.metaData === 'quarter') {
      this.dataService.dataModel[field.metaData] = selectedOption.value;
    } else {
      this.dataService.dataModel["areaId"] = selectedOption.key;
      this.dataService.dataModel["areaName"] = selectedOption.value;
      this.dataService.dataModel["parentAreaId"] = selectedOption.parentId;
      this.dataService.dataModel["areaLevel"] = field.metaData;


      if (field.dependecy) {
        this.dataService.dataModel["parentAreaName"] = dependentColumn.value;
      }
    }

    if (!field.dependecy) {
      dependentColumn.value = null;
    }
  }


  // Key contacts starts
  selectDropdown(selectedOption, index, kcdataModel, listOfCmsDataDto) {
    // this.dataService.showNationalLevelData = true;
    this.dataService.keyContactsdetailsOfNationLevel = [];
    this.dataService.keyContactsdetailsOfStateLevel = [];
    this.dataService.keyContactsdetailsOfDistrictLevel = [];
    this.dataService.stateLevelLists = [];
    this.dataService.districtLevelLists = [];
    this.dataService.keyContactsInfoDetailsOfDistrict = [];
    this.dataService.keyContactsInfoDetailsOfState = [];
    this.dataService.selectedStateForDistrictLevel = "";
    this.dataService.indexOfSelectedStateForDistrictLevel = 0;
    this.dataService.kcName = "";
    this.dataService.kcAddress = "";
    this.dataService.kcDepartment = "";
    this.dataService.kcDesignation = "";
    this.dataService.kcPhoneNo = "";
    this.dataService.kcEmail = "";
    this.dataService.noDataFound = false;
    this.dataService.selectedRowOfStateOrDistrict = "";
    this.dataService.showAddOptionOfKc = false;
    this.dataService.showEditCallOptionOfKc = false;
    this.dataService.showEditOptionOfKc = false;
    this.dataService.searchTextsOfNational = "";
    this.dataService.searchTextsOfStateLists = "";
    this.dataService.searchTextsOfStateListsContacts = "";
    this.dataService.searchTextsOfDistrict = "";
    this.dataService.searchTextsOfDistrictContacts = "";
    $("#keyContactsNational").hide();
    $("#keyContactsState").hide();
    $("#keyContactsDistrict").hide();
    $("#contact-details-district").hide();
    $("#contact-details-state").hide();
    $("#publish-button-kc").attr("disabled", true);
    this.val = kcdataModel.filter(d => d.dependentCondition[0] == '');
    for (let k = 0; k < kcdataModel.length; k++) {
      if (kcdataModel[k].label == 'Select Level') {
        kcdataModel[k].value = selectedOption.value;
        this.dataService.selectLevelDropdownVal = kcdataModel[k].value;
        if (this.dataService.selectLevelDropdownVal == 'National Level') {
          this.val = kcdataModel.filter(d => d.dependentCondition[0] == '');
          this.dataService.showAddOptionOfKc = false;
          this.dataService.showEditCallOptionOfKc = false;
          this.dataService.showEditOptionOfKc = false;
          for (let k = 0; k < kcdataModel.length; k++) {
            if (kcdataModel[k].label == 'Select State' || kcdataModel[k].label == 'District Name'
              || kcdataModel[k].label == 'State Name' || kcdataModel[k].label == 'Name'
              || kcdataModel[k].label == 'Address' || kcdataModel[k].label == 'E-mail'
              || kcdataModel[k].label == 'Phone No. ' || kcdataModel[k].label == 'Department'
              || kcdataModel[k].label == 'Designation') {
              kcdataModel[k].value = "";
            }
          }
          $("#publish-button-kc").removeAttr("disabled");
          this.dataService.showNationalLevelData = true;
          $("#div" + this.dataService.nationalTableId).show();
          this.dataService.showStateLevelData = false;
          $("#div" + this.dataService.stateTableId).hide();
          this.dataService.showDistrictLevelData = false;
          $("#div" + this.dataService.districtTableId).hide();
          $("#keyContactsNational").show();
          $("#keyContactsState").hide();
          $("#keyContactsDistrict").hide();
          $("#contact-details-district").hide();
          $("#contact-details-state").hide();
          for (let kc = 0; kc < listOfCmsDataDto.length; kc++) {
            if (listOfCmsDataDto[kc].cmsType == "nationalcontact") {
              this.dataService.viewNameOfNational = listOfCmsDataDto[kc].viewName;
              this.dataService.keyContactsdetailsOfNationLevel = listOfCmsDataDto[kc].viewContent[this.staticHomeService.selectedLang][0].keyContacts;
              document.getElementById('table-national').style.display = 'block';
              $('html,body').animate({
                scrollTop: $('#selectedRowKCDataInTablenational').offset().top - 250
              }, 'slow');
              return false;
            }
          }
        } else if (this.dataService.selectLevelDropdownVal == 'State Level') {
          this.dataService.showAddOptionOfKc = true;
          this.dataService.showEditCallOptionOfKc = false;
          this.dataService.showEditOptionOfKc = false;
          this.val = kcdataModel.filter(d => d.dependentCondition[0] == '' || d.dependentCondition[0] == 'isState');
          for (let k = 0; k < kcdataModel.length; k++) {
            if (kcdataModel[k].label == 'Select State' || kcdataModel[k].label == 'District Name'
              || kcdataModel[k].label == 'State Name' || kcdataModel[k].label == 'Name'
              || kcdataModel[k].label == 'Address' || kcdataModel[k].label == 'E-mail'
              || kcdataModel[k].label == 'Phone No. ' || kcdataModel[k].label == 'Department'
              || kcdataModel[k].label == 'Designation') {
              kcdataModel[k].value = "";
              $("#" + kcdataModel[k].columnName).removeAttr("disabled");
              $("#" + kcdataModel[k].columnName).css("background", "#f0f0f0");
            }
          }
          this.dataService.showNationalLevelData = false;
          $("#div" + this.dataService.nationalTableId).hide();
          this.dataService.showStateLevelData = true;
          $("#div" + this.dataService.stateTableId).show();
          this.dataService.showDistrictLevelData = false;
          $("#div" + this.dataService.districtTableId).hide();
          $("#keyContactsNational").hide();
          $("#keyContactsState").show();
          $("#keyContactsDistrict").hide();
          $("#contact-details-district").hide();
          $("#publish-button-kc").attr("disabled", true);
          for (let kc = 0; kc < listOfCmsDataDto.length; kc++) {
            if (listOfCmsDataDto[kc].cmsType == "statecontact") {
              this.dataService.viewNameOfState = listOfCmsDataDto[kc].viewName;
              this.apiService.getResourceQuestionList().subscribe(res => {
                this.dataService.questions = res;
                this.dataService.allMenuContents = Object.keys(this.dataService.questions).map(key => ({
                  type: key,
                  keyVal: this.dataService.questions[key]
                }));
                this.dataService.allMenuContents.forEach(element => {
                  if (element.type == this.dataService.selectedTabMenuUrl) {
                    for (let i = 0; i < kcdataModel.length; i++) {
                      for (let el = 0; el <= element.keyVal[0].listOfCmsDataDto.length; el++) {
                        if (kcdataModel[i].dependentCondition[0] == 'isState' && this.dataService.selectLevelDropdownVal == "State Level"
                          && element.keyVal[0].listOfCmsDataDto[el].cmsType == "statecontact") {
                          this.dataService.stateLevelLists = element.keyVal[0].listOfCmsDataDto[el].viewContent[this.staticHomeService.selectedLang];
                        }
                      }
                    }
                  }
                });
              })
              // this.dataService.keyContactsdetailsOfStateLevel = listOfCmsDataDto[kc].viewContent[this.staticHomeService.selectedLang][0].keyContacts;
              $('html,body').animate({
                scrollTop: $('#selectedRowKCDataInTableState').offset().top + 350
              }, 'slow');
              return false;
            }
          }
        }
        else if (this.dataService.selectLevelDropdownVal == 'District Level') {
          this.val = kcdataModel.filter(d => d.dependentCondition[0] == '' || d.dependentCondition[0] == 'isDistrict');
          this.dataService.showAddOptionOfKc = false;
          this.dataService.showEditCallOptionOfKc = false;
          this.dataService.showEditOptionOfKc = false;
          for (let k = 0; k < kcdataModel.length; k++) {
            if (kcdataModel[k].label == 'Select State' || kcdataModel[k].label == 'District Name'
              || kcdataModel[k].label == 'State Name' || kcdataModel[k].label == 'Name'
              || kcdataModel[k].label == 'Address' || kcdataModel[k].label == 'E-mail'
              || kcdataModel[k].label == 'Phone No. ' || kcdataModel[k].label == 'Department'
              || kcdataModel[k].label == 'Designation') {
              kcdataModel[k].value = "";
            }
          }
          $("#contact-details-state").hide();
          this.dataService.keyContactsInfoDetailsOfState = [];
          this.dataService.districtLevelLists = [];
          this.dataService.showNationalLevelData = false;
          $("#div" + this.dataService.nationalTableId).hide();
          this.dataService.showStateLevelData = false;
          $("#div" + this.dataService.stateTableId).hide();
          this.dataService.showDistrictLevelData = false;
          $("#div" + this.dataService.districtTableId).hide();
          $("#keyContactsNational").hide();
          $("#keyContactsState").hide();
          $("#contact-details-state").hide();
          $("#keyContactsDistrict").show();
          for (let kc = 0; kc < listOfCmsDataDto.length; kc++) {
            if (listOfCmsDataDto[kc].cmsType == "districtcontact") {
              this.dataService.viewNameOfDistrict = listOfCmsDataDto[kc].viewName;
              // this.dataService.keyContactsdetailsOfDistrictLevel = listOfCmsDataDto[kc].viewContent[this.staticHomeService.selectedLang][0].keyContactRoles;
              this.apiService.getResourceQuestionList().subscribe(res => {
                this.dataService.questions = res;
                this.dataService.allMenuContents = Object.keys(this.dataService.questions).map(key => ({
                  type: key,
                  keyVal: this.dataService.questions[key]
                }));
                this.dataService.allMenuContents.forEach(element => {
                  if (element.type == this.dataService.selectedTabMenuUrl) {
                    for (let i = 0; i < kcdataModel.length; i++) {
                      for (let el = 0; el <= element.keyVal[0].listOfCmsDataDto.length; el++) {
                        if (kcdataModel[i].dependentCondition[0] == 'isDistrict' && this.dataService.selectLevelDropdownVal == "District Level"
                          && element.keyVal[0].listOfCmsDataDto[el].cmsType == "districtcontact") {
                          this.dataService.stateLevelLists = element.keyVal[0].listOfCmsDataDto[el].viewContent[this.staticHomeService.selectedLang];
                        }
                      }
                    }
                  }
                });
              })
              return false;
            }
          }
        }
      }
    }
  }

  // selecting state name for district level
  selectStateItem(selectedStateOption, index, stateDataModel, listOfCmsDataDtoD) {
    this.dataService.selectedStateForDistrictLevel = selectedStateOption.section;
    this.dataService.indexOfSelectedStateForDistrictLevel = index;
    this.dataService.showAddOptionOfKc = true;
    this.dataService.showEditCallOptionOfKc = false;
    this.dataService.showEditOptionOfKc = false;
    this.dataService.selectedRowOfStateOrDistrict = "";
    this.dataService.showDistrictLevelData = true;
    this.dataService.noDataFound = false;
    this.dataService.selectedRowOfStateOrDistrict = "";
    this.dataService.keyContactsInfoDetailsOfDistrict = [];
    $("#div" + this.dataService.districtTableId).show();
    $("#contact-details-state").show();
    $("#publish-button-kc").attr("disabled", true);
    for (let k = 0; k < stateDataModel.length; k++) {
      if (stateDataModel[k].label == 'Select State') {
        stateDataModel[k].value = selectedStateOption.section;
        this.dataService.districtLevelSelectedStateValue = selectedStateOption.section;
      }
      if (stateDataModel[k].label == 'District Name') {
        stateDataModel[k].value = "";
        $("#" + stateDataModel[k].columnName).removeAttr("disabled");
        $("#" + stateDataModel[k].columnName).css("background", "#fff");
      }
    }
    this.dataService.districtLevelLists = this.dataService.stateLevelLists.filter(d => d.section == selectedStateOption.section)[0].keyContactRoles;
    if (Object.keys(this.dataService.districtLevelLists[0]).length === 0) {
      this.dataService.noDataFound = true;
      document.getElementById('keyContactsState').style.display = "none";
      document.getElementById('keyContactsDistrict').style.display = "none";
      this.dataService.districtLevelLists = [];
      this.dataService.keyContactsInfoDetailsOfDistrict = [];
      this.dataService.showDistrictLevelData = false;
      this.dataService.selectedRowOfStateOrDistrict = "";
    }
    $('html,body').animate({
      scrollTop: $('#selectedRowKCDataInTableDistrict').offset().top + 350
    }, 'slow');
    return false;
  }




  // adding new state and district names of KC
  addNewStateORDistrictName(stateOrDistrictField) {
    this.dataService.isUpdate = false;
    this.dataService.pageLanguage = this.staticHomeService.selectedLang;
    for (let s = 0; s < stateOrDistrictField.length; s++) {
      if (this.dataService.selectLevelDropdownVal == 'State Level') {
        this.dataService.viewName = this.dataService.viewNameOfState;
        this.dataService.languageIndex = this.dataService.stateLevelLists.length;
      }
      else if (this.dataService.selectLevelDropdownVal == 'District Level') {
        this.dataService.viewName = this.dataService.viewNameOfDistrict;
        this.dataService.languageIndex = this.dataService.districtLevelLists.length;
      }

      if ((this.dataService.selectLevelDropdownVal == 'State Level' && stateOrDistrictField[s].label == "State Name" && stateOrDistrictField[s].value.trim() == "")
        || (this.dataService.selectLevelDropdownVal == 'District Level' && stateOrDistrictField[s].label == "District Name" && (this.dataService.selectedStateForDistrictLevel == "" || stateOrDistrictField[s].value.trim() == ""))) {
        this.dataService.errorFound = "Please " + stateOrDistrictField[s].section + ".";
        $("#error").modal('show');
        return false;
      } else if ((this.dataService.selectLevelDropdownVal == 'State Level' && stateOrDistrictField[s].label == "State Name" && stateOrDistrictField[s].dependentCondition[0] == "isState")
        || (this.dataService.selectLevelDropdownVal == 'District Level' && this.dataService.selectedStateForDistrictLevel != "" && stateOrDistrictField[s].label == "District Name" && stateOrDistrictField[s].dependentCondition[0] == 'isDistrict')) {
        this.dataService.newStateOrDistrictName = stateOrDistrictField[s].value;
        this.addNamesOfStateOrDistrict(stateOrDistrictField);
        return false;
      }

    }
  }

  addNamesOfStateOrDistrict(nameModelField) {
    this.http.get(Constants.HOME_URL + Constants.CMS_URL + 'sectionAddCmsContent?viewName=' + this.dataService.viewName + '&languageIndex=' + this.dataService.languageIndex
      + '&section=' + this.dataService.newStateOrDistrictName.trim() + '&pageLanguage=' + this.dataService.pageLanguage + '&isUpdate=' + this.dataService.isUpdate
      + '&stateIndex=' + this.dataService.indexOfSelectedStateForDistrictLevel).subscribe((data) => {
        // $("#" + sectionField.columnName).attr("disabled", true);
        this.dataService.showEditOptionOfKc = true;
        this.dataService.showEditCallOptionOfKc = false;
        // for (let i = 0; i < nameModelField.length; i++) {
        //   if (nameModelField[i].label == "State Name"
        //     || nameModelField[i].label == "District Name") {
        //     $("#" + nameModelField[i].columnName).attr("disabled", true);
        //     $("#" + nameModelField[i].columnName).css("background", "#ddd");
        //   }
        // }
        this.dataService.selectedRowAdded = '';
        this.dataService.successMsg = this.dataService.newStateOrDistrictName + " added successfully. "
        $("#addingSuccessfullyOfKc").modal('show');
      }, err => {
        this.dataService.errorFound = "Some error found";
        $("#error").modal('show');
      });
  }

  // editing new name of state and district level
  editNewStateOrDistrictName(editField) {
    this.dataService.isUpdate = true;
    this.dataService.pageLanguage = this.staticHomeService.selectedLang;
    if (this.dataService.selectLevelDropdownVal == 'State Level') {
      this.dataService.viewName = this.dataService.viewNameOfState;
      this.dataService.languageIndex = this.dataService.selectedStateNameIndex;
    }
    else if (this.dataService.selectLevelDropdownVal == 'District Level') {
      this.dataService.viewName = this.dataService.viewNameOfDistrict;
      this.dataService.languageIndex = this.dataService.selectedDistrictNameIndex;
    }

    this.dataService.showAddOptionOfKc = false;
    for (let e = 0; e < editField.length; e++) {
      if ((this.dataService.selectLevelDropdownVal == 'State Level' && editField[e].label == "State Name" && editField[e].value.trim() == "")
        || (this.dataService.selectLevelDropdownVal == 'District Level' && this.dataService.selectedStateForDistrictLevel != "" && editField[e].label == "District Name" && editField[e].value.trim() == "")) {
        this.dataService.errorFound = "Please " + editField[e].section + ".";
        $("#error").modal('show');
        return false;
      } else if ((this.dataService.selectLevelDropdownVal == 'State Level' && editField[e].label == "State Name" && editField[e].dependentCondition[0] == "isState")
        || (this.dataService.selectLevelDropdownVal == 'District Level' && this.dataService.selectedStateForDistrictLevel != "" && editField[e].label == "District Name" && editField[e].dependentCondition[0] == 'isDistrict')) {
        this.dataService.newStateOrDistrictName = editField[e].value;
        this.addNamesOfStateOrDistrict(editField);
        return false;
      }
    }
  }



  //  editing 1st table data of State Level
  editRowOfState(selectedRowDataOfState, si, kcStateData) {
    this.dataService.selectedStateNameIndex = si;
    this.dataService.noDataFound = false;
    this.dataService.searchTextsOfStateLists = "";
    this.dataService.searchTextsOfStateListsContacts = "";
    for (let s = 0; s < kcStateData.length; s++) {
      if (kcStateData[s].label == 'State Name') {
        kcStateData[s].value = selectedRowDataOfState;
        $("#" + kcStateData[s].columnName).attr("disabled", true);
        $("#" + kcStateData[s].columnName).css("background", "#ddd");
      }
      for (let i = 0; i < kcStateData.length; i++) {
        if (kcStateData[i].label == "Name" || kcStateData[i].label == "Address"
          || kcStateData[i].label == "E-mail" || kcStateData[i].label == "Phone No. "
          || kcStateData[i].label == "Department" || kcStateData[i].label == "Designation") {
          kcStateData[i].value = "";
        }
      }
      $("#publish-button-kc").removeAttr("disabled");
      this.dataService.showAddOptionOfKc = false;
      this.dataService.showEditOptionOfKc = true;
      $(".selectedTableInfo").show();
      this.dataService.selectedRowOfStateOrDistrict = selectedRowDataOfState;
      $("#div" + this.dataService.nationalTableId).hide();
      $("#div" + this.dataService.districtTableId).hide();
      $("#contact-details-state").show();
      this.dataService.keyContactsInfoDetailsOfState = this.dataService.stateLevelLists.filter(d => d.section == selectedRowDataOfState)[0].keyContacts;
      if (Object.keys(this.dataService.keyContactsInfoDetailsOfState[0]).length === 0 && kcStateData[s].label == 'State Name') {
        this.dataService.noDataFound = true;
        // return false;
      }
    }

    $('html,body').animate({
      scrollTop: $('#selectedRowKCDataInTableState').offset().top - 250
    }, 'slow');
    return false;
  }

  // editing state name Of 1st table of state level
  editNameOfStateOrDistrict(editFielddata) {
    for (let e = 0; e < editFielddata.length; e++) {
      if (editFielddata[e].label == "State Name" || editFielddata[e].label == "District Name") {
        $("#" + editFielddata[e].columnName).removeAttr("disabled");
        $("#" + editFielddata[e].columnName).css("background", "#fff");
        $("#publish-button-kc").attr("disabled", true);
        this.dataService.showAddOptionOfKc = false;
        this.dataService.showEditOptionOfKc = false;
        this.dataService.showEditCallOptionOfKc = true;
        $("#addBtnDiv").hide();
      }
    }
  }

  // editing 1st table data of district level
  editRowOfDistrict(selectedRowDataOfDistrict, di, kcDistrcitData) {
    this.dataService.selectedDistrictNameIndex = di;
    this.dataService.searchTextsOfDistrict = "";
    this.dataService.searchTextsOfDistrictContacts = "";
    for (let s = 0; s < kcDistrcitData.length; s++) {
      if (kcDistrcitData[s].label == 'District Name') {
        kcDistrcitData[s].value = selectedRowDataOfDistrict;
        $("#" + kcDistrcitData[s].columnName).attr("disabled", true);
        $("#" + kcDistrcitData[s].columnName).css("background", "#ddd");
      }
      $("#publish-button-kc").removeAttr("disabled");
      this.dataService.showAddOptionOfKc = false;
      this.dataService.showEditOptionOfKc = true;
      $(".selectedTableInfo").show();
      this.dataService.selectedRowOfStateOrDistrict = selectedRowDataOfDistrict;
      $("#div" + this.dataService.nationalTableId).hide();
      $("#div" + this.dataService.stateTableId).hide();
      this.dataService.keyContactsInfoDetailsOfDistrict = this.dataService.districtLevelLists.filter(d => d.section == selectedRowDataOfDistrict)[0].keyContacts;
      if (Object.keys(this.dataService.keyContactsInfoDetailsOfDistrict[0]).length === 0) {
        this.dataService.noDataFound = true;
        // return false;
      }
    }
    $('html,body').animate({
      scrollTop: $('#selectedRowDataInTableOfDistrict').offset().top - 250
    }, 'slow');
    return false;
  }

  // editing each row data of national level table
  editRowOfContactDetails(rowData, ni, contactsModel) {
    this.dataService.selectedContactIndex = ni;
    this.dataService.searchTextsOfStateLists = "";
    this.dataService.searchTextsOfStateListsContacts = "";
    this.dataService.searchTextsOfDistrict = "";
    this.dataService.searchTextsOfDistrictContacts = "";
    if (this.dataService.selectLevelDropdownVal == 'National Level') {
      this.dataService.showAddOptionOfKc = false;
      this.dataService.showEditCallOptionOfKc = false;
      this.dataService.showEditOptionOfKc = false;
    } else {
      this.dataService.showEditOptionOfKc = false;
      this.dataService.showAddOptionOfKc = true;
      this.dataService.showEditCallOptionOfKc = false;
    }


    $("#publish-button-kc").removeAttr("disabled");
    for (let n = 0; n < contactsModel.length; n++) {
      if (contactsModel[n].label == 'Name') {
        contactsModel[n].value = rowData.name;
        this.dataService.kcName = rowData.name;
      }
      if (contactsModel[n].label == 'Designation') {
        contactsModel[n].value = rowData.designation;
        this.dataService.kcDesignation = rowData.designation;
      }
      if (contactsModel[n].label == 'Address') {
        contactsModel[n].value = rowData.address;
        this.dataService.kcAddress = rowData.address;
      }
      if (contactsModel[n].label == 'E-mail') {
        contactsModel[n].value = rowData.email;
        this.dataService.kcEmail = rowData.email;
      }
      if (contactsModel[n].label == 'Phone No. ') {
        contactsModel[n].value = rowData.phoneNumber;
        this.dataService.kcPhoneNo = rowData.phoneNumber;
      }
      if (contactsModel[n].label == 'Department') {
        contactsModel[n].value = rowData.act;
        this.dataService.kcDepartment = rowData.act;
      }
    }
    $('html,body').animate({
      scrollTop: $('#selectedRowDataInFieldOfKC').offset().top - 350
    }, 'slow');
    return false;
  }

  preventNegative(e) {
    if (!((e.keyCode > 95 && e.keyCode < 106)
      || (e.keyCode > 47 && e.keyCode < 58)
      || e.keyCode == 8)) {
      return false;
    }
  }
  // reset data of kc
  resetData(resetDataModel) {
    this.dataService.showEditOptionOfKc = false;

    if (this.dataService.selectLevelDropdownVal != 'National Level') {
      this.dataService.showAddOptionOfKc = true;
    }

    this.dataService.showEditCallOptionOfKc = false;
    this.dataService.selectedContactIndex = undefined;
    for (let i = 0; i < resetDataModel.length; i++) {
      if (resetDataModel[i].label == "Name" || resetDataModel[i].label == "Address"
        || resetDataModel[i].label == "E-mail" || resetDataModel[i].label == "Phone No. "
        || resetDataModel[i].label == "Department" || resetDataModel[i].label == "Designation"
        || resetDataModel[i].label == 'Select State') {
        resetDataModel[i].value = "";
        this.dataService.noDataFound = false;
      }
      if (resetDataModel[i].controlType == "table" && resetDataModel[i].dependentCondition[0] == 'isDistrict') {
        $("#div" + resetDataModel[i].columnName).hide();
      }
      if (resetDataModel[i].label == "State Name" || resetDataModel[i].label == "District Name") {
        $("#" + resetDataModel[i].columnName).removeAttr("disabled");
        $("#" + resetDataModel[i].columnName).css("background", "#fff");
        $("#publish-button-kc").attr("disabled", true);
        resetDataModel[i].value = "";
        this.dataService.showEditOptionOfKc = false;
        $(".selectedTableInfo").hide();
        this.dataService.selectedRowOfStateOrDistrict = "";
        this.dataService.keyContactsInfoDetailsOfState = [];
        this.dataService.keyContactsInfoDetailsOfDistrict = [];
        $("#keyContactsDistrict").hide();
        $("#contact-details-district").hide();
        $("#contact-details-state").hide();

        this.dataService.districtLevelLists = [];
      } else if (this.dataService.selectLevelDropdownVal == 'National Level') {
        $("#publish-button-kc").removeAttr("disabled");
      }
    }
  }

  // deleting row data of 2nd contact lists
  deleteRowOfContactDetails(selectedRowDataOfKC, indexofRowForDeleteOfKC) {
    this.dataService.indexOfRowForDeleteOfKC = indexofRowForDeleteOfKC;
    this.warningMsg = "Are you sure you want to delete this data ?"
    $("#confirmRejectModalofKC").modal("show");
  }

  deleteSelectedRowDataOfKC(dModel) {
    for (let i = 0; i < dModel.length; i++) {
      if (this.dataService.selectLevelDropdownVal == 'National Level') {
        this.dataService.viewName = this.dataService.viewNameOfNational;
        this.dataService.languageIndex = 0;
        this.dataService.dataIndex = this.dataService.indexOfRowForDeleteOfKC;
        this.dataService.tableSize = this.dataService.keyContactsdetailsOfNationLevel.length;
      }
      else if (this.dataService.selectLevelDropdownVal == 'State Level') {
        this.dataService.viewName = this.dataService.viewNameOfState;
        this.dataService.languageIndex = this.dataService.selectedStateNameIndex;
        this.dataService.dataIndex = this.dataService.indexOfRowForDeleteOfKC;
        this.dataService.tableSize = this.dataService.keyContactsInfoDetailsOfState.length;
      }
      else if (this.dataService.selectLevelDropdownVal == 'District Level') {
        this.dataService.viewName = this.dataService.viewNameOfDistrict;
        this.dataService.languageIndex = this.dataService.selectedDistrictNameIndex;
        this.dataService.dataIndex = this.dataService.indexOfRowForDeleteOfKC;
        this.dataService.tableSize = this.dataService.keyContactsInfoDetailsOfDistrict.length;
      }
    }
    if (this.dataService.tableSize == 1) {
      this.dataService.errorFound = "Minimum 1 record is required."
      $("#error").modal('show');
    } else {
      this.dataService.pageLanguage = this.staticHomeService.selectedLang;
      this.http.get(Constants.HOME_URL + Constants.CMS_URL + 'deleteCmsContentKeyContacts' + '?viewName='
        + this.dataService.viewName + '&languageIndex=' + this.dataService.languageIndex + '&dataIndex='
        + this.dataService.dataIndex + '&pageLanguage=' + this.dataService.pageLanguage + '&stateIndex=' + this.dataService.indexOfSelectedStateForDistrictLevel + '&tableSize=' + this.dataService.tableSize).subscribe(response => {
          this.dataService.successMsg = "Record deleted successfully."
          $("#deleteRowOfKCdata").modal('show');
        },
          error => {
            this.dataService.errorFound = "Some error found."
            $("#error").modal('show');
          });
    }

  }
  // end

  // deleting state name with its contacts list
  deleteRowOfStateOrDistrictName(selectedRowDataOfStateName, indexofRowForDeleteOfStateName) {
    this.dataService.indexOfRowForDeleteOfStateOrDistrictName = indexofRowForDeleteOfStateName;
    this.warningMsg = "Are you sure you want to delete this data ?"
    $("#confirmRejectModalofStateNameOrDistrictName").modal("show");
  }

  deleteSelectedRowDataOfStateNameOrDistrictName(dsnModel) {
    if (this.dataService.selectLevelDropdownVal == 'State Level') {
      this.dataService.viewName = this.dataService.viewNameOfState;
      this.dataService.languageIndex = this.dataService.indexOfRowForDeleteOfStateOrDistrictName;
      this.dataService.tableSize = this.dataService.stateLevelLists.length;
    }
    else if (this.dataService.selectLevelDropdownVal == 'District Level') {
      this.dataService.viewName = this.dataService.viewNameOfDistrict;
      this.dataService.languageIndex = this.dataService.indexOfRowForDeleteOfStateOrDistrictName;
      this.dataService.tableSize = this.dataService.districtLevelLists.length;
    }
    if (this.dataService.tableSize == 1) {
      this.dataService.errorFound = "Minimum 1 record is required."
      $("#error").modal('show');
    } else {
      this.dataService.selectedRowOfStateOrDistrict = "";
      this.dataService.pageLanguage = this.staticHomeService.selectedLang;
      this.http.get(Constants.HOME_URL + Constants.CMS_URL + 'deleteCmsSectionContentKeyContacts?viewName=' + this.dataService.viewName + '&languageIndex=' + this.dataService.languageIndex
        + '&pageLanguage=' + this.dataService.pageLanguage + '&tableSize=' + this.dataService.tableSize + '&stateIndex=' + this.dataService.indexOfSelectedStateForDistrictLevel).subscribe((data) => {
          this.dataService.successMsg = "Data deleted successfully."
          $("#deleteRowOfKCdata").modal('show');
          this.resetData(dsnModel);
        }, err => {
          $("#error").modal('show');
        });
    }
  }

  refreshDataOfKC(refreshDeletedData) {
    this.refreshPublishedKCData(refreshDeletedData);
  }

  // validation with publishing kc data
  submitKeyContactsDetails(keyContactsDetails) {
    var emailPattern = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    for (let a = 0; a < keyContactsDetails.length; a++) {
      if (keyContactsDetails[a].value == null) {
        keyContactsDetails[a].value = "";
      }
      if (this.dataService.selectedTabMenuUrl != 'aboutus'
        && this.dataService.selectedTabMenuUrl != undefined) {
        if (keyContactsDetails[a].validation != null && this.dataService.selectLevelDropdownVal == "") {
          if (keyContactsDetails[a].validation.mandatory == true) {
            if (keyContactsDetails[a].value.trim() == "") {
              this.dataService.errorFound = keyContactsDetails[a].section + ".";
              $("#error").modal('show');
              return false;
            }
          }
        }
        if (this.dataService.selectLevelDropdownVal == 'National Level') {
          this.val = keyContactsDetails.filter(d => d.dependentCondition[0] == '');
          for (let index = 0; index < this.val.length; index++) {
            if (this.val[index].validation != null) {
              if (this.val[index].validation.mandatory == true) {
                if (this.val[index].value.trim() == "") {
                  this.dataService.errorFound = this.val[index].section + ".";
                  $("#error").modal('show');
                  return false;
                }
              } else if (this.val[index].validation.mandatory == undefined) {
                if (this.val[index].label == "E-mail") {
                  if (this.dataService.kcEmail != "") {
                    if (!emailPattern.test(this.dataService.kcEmail)) {
                      this.dataService.errorFound = "Please provide a valid email address";
                      $("#error").modal('show');
                      return false;
                    }
                  }
                }
                if (this.val[index].label == "Phone No. ") {
                  if (this.dataService.kcPhoneNo != "" && this.dataService.kcPhoneNo.length < 10) {
                    this.dataService.errorFound = "Please enter 10 digit mobile number.";
                    $("#error").modal('show');
                    return false;
                  }
                }
              }
            }
            else {
              this.publishDataOfKeyContacts();
              return false;
            }
          }
        }

        if (this.dataService.selectLevelDropdownVal == 'State Level') {
          this.val = keyContactsDetails.filter(d => d.dependentCondition[0] == '' || d.dependentCondition[0] == 'isState');
          for (let index = 0; index < this.val.length; index++) {
            if (this.val[index].validation != null) {
              if (this.val[index].validation.mandatory == true) {
                if (this.val[index].value.trim() == "") {
                  this.dataService.errorFound = this.val[index].section + ".";
                  $("#error").modal('show');
                  return false;
                }
              } else if (this.val[index].validation.mandatory == undefined) {
                if (this.val[index].label == "E-mail") {
                  if (this.dataService.kcEmail != "") {
                    if (!emailPattern.test(this.dataService.kcEmail)) {
                      this.dataService.errorFound = "Please provide a valid email address";
                      $("#error").modal('show');
                      return false;
                    }
                  }
                }
                if (this.val[index].label == "Phone No. ") {
                  if (this.dataService.kcPhoneNo != "" && this.dataService.kcPhoneNo.length < 10) {
                    this.dataService.errorFound = "Please enter 10 digit mobile number.";
                    $("#error").modal('show');
                    return false;
                  }
                }
              }
            }
            else {
              this.publishDataOfKeyContacts();
              return false;
            }
          }
        }
        if (this.dataService.selectLevelDropdownVal == 'District Level') {
          this.val = keyContactsDetails.filter(d => d.dependentCondition[0] == '' || d.dependentCondition[0] == 'isDistrict');
          for (let index = 0; index < this.val.length; index++) {
            if (this.val[index].validation != null) {
              if (this.val[index].validation.mandatory == true) {
                if (this.val[index].value.trim() == "") {
                  this.dataService.errorFound = this.val[index].section + ".";
                  $("#error").modal('show');
                  return false;
                }
              }
              else if (this.val[index].validation.mandatory == undefined) {
                if (this.val[index].label == "E-mail") {
                  if (this.dataService.kcEmail != "") {
                    if (!emailPattern.test(this.dataService.kcEmail)) {
                      this.dataService.errorFound = "Please provide a valid email address";
                      $("#error").modal('show');
                      return false;
                    }
                  }
                }
                if (this.val[index].label == "Phone No. ") {
                  if (this.dataService.kcPhoneNo != "" && this.dataService.kcPhoneNo.length < 10) {
                    this.dataService.errorFound = "Please enter 10 digit mobile number.";
                    $("#error").modal('show');
                    return false;
                  }
                }
              }
            }
            else {
              this.publishDataOfKeyContacts();
              return false;
            }
          }
        }
      }
    }
  }

  publishDataOfKeyContacts() {
    this.dataService.keyContactModelDistrict = [];
    if (this.dataService.selectLevelDropdownVal == 'National Level') {
      this.dataService.viewName = this.dataService.viewNameOfNational;
      this.dataService.languageIndex = 0;
      this.dataService.dataIndex = this.dataService.selectedContactIndex;
      this.dataService.isUpdate = true;
      if (this.dataService.dataIndex == undefined) {
        this.dataService.dataIndex = this.dataService.keyContactsdetailsOfNationLevel.length;
        this.dataService.isUpdate = false;
      }
    }
    else if (this.dataService.selectLevelDropdownVal == 'State Level') {
      this.dataService.viewName = this.dataService.viewNameOfState;
      this.dataService.languageIndex = this.dataService.selectedStateNameIndex;
      this.dataService.isUpdate = true;
      if (this.dataService.languageIndex == undefined) {
        this.dataService.languageIndex = this.dataService.stateLevelLists.length;
        this.dataService.isUpdate = false;
      }
      this.dataService.dataIndex = this.dataService.selectedContactIndex;
      if (Object.keys(this.dataService.keyContactsInfoDetailsOfState[0]).length === 0) {
        this.dataService.dataIndex = 0;
        this.dataService.isUpdate = true;
      }
      if (this.dataService.dataIndex == undefined) {
        this.dataService.dataIndex = this.dataService.keyContactsInfoDetailsOfState.length;
        this.dataService.isUpdate = false;
      }
    }
    else if (this.dataService.selectLevelDropdownVal == 'District Level') {
      this.dataService.viewName = this.dataService.viewNameOfDistrict;
      this.dataService.languageIndex = this.dataService.selectedDistrictNameIndex;
      this.dataService.isUpdate = true;
      if (this.dataService.languageIndex == undefined) {
        this.dataService.languageIndex = this.dataService.districtLevelLists.length;
        this.dataService.isUpdate = false;
      }
      this.dataService.dataIndex = this.dataService.selectedContactIndex;
      if (Object.keys(this.dataService.keyContactsInfoDetailsOfDistrict[0]).length === 0) {
        this.dataService.dataIndex = 0;
        this.dataService.isUpdate = true;
      }
      if (this.dataService.dataIndex == undefined) {
        this.dataService.dataIndex = this.dataService.keyContactsInfoDetailsOfDistrict.length;
        this.dataService.isUpdate = false;
      }
    }

    this.dataService.keyContactModel = {
      'name': this.dataService.kcName != undefined ? this.dataService.kcName.trim() : null,
      'address': this.dataService.kcAddress != undefined ? this.dataService.kcAddress.trim() : null,
      'email': this.dataService.kcEmail != undefined ? this.dataService.kcEmail.trim() : null,
      'phoneNumber': this.dataService.kcPhoneNo != undefined ? this.dataService.kcPhoneNo.trim() : null,
      'act': this.dataService.kcDepartment != undefined ? this.dataService.kcDepartment.trim() : null,
      'designation': this.dataService.kcDesignation != undefined ? this.dataService.kcDesignation.trim() : null,
    }
    this.dataService.keyContactModelDistrict.push(this.dataService.keyContactModel);
    this.dataService.keyContactRoleModel = {
      'section': this.dataService.selectedStateForDistrictLevel != undefined ? this.dataService.selectedStateForDistrictLevel.trim() : null,
      'keyContacts': this.dataService.keyContactModelDistrict
    }

    this.dataService.pageLanguage = this.staticHomeService.selectedLang;
    if (this.dataService.selectLevelDropdownVal == "National Level" || this.dataService.selectLevelDropdownVal == "State Level") {
      this.http.post(Constants.HOME_URL + Constants.CMS_URL + 'updateAddCmsContentKeyContacts' + '?viewName='
        + this.dataService.viewName + '&languageIndex=' + this.dataService.languageIndex + '&dataIndex='
        + this.dataService.dataIndex + '&pageLanguage=' + this.dataService.pageLanguage + '&isUpdate='
        + this.dataService.isUpdate + '&stateIndex=' + this.dataService.indexOfSelectedStateForDistrictLevel
        + '&stateName=' + this.dataService.selectedStateForDistrictLevel, this.dataService.keyContactModel).subscribe(response => {
          this.dataService.successMsg = "Published data successfully.";
          $("#publishedSuccessfullyOfKc").modal('show');
        },
          error => {
            this.dataService.errorFound = "Some error found."
            $("#error").modal('show');
          });
    } else if (this.dataService.selectLevelDropdownVal == "District Level") {
      this.http.post(Constants.HOME_URL + Constants.CMS_URL + 'updateAddCmsContentKeyContactsDistrict' + '?viewName='
        + this.dataService.viewName + '&languageIndex=' + this.dataService.languageIndex + '&dataIndex='
        + this.dataService.dataIndex + '&pageLanguage=' + this.dataService.pageLanguage + '&isUpdate='
        + this.dataService.isUpdate + '&stateIndex=' + this.dataService.indexOfSelectedStateForDistrictLevel
        + '&stateName=' + this.dataService.selectedStateForDistrictLevel, this.dataService.keyContactRoleModel).subscribe(response => {
          this.dataService.successMsg = "Published data successfully.";
          $("#publishedSuccessfullyOfKc").modal('show');
        },
          error => {
            this.dataService.errorFound = "Some error found."
            $("#error").modal('show');
          });
    }
  }

  // refresh added state data

  refreshAddedKCData(refreshAddeddName) {
    this.apiService.getResourceQuestionList().subscribe(res => {
      this.dataService.questions = res;
      this.dataService.selectedContactIndex = undefined;
      this.dataService.allMenuContents = Object.keys(this.dataService.questions).map(key => ({
        type: key,
        keyVal: this.dataService.questions[key]
      }));
      this.dataService.allMenuContents.forEach(element => {
        if (element.type == this.dataService.selectedTabMenuUrl) {
          for (let i = 0; i < refreshAddeddName.length; i++) {
            for (let el = 0; el <= element.keyVal[0].listOfCmsDataDto.length; el++) {
              if (refreshAddeddName[i].dependentCondition[0] == 'isState' && this.dataService.selectLevelDropdownVal == "State Level"
                && element.keyVal[0].listOfCmsDataDto[el].cmsType == "statecontact") {
                this.dataService.selectedRowOfStateOrDistrict = "";
                this.dataService.stateLevelLists = element.keyVal[0].listOfCmsDataDto[el].viewContent[this.staticHomeService.selectedLang];
                this.dataService.noDataFound = false;
                this.dataService.keyContactsInfoDetailsOfState = [];
                $("#contact-details-state").hide();
                this.dataService.kcStateName = "";
                for (let i = 0; i < refreshAddeddName.length; i++) {
                  if (refreshAddeddName[i].label == "State Name") {
                    refreshAddeddName[i].value = "";
                  }
                }
              }
              else if (this.dataService.selectLevelDropdownVal == "District Level" && refreshAddeddName[i].dependentCondition[0] == "isDistrict"
                && element.keyVal[0].listOfCmsDataDto[el].cmsType == "districtcontact") {
                this.dataService.selectedRowOfStateOrDistrict = this.dataService.kcDistrictName;
                this.dataService.stateLevelLists = element.keyVal[0].listOfCmsDataDto[el].viewContent[this.staticHomeService.selectedLang];
                this.dataService.districtLevelLists = this.dataService.stateLevelLists.filter(d => d.section == this.dataService.selectedStateForDistrictLevel)[0].keyContactRoles
                if (Object.keys(this.dataService.districtLevelLists[0]).length === 0) {
                  this.dataService.noDataFound = true;
                } else {
                  $("#contact-details-district").hide();
                  $("#keyContactsDistrict").show();
                  this.dataService.noDataFound = false;
                  for (let i = 0; i < refreshAddeddName.length; i++) {
                    if (refreshAddeddName[i].label == "District Name") {
                      refreshAddeddName[i].value = "";
                      this.dataService.selectedRowOfStateOrDistrict = "";
                    }
                    // if(refreshAddeddName[i].controlType == 'table' && refreshAddeddName[i].dependentCondition[0] == "isDistrict"){
                    //   $("#div" + refreshAddeddName[i].columnName).show();
                    //   $("#contact-details-district").hide();
                    //   $("#keyContactsDistrict").show();
                    // }
                  }
                }
              }
            }
          }
        }
      });
    })
    this.dataService.showAddOptionOfKc = true;
    this.dataService.showEditCallOptionOfKc = false;
    this.dataService.showEditOptionOfKc = false;
    for (let i = 0; i < refreshAddeddName.length; i++) {
      if (refreshAddeddName[i].label == "Name" || refreshAddeddName[i].label == "Address"
        || refreshAddeddName[i].label == "E-mail" || refreshAddeddName[i].label == "Phone No. "
        || refreshAddeddName[i].label == "Department" || refreshAddeddName[i].label == "Designation") {
        refreshAddeddName[i].value = "";
      }
    }
  }




  // refresh call on submission of KC Data 
  refreshPublishedKCData(refreshKcData) {
    this.dataService.selectedContactIndex = undefined;
    this.apiService.getResourceQuestionList().subscribe(res => {
      this.dataService.questions = res;
      this.dataService.allMenuContents = Object.keys(this.dataService.questions).map(key => ({
        type: key,
        keyVal: this.dataService.questions[key]
      }));
      this.dataService.allMenuContents.forEach(element => {
        if (element.type == this.dataService.selectedTabMenuUrl) {
          for (let i = 0; i < refreshKcData.length; i++) {
            for (let el = 0; el <= element.keyVal[0].listOfCmsDataDto.length; el++) {
              if (refreshKcData[i].dependentCondition[0] == '' && this.dataService.languageIndex == 0
                && this.dataService.selectLevelDropdownVal == "National Level"
                && element.keyVal[0].listOfCmsDataDto[el].cmsType == "nationalcontact") {
                this.dataService.showAddOptionOfKc = false;
                this.dataService.showEditCallOptionOfKc = false;
                this.dataService.showEditOptionOfKc = false;
                this.dataService.keyContactsdetailsOfNationLevel = element.keyVal[0].listOfCmsDataDto[el].viewContent[this.staticHomeService.selectedLang][0].keyContacts;
                $('html,body').animate({
                  scrollTop: $('#selectedRowKCDataInTablenational').offset().top - 250
                }, 'slow');
                return false;
              }
              else if (refreshKcData[i].dependentCondition[0] == 'isState' && this.dataService.selectLevelDropdownVal == "State Level"
                && element.keyVal[0].listOfCmsDataDto[el].cmsType == "statecontact") {
                if (refreshKcData[i].label == "State Name") {
                  this.dataService.selectedRowOfStateOrDistrict = refreshKcData[i].value;
                }
                this.dataService.showAddOptionOfKc = true;
                this.dataService.showEditCallOptionOfKc = false;
                this.dataService.showEditOptionOfKc = false;
                this.dataService.stateLevelLists = element.keyVal[0].listOfCmsDataDto[el].viewContent[this.staticHomeService.selectedLang];
                this.dataService.noDataFound = false;
                this.dataService.keyContactsInfoDetailsOfState = this.dataService.stateLevelLists.filter(d => d.section == this.dataService.selectedRowOfStateOrDistrict)[0].keyContacts;
                if (this.dataService.languageIndex > 0) {
                  if (Object.keys(this.dataService.keyContactsInfoDetailsOfState[0]).length === 0) {
                    $(".selectedTableInfo").hide();
                  }
                }
                if (this.dataService.languageIndex >= 0) {
                  this.dataService.keyContactsInfoDetailsOfState = this.dataService.stateLevelLists.filter(d => d.section == this.dataService.selectedRowOfStateOrDistrict)[0].keyContacts;
                  // $('#hideTableIfNoData').show();
                  if (Object.keys(this.dataService.keyContactsInfoDetailsOfState[0]).length > 0) {
                    $(".selectedTableInfo").show();
                    $("#contact-details-state").hide();
                    $('html,body').animate({
                      scrollTop: $('#selectedRowDataInTableState').offset().top - 250
                    }, 'slow');

                  } else {
                    if (refreshKcData[i].label == "State Name") {
                      refreshKcData[i].value = '';
                    }
                    $('html,body').animate({
                      scrollTop: $('#selectedRowDataInTableState').offset().top - 250
                    }, 'slow');
                    return false;
                  }
                }
              }
              else if (this.dataService.selectLevelDropdownVal == "District Level" && refreshKcData[i].dependentCondition[0] == "isDistrict"
                && element.keyVal[0].listOfCmsDataDto[el].cmsType == "districtcontact") {
                this.dataService.showAddOptionOfKc = true;
                this.dataService.showEditCallOptionOfKc = false;
                this.dataService.showEditOptionOfKc = false;
                // this.dataService.selectedRowOfStateOrDistrict = this.dataService.kcDistrictName;
                this.dataService.stateLevelLists = element.keyVal[0].listOfCmsDataDto[el].viewContent[this.staticHomeService.selectedLang];
                this.dataService.districtLevelLists = this.dataService.stateLevelLists.filter(d => d.section == this.dataService.selectedStateForDistrictLevel)[0].keyContactRoles
                this.dataService.noDataFound = false;
                if (this.dataService.languageIndex > 0) {
                  if (Object.keys(this.dataService.keyContactsInfoDetailsOfDistrict[0]).length === 0) {
                    $(".selectedTableInfo").hide();
                  }
                }
                if (this.dataService.languageIndex >= 0) {
                  this.dataService.keyContactsInfoDetailsOfDistrict = this.dataService.districtLevelLists.filter(d => d.section == this.dataService.selectedRowOfStateOrDistrict)[0].keyContacts;
                  if (Object.keys(this.dataService.keyContactsInfoDetailsOfDistrict[0]).length > 0) {
                    $(".selectedTableInfo").show();
                    // this.dataService.selectedRowOfStateOrDistrict = "";
                    $('html,body').animate({
                      scrollTop: $('#selectedRowDataInTableOfDistrict').offset().top - 250
                    }, 'slow');


                  } else {
                    this.dataService.showDistrictLevelData = true;
                    this.dataService.selectedRowOfStateOrDistrict = "";
                    $('html,body').animate({
                      scrollTop: $('#selectedRowDataInTableOfDistrict').offset().top - 250
                    }, 'slow');
                    return false;
                  }
                }
              }
            }
          }
        }
      });
    })

    for (let i = 0; i < refreshKcData.length; i++) {
      if (refreshKcData[i].label == "Name" || refreshKcData[i].label == "Address"
        || refreshKcData[i].label == "E-mail" || refreshKcData[i].label == "Phone No. "
        || refreshKcData[i].label == "Department" || refreshKcData[i].label == "Designation") {
        refreshKcData[i].value = "";
      }
    }
  }

  refreshPageDescriptionData() {
    this.apiService.getResourceQuestionListByViewName(this.dataService.viewName).subscribe(res => {
      this.dataService.questions = res;
      this.dataService.eachMenuContents = Object.keys(this.dataService.questions).map(key => ({
        type: key,
        keyVal: this.dataService.questions[key]
      }));
      this.updateItem(this.dataService.eachMenuContents[0]);
      this.dataService.eachMenuContents.forEach(element => {
        if (element.type == this.dataService.selectedTabMenuUrl) {
          this.dataService.shortDescription = element.keyVal[0].listOfCmsDataDto[0].viewType;
          for (let i = 0; i < this.dataService.selectedMenuLists.listOfQuestionModel.length; i++) {
            if (element.keyVal[0].listOfQuestionModel[i].label == "Page Description") {
              element.keyVal[0].listOfQuestionModel[i].value = this.dataService.shortDescription;
            }
          }
        } else if (this.dataService.selectedTabMenuUrl == undefined) {

          this.dataService.shortDescription = element.keyVal[0].listOfCmsDataDto[0].viewType;
          for (let i = 0; i < this.dataService.selectedMenuLists.listOfQuestionModel.length; i++) {

            if (element.keyVal[0].listOfQuestionModel[i].label == "Page Description") {
              element.keyVal[0].listOfQuestionModel[i].value = this.dataService.shortDescription;
            }
          }
        }
      });
    })
  }



  publishDataOfPreviewpage() {
    this.publishData();
  }

  pushFileToStorage(file: File, fileColumn: any): Observable<HttpEvent<{}>> {
    const formdata: FormData = new FormData();
    formdata.append('fileUpload', file);
    formdata.append('columnName', fileColumn);
    formdata.append('viewName', 'Applications and Appeals')
    const req = new HttpRequest("POST", Constants.HOME_URL + Constants.CMS_URL + 'uploadAllFiles', formdata, {
      responseType: "text"
    });
    return this.http.request(req);
  }


  saveRTIData() {
    if (this.dataService.languageIndex == undefined) {
      this.dataService.languageIndex = 0;
    }
    this.dataService.pageLanguage = this.staticHomeService.selectedLang;
    if (Object.keys(this.dataService.selectedMenuLists.listOfCmsDataDto[0].viewContent[this.staticHomeService.selectedLang][0].data[0]).length === 0) {
      this.dataService.dataIndex = 0;
      this.dataService.isUpdate = false;
    } else {
      this.dataService.dataIndex = this.dataService.indexOfEachRowSecond;
      this.dataService.isUpdate = true;
    }
    if (this.dataService.dataIndex == undefined) {
      this.dataService.dataIndex = this.dataService.selectedMenuLists.listOfCmsDataDto[0].viewContent[this.staticHomeService.selectedLang][0].data.length;
      this.dataService.isUpdate = false;
    }
    this.http.post(Constants.HOME_URL + Constants.CMS_URL + 'updateRTICmsContent' + '?viewName='
      + this.dataService.viewName + '&languageIndex=' + this.dataService.languageIndex + '&dataIndex=' + this.dataService.dataIndex
      + '&pageLanguage=' + 'english' + '&isUpdate='
      + this.dataService.isUpdate, this.dataService.submitModel).subscribe(response => {
        this.dataService.successMsg = "Published data successfully."
        $("#pop").modal('show');
      },
        error => {
          this.dataService.errorFound = "Some error found."
          $("#error").modal('show');
        });
  }

  // publishing data
  async submitForm(field) {

    for (let a = 0; a < field.length; a++) {
      if (field[a].value == null) {
        field[a].value = "";
      }
      if (this.dataService.selectedTabMenuUrl != 'aboutus') {
        if (this.dataService.isPriority == true && field[a].label == 'Priority') {
          field[a].value = true;
          this.dataService.isPriority == true;
        } else if (this.dataService.isPriority == false && field[a].label == 'Priority') {
          field[a].value = false;
          this.dataService.isPriority == false;
        }
        if (this.dataService.uploadedFile != "" && (this.dataService.selectedFileName == '' || this.dataService.selectedFileName == undefined) && field[a].label == "Upload File") {
          field[a].value = this.dataService.uploadedFile;
          this.dataService.file.push(this.dataService.uploadedFile);
        } else if (this.dataService.uploadedFile != "" && (this.dataService.selectedFileName != undefined || this.dataService.selectedFileName != '') && field[a].label == "Upload File") {
          field[a].value = this.dataService.selectedFileName;
          this.dataService.file.push(this.dataService.uploadedFile)
        }
        if (this.dataService.imageUploadedFile != "" && (this.dataService.selectedImageName == '' || this.dataService.selectedImageName == undefined) && field[a].label == "Upload Image") {
          field[a].value = this.dataService.imageUploadedFile;
          this.dataService.file.push(this.dataService.imageUploadedFile)
        } else if (this.dataService.imageUploadedFile != "" && (this.dataService.selectedImageName != undefined || this.dataService.selectedImageName != '') && field[a].label == "Upload Image") {
          field[a].value = this.dataService.selectedImageName;
          this.dataService.file.push(this.dataService.imageUploadedFile)
        }

        if (this.dataService.uploadedAudio != "" && (this.dataService.selectedAudioName == '' || this.dataService.selectedAudioName == undefined) && field[a].label == "Upload Audio") {
          field[a].value = this.dataService.uploadedAudio;
          this.dataService.file.push(this.dataService.uploadedAudio)
        } else if (this.dataService.uploadedAudio != "" && (this.dataService.selectedAudioName != undefined || this.dataService.selectedAudioName != '') && field[a].label == "Upload Audio") {
          field[a].value = this.dataService.selectedAudioName;
          this.dataService.file.push(this.dataService.uploadedAudio)
        }
        if (this.dataService.cmsType == 'news' && (field[a].label == "Content" || field[a].label == "Upload Image"
          || field[a].label == "Upload File" || field[a].label == "File Name" || field[a].label == "Link")) {
          let checkBox = document.getElementById(field[a].dependentColumn);
          let fieldBlock = field[a].columnName;
          let fieldAttr = document.getElementById(field[a].columnName);
          if (checkBox.checked == false) {
            $("#div" + fieldBlock).hide();
          } else {
            $("#div" + fieldBlock).show();
          }
        }
        if ((field[a].validation != null && field[a].dependentCondition[0] == "") || (field[a].validation != null && field[a].dependentCondition[0] == "isChecked" && this.getObjectByColumnName(field[a].dependentColumn, this.dataService.selectedMenuLists.listOfQuestionModel).value == false)) {
          if (field[a].validation.mandatory == true) {
            if ((field[a].controlType == 'textbox' || field[a].controlType == 'dropdown') && field[a].value.trim() == "" && field[a].dependentCondition[0] == "") {
              this.dataService.errorFound = 'Please ' + field[a].section + ".";
              $("#error").modal('show');
              return false;
            }
            else if (field[a].controlType == "calendar" && field[a].label == 'Publish Date' && field[a].value == "") {
              this.dataService.errorFound = 'Please ' + field[a].section + ".";
              $("#error").modal('show');
              return false;
            }
          } else if (this.dataService.cmsType == 'news' && (field[a].label == "Content" || field[a].label == "Upload Image"
            || field[a].label == "Upload File" || field[a].label == "File Name" || field[a].label == "Link")) {
            let checkBox = document.getElementById(field[a].dependentColumn);
            let fieldBlock = field[a].columnName;
            if (checkBox.checked == false) {
              $("#div" + fieldBlock).hide();
            } else {
              if (field[a].value.trim() == "") {
                this.dataService.errorFound = 'Please ' + field[a].section + ".";
                $("#error").modal('show');
                return false;
              }
            }
          }
        } else if (field[a].validation == null) {

          //RTI submit starts here
          if (this.dataService.selectedTabMenuUrl == 'Applications and Appeals') {
            this.dataService.rtiUploadedFiles = [];

            let eachQs = this.dataService.selectedMenuLists.listOfQuestionModel;
            let qsVals = Object.keys(eachQs);

            //1 is page desription
            for (let i = 1; i < qsVals.length; i++) {
              this.dataService.submitModel[eachQs[i].columnName] = eachQs[i].value;
            }

            let keys = Object.keys(this.dataService.selectedFilesToUpload);
            // keys.forEach( async key => {
            for (let index = 0; index < keys.length; index++) {
              this.dataService.rtiUploadedFiles = await this.pushFileToStorage(this.dataService.selectedFilesToUpload[keys[index]],
                keys[index]).toPromise();
              let obj = JSON.parse(this.dataService.rtiUploadedFiles.body);
              let columnKey = Object.keys(obj)[0];
              let val = obj[columnKey];
              // this.dataService.submitModel[columnKey + '_fileName'] = val;
              this.dataService.submitModel[columnKey] = val;
              // delete  this.dataService.submitModel[columnKey]; // delete fakepath value
            }
            //  });

            //put blank in other size columns

            this.saveRTIData();

            //RTI submit ends here
          } else {
            this.publishData();
          }
          return false;
        }
      }
    }
  }


  publishData() {
    this.dataService.languageIndex = 0;

    if (this.dataService.cmsType == 'nested tabular' && this.dataService.selectedRow == "") {
      this.dataService.languageIndex = this.dataService.fetchedDropdown.length;
    }

    if (this.dataService.uploadedFile != "" && (this.dataService.selectedFileName == "" || this.dataService.selectedFileName == undefined)) {
      if (this.dataService.file.indexOf(this.dataService.uploadedFile) < 0)
        this.dataService.file.push(this.dataService.uploadedFile);
    }
    if (this.dataService.imageUploadedFile != "" && (this.dataService.selectedImageName == "" || this.dataService.selectedImageName == undefined)) {
      if (this.dataService.file.indexOf(this.dataService.imageUploadedFile) < 0)
        this.dataService.file.push(this.dataService.imageUploadedFile);
    }
    if (this.dataService.selectedTabMenuUrl == 'audio-gallery' && this.dataService.uploadedAudio != "" && (this.dataService.selectedAudioName == "" || this.dataService.selectedAudioName == undefined)) {
      if (this.dataService.file.indexOf(this.dataService.uploadedAudio) < 0)
        this.dataService.file.push(this.dataService.uploadedAudio);
    }

    this.dataService.languageIndex = this.dataService.indexOfSection;
    if (this.dataService.languageIndex == undefined) {
      this.dataService.languageIndex = 0;
    }
    this.dataService.pageLanguage = this.staticHomeService.selectedLang;
    // this.dataService.isUpdate = true;
    for (let i = 0; i < this.dataService.selectedMenuLists.listOfQuestionModel.length; i++) {
      if (this.dataService.cmsType != 'nested tabular' &&
        this.dataService.selectedMenuLists.listOfQuestionModel[i].controlType == 'table' &&
        this.dataService.selectedMenuLists.listOfQuestionModel[i].dependecy == false
        && this.dataService.selectedMenuLists.listOfQuestionModel[i].dependentCondition[0] != 'hasChildren') {

        if (Object.keys(this.dataService.selectedMenuLists.listOfCmsDataDto[0].viewContent[this.staticHomeService.selectedLang][0].data[0]).length === 0) {
          this.dataService.dataIndex = 0;
          this.dataService.isUpdate = false;
        } else {
          this.dataService.dataIndex = this.dataService.indexOfEachRowSecond;
          this.dataService.isUpdate = true;
        }
        // this.dataService.isNew = true;
      }
      if (this.dataService.cmsType == 'nested tabular' &&
        this.dataService.selectedMenuLists.listOfQuestionModel[i].controlType == 'table' &&
        this.dataService.selectedMenuLists.listOfQuestionModel[i].dependecy == true) {
        this.dataService.dataIndex = this.dataService.indexOfEachRowFirst;
        this.dataService.isUpdate = true;
        // this.dataService.isNew = true;
      }

      if (this.dataService.dataIndex == undefined && this.dataService.cmsType != 'nested tabular' &&
        this.dataService.selectedMenuLists.listOfQuestionModel[i].controlType == 'table' &&
        this.dataService.selectedMenuLists.listOfQuestionModel[i].dependecy == false
        && this.dataService.selectedMenuLists.listOfQuestionModel[i].dependentCondition[0] != 'hasChildren') {

        this.dataService.dataIndex = this.dataService.selectedTabMenuUrl != undefined ?
          this.dataService.selectedMenuLists.listOfCmsDataDto[0].viewContent[this.staticHomeService.selectedLang][0].data.length : this.dataService.dataOfHomePage.length;
        this.dataService.isUpdate = false;
      }
      else if (this.dataService.dataIndex == undefined && this.dataService.cmsType == 'nested tabular' &&
        this.dataService.selectedMenuLists.listOfQuestionModel[i].controlType == 'table' &&
        this.dataService.selectedMenuLists.listOfQuestionModel[i].dependecy == true) {
        this.dataService.dataIndex = this.dataService.tableData.length;
        this.dataService.isUpdate = false;
      }
      if (this.dataService.cmsType == 'nested tabular') {
        if (Object.keys(this.dataService.tableData[0]).length === 0) {
          this.dataService.dataIndex = 0;
          this.dataService.isUpdate = true;
          // this.dataService.isNew = true;
        }
        else if (Object.keys(this.dataService.tableData[0]).length > 0) {
          this.dataService.isUpdate = false;
        }
      }
      if (this.dataService.cmsType == 'aboutus') {
        this.dataService.isUpdate = false;
      }
    }

    if (this.dataService.selectedTabMenuUrl != 'Applications and Appeals') {
      this.upload().then(e => {
        let filepath = e.body == '{}' ? "" : JSON.parse(e.body);
        let finalFilePath;
        let finalImagePath;
        let finalAudioPath;
        if (filepath == "") {
          if (this.dataService.cmsType != 'aboutus' && this.dataService.selectedTabMenuUrl != 'photo-gallery'
            && this.dataService.selectedTabMenuUrl != 'audio-gallery' && this.dataService.selectedTabMenu.title != 'Banners'
            && this.dataService.selectedTabMenuUrl != 'Exposure Visits') {
            finalFilePath = this.dataService.selectedFileName;
            if (this.dataService.selectedFileName == undefined) {
              finalImagePath = "";
            }
            if (this.dataService.selectedImageName == undefined) {
              finalFilePath = "";
            }
            if (this.dataService.selectedAudioName == undefined) {
              finalAudioPath = "";
            }
            if (this.dataService.uploadedFile != "" && (this.dataService.selectedFileName == "" || this.dataService.selectedFileName == undefined)) {
              finalFilePath = this.dataService.uploadedFilepath;
            }
          } else {
            if (this.dataService.selectedImageName == "" || this.dataService.selectedImageName == undefined) {
              this.dataService.imageUploadedFilepath = this.dataService.imageFileName;
            } else {
              this.dataService.imageUploadedFilepath = this.dataService.selectedImageName;
            }
            finalFilePath = this.dataService.uploadedFilepath;
            finalImagePath = this.dataService.imageUploadedFilepath;
            finalAudioPath = this.dataService.uploadedAudiopath;
            if (this.dataService.imageUploadedFilepath == "") {
              finalImagePath = "";
            }
            if (this.dataService.uploadedFilepath == undefined) {
              finalFilePath = "";
            }
            if (this.dataService.uploadedAudiopath == undefined) {
              finalAudioPath = "";
            }
          }

        } else {
          if (this.dataService.selectedTabMenuUrl != 'aboutus' &&
            this.dataService.selectedTabMenu.title != 'Banners' &&
            this.dataService.selectedTabMenu.title != this.dataService.whatsnew) {
            finalFilePath = filepath.pdf;
            finalImagePath = filepath.image;
            finalAudioPath = filepath.audio;
            if (this.dataService.imageUploadedFilepath == "") {
              finalImagePath = "";
            }
            if (this.dataService.uploadedFilepath == undefined) {
              finalFilePath = "";
            }
            if (this.dataService.uploadedAudiopath == undefined) {
              finalAudioPath = "";
            }
          } else {
            if (this.dataService.selectedImageName == "" || this.dataService.selectedImageName == undefined) {
              this.dataService.imageUploadedFilepath = this.dataService.imageFileName;
            } else {
              this.dataService.imageUploadedFilepath = filepath.image;
            }
            if (this.dataService.selectedFileName == undefined && this.dataService.selectedImageName != "" && this.dataService.selectedImageName != undefined) {
              finalFilePath = this.dataService.uploadedFilepath;
              // finalImagePath = filepath + this.dataService.imageUploadedFilepath;
              finalImagePath = this.dataService.imageUploadedFilepath;
              finalAudioPath = this.dataService.uploadedAudiopath;
            }
            else if (this.dataService.selectedFileName != undefined && (this.dataService.selectedImageName == "" || this.dataService.selectedImageName == undefined)) {
              finalFilePath = filepath.pdf;
              finalImagePath = this.dataService.imageUploadedFilepath;
              finalAudioPath = this.dataService.uploadedAudiopath;
            }

            else {
              finalFilePath = filepath.pdf;
              finalImagePath = filepath.image;
              finalAudioPath = filepath.audio;
              if (this.dataService.imageUploadedFilepath == "") {
                finalImagePath = "";
              }
              if (this.dataService.uploadedFilepath == undefined) {
                finalFilePath = "";
              }
              if (this.dataService.uploadedAudiopath == "") {
                finalAudioPath = "";
              }
            }
          }
        }
        if (this.dataService.selectedTabMenuUrl != 'aboutus'
          && this.dataService.selectedTabMenu.title != 'Banners') {

          this.dataService.dataModel.imageName = finalImagePath,
            this.dataService.dataModel.content = this.dataService.content != undefined ? this.dataService.content.trim() : '',
            this.dataService.dataModel.title = this.dataService.title != undefined ? this.dataService.title.trim() : '',
            this.dataService.dataModel.link = this.dataService.url != undefined ? this.dataService.url.trim() : '',
            this.dataService.dataModel.fileName = this.dataService.selectedTabMenuUrl == 'audio-gallery' ? finalAudioPath : finalFilePath,
            this.dataService.dataModel.size = this.dataService.fileSize,
            this.dataService.dataModel.isLive = true,
            this.dataService.dataModel.isNew = this.dataService.isNew,
            this.dataService.dataModel.publishedDate = this.dataService.publishedDate,
            this.dataService.dataModel.order = 0,
            this.dataService.dataModel.flag = true,
            this.dataService.dataModel.isPriority = this.dataService.isPriority,
            this.dataService.dataModel.oldTitle = this.dataService.whatsnewTitle,
            this.dataService.dataModel.fileTitle = this.dataService.fileTitle != undefined ? this.dataService.fileTitle.trim() : '',
            this.dataService.dataModel.hashKey = this.dataService.hashKeyVal != undefined ? this.dataService.hashKeyVal : null

        } else {
          this.dataService.dataModel.imageName = finalImagePath;
          this.dataService.dataModel.content = this.dataService.contentVal != undefined ? this.dataService.contentVal.trim() : null,
            this.dataService.dataModel.title = this.dataService.aboutSchemeFileTitle != undefined ? this.dataService.aboutSchemeFileTitle.trim() : null,
            this.dataService.dataModel.url = '',
            this.dataService.dataModel.createdDate = '',
            this.dataService.dataModel.caption = '',
            this.dataService.dataModel.fileName = finalFilePath,
            this.dataService.dataModel.size = this.dataService.fileSize,
            this.dataService.dataModel.isLive = true,
            this.dataService.dataModel.isNew = this.dataService.isNew,
            this.dataService.dataModel.publishedDate = '',
            this.dataService.dataModel.order = 0,
            this.dataService.dataModel.flag = true
        }

        this.http.post(Constants.HOME_URL + Constants.CMS_URL + 'updateAddCmsContent' + '?viewName='
          + this.dataService.viewName + '&languageIndex=' + this.dataService.languageIndex + '&dataIndex='
          + this.dataService.dataIndex + '&pageLanguage=' + this.dataService.pageLanguage + '&isUpdate='
          + this.dataService.isUpdate + '&tableSize=' + (this.dataService.selectedTabMenuUrl == undefined ? this.dataService.dataOfHomePage.length :
            this.dataService.cmsType == 'nested tabular' ? this.dataService.tableData.length :
              this.dataService.selectedMenuLists.listOfCmsDataDto[0].viewContent[this.staticHomeService.selectedLang][0].data.length),
          this.dataService.dataModel).subscribe(response => {
            if (this.dataService.cmsType != 'aboutus') {
              this.dataService.successMsg = "Published data successfully."
              $("#pop").modal('show');
            } else {
              this.dataService.successMsg = "Published data successfully."
              $("#withPreviewSubmission").modal('show');
            }
          },
            error => {
              this.dataService.errorFound = "Some error found."
              $("#error").modal('show');
            });
      })
    }
  }

  upload(): Promise<any> {

    let fileObject: any = {};
    fileObject.viewName = this.dataService.viewName;
    fileObject.section = this.dataService.selectedSectionName;
    return this.uploadFile(this.dataService.file, fileObject).toPromise();
  }
  uploadFile(file: File[], fileObject: any): Observable<HttpEvent<{}>> {

    var formdata: FormData = new FormData();
    let url = '';
    if (this.dataService.selectedTabMenuUrl != 'Applications and Appeals') {
      for (let file of this.dataService.file) {
        formdata.append('fileUpload', file);
      }
      url = 'uploadFile';

    }


    formdata.append("viewName", fileObject.viewName);
    formdata.append("section", fileObject.section);



    var headers = new Headers();
    const req = new HttpRequest("POST", Constants.HOME_URL + Constants.CMS_URL + url, formdata, {
      responseType: "text"
    });
    return this.http.request(req);

  }

  showCharRemaining(field) {

    if (field.label == 'Timeperiod' || field.label == 'Document Type'
      || field.label == 'Album Name' || field.label == 'State Name' || field.label == 'District Name') {
      this.dataService.charLeft = true;
    }

    $("#remainingC" + field.columnName).show();
    $("#remainingC" + field.columnName).html("Remaining characters : " + (field.validation.maxLength - field.value.length));
  }

  hideCharRemaining(field) {
    this.dataService.charLeft = false;
    $("#remainingC" + field.columnName).hide();
  }

  showImage(url: string){
    this.dataService.clickledImagePrview = url;
    $("#previewImageModal").modal("show");
  }


}
