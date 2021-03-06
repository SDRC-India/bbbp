import { Injectable } from '@angular/core';

@Injectable()
export class DataService {

  menus: any[];
  questions: any;
  landingPagedata: any;
  landingPageLeftBlockData: any = [];
  landingPageRightBlockData: any;
  radioOptions: any;
  levelOptions: any;
  description: any;
  selectedTabMenu: any;
  selectedTabMenuUrl: string;
  changedMenuList: any[];
  allMenuContents: any = {};
  eachMenuContents: any = {};
  cmslandingPageData : any = {};
  allCmsPageDescription: any = {};
  selectedMenuLists: any = [];
  selectedMenuListTableData: any = [];
  cmsTableData: any = [];
  fethedTableData: any = [];
  fetchedDropdown: any = [];
  dataOfHomePage: any = [];
  fetchedDropdownDetails: any = [];
  selectedSectionName: any;
  selectionFields: any;
  tableData: any = [];
  shortDescription: any;
  removeColumnList: any = [];  
  selectedRow: any;
  newSectionName: any;
  uploadedFile: any; 
  uploadedFilepath: any;
  imageUploadedFile: any;
  imageUploadedFilepath: any;
  selectedFileName: any;
  selectedAudioName: any;
  selectedImageName: any;
  uploadedAudio: any;
  uploadedAudiopath: any;
  uploadedAudioName: any;
  publishDate: any;
  day: any;
  month: any;
  year: any;
  errorOnSubmit: string = "";
  indexOfEachRowFirst: number;
  indexOfEachRowSecond: number;
  indexOfRowForDelete: number;
  indexOfRowForFetchedDataDelete: number;
  indexOfSection: number;
  sectionsSelectedIndex: number;
  a: number = 1;
  q: number = 1;
  m: number = 1;
  k: number = 1;
  s: number = 1;
  sc: number = 1;
  d: number = 1;
  dc: number = 1;
  nl: number = 1;
  selectedRowDataFirstType: any = [];
  noDataFound: boolean = false;
  withChildren: boolean = true;
  noChildren: boolean =  true;
  dataModel: any = {};
  deletedRowObject: any = {};
  newSectionsAddedObj : any = {};
  selectedSectionForDetetion: any = {};
  publishEditData: any = {};
  editedSectionIndex: number;
  ifEditingDescription: boolean = true;
  showAddOption: Boolean = true;
  showEditOption: Boolean = false;
  showEditCallOption: Boolean = false;
  selectedRowAdded: string = "";
  // cms model 
  // id: 0;
  imageName: string;
	content: string;
	title: string;
  url: string;
  sectionName: string;
	createdDate: string;
	caption: string;
  fileName: string;
  imageFileName: string;
	size: string;
	isLive: Boolean;
	isNew: Boolean;
	publishedDate: string;
	order: number;
  flag: Boolean;
  viewName: string;
  languageIndex: number = 0;
  dataIndex: number;
  pageLanguage: string;
  isUpdate: Boolean;
  // file: File;
  pageDescription: any;
  aboutSchemeFileTitle: any;
  titleForPreview: any;
  headingTitle: any;
  contentVal: any;
  validation: any = {};
  showList: boolean = false;
  blankErrorMessage: any;
  textBlankErrorMessage: any;
  whatsnew:string = "What's New";
  menuKeys: any;
  showContenType: Boolean = true;
  showFileType: Boolean = true;
  showImageType: Boolean = true;
  shoeUrlType: Boolean = true;
  showIfChecked: Boolean = false;
  radioSel:any;
  radioSelected:string;
  fileSize: any;
  file: any = [];
  errorFound: any;
  acceptPdfType: any;
  acceptAudioType: any;
  acceptImageType: any;
  isPriority: Boolean;
  whatsnewTitle: any;
  successMsg: any;
  blankTitleField; any;
  blankFileField: any;
  blankDateField: any;
  blankContentField: any;
  blankUrlField: any;
  checkBox: any[] = [];
  hashKeyVal: any;
  ifInputNotBlank: Boolean = true;
  errorFilemsg: string = "";
  errorImagePng: string = "";
  errorImageJpeg: string = "";
  errorImagejpg: string = "";
  errorImageMsg: string = "";
  errorAudioMsg : string = "";
  fileTitle: any;
  cmsType: string;
  tableHeaderData: string;
  headerRemoveList: string [];
  charLeft: Boolean = false;
  newNewsValidity : string;
  // for Key contacts
  keyContactsdetailsSecondTable: any[] = [];
  keyContactsdetailsOfNationLevel: any[] = [];
  keyContactsdetailsOfStateLevel: any[] = [];
  keyContactsdetailsOfDistrictLevel: any[] = [];
  showNationalLevelData: Boolean = false;
  showStateLevelData: Boolean = false;
  showDistrictLevelData: Boolean = false;
  selectLevelDropdownVal: string;
  keycontactStateData:any;
  keycontactStateDetails: any[] = [];
  stateSections: any = {};
  keycontactDistrictData: any;
  keycontactDistrictSection:  any = {};
  keycontactDistrictDetails: any[] = [];
  keycontactDistrictDetail: any[] = [];
  stateLevelLists: any[] = [];
  districtLevelLists: any[] = [];
  nationalTableId: string;
  stateTableId: string;
  districtTableId: string;
  districtLevelSelectedStateValue: string;
  keyContactsInfoDetailsOfState: any[] = [];
  keyContactsInfoDetailsOfDistrict: any[] =[];
  showAddOptionOfKc: Boolean = true;
  showEditOptionOfKc: Boolean = false;
  showEditCallOptionOfKc: Boolean = false;
  selectedRowOfStateOrDistrict: any;
  kcName: string;
  kcDesignation: string;
  kcDistrictName: string;
  kcStateName: string;
  kcAddress: any;
  kcEmail: any;
  kcPhoneNo : string;
  kcDepartment : string;
  viewNameOfNational: string;
  viewNameOfState: string;
  viewNameOfDistrict: string;
  keyContactModel: any = {};
  keyContactRoleModel: any = {};
  keyContactModelDistrict: any[] = [];
  selectedContactIndex : number;
  selectedStateNameIndex: number;
  selectedDistrictNameIndex: number;
  selectedStateForDistrictLevel: string;
  indexOfSelectedStateForDistrictLevel : number;
  indexOfRowForDeleteOfKC : number;
  indexOfRowForDeleteOfStateOrDistrictName: number;
  tableSize: number;
  newStateOrDistrictName: any;
  searchTexts: any;
  searchTextsOfDependentTable: any;
  searchTextsOfNational: any;
  searchTextsOfStateLists: any;
  searchTextsOfStateListsContacts: any;
  searchTextsOfDistrict: any;
  searchTextsOfDistrictContacts: any;
  submitModel: any = {};
  selectedFilesToUpload: any = {};
  rtiUploadedFiles: any = [];
  showNoCmsData: Boolean = true; 
  rtiHeaderList: string [];
  editClicked: boolean = false;
  clickledImagePrview: any;
  isImageSelected: boolean= false;
  constructor() { }

  getKeys(obj){
    return Object.keys(obj)
  }
  
  
}
