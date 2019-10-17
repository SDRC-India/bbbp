import { Component, OnInit } from '@angular/core';
import { Constants } from '../../../../constants';
import { StaticPageService } from '../../services/static-page.service';
import { DomSanitizer } from '../../../../../../node_modules/@angular/platform-browser';
declare var $: any;

@Component({
  selector: 'app-revalidationletter',
  templateUrl: './revalidationletter.component.html',
  styleUrls: ['./revalidationletter.component.scss']
})
export class RevalidationletterComponent implements OnInit {

  subSection: any[] = [];
  selectedItem: any;
  showOrderData: Boolean;
  isDesc:any;
  column :any;
  direction:number;
  p: number = 1;
  url: any;
  searchTexts: any;
  f_sl: number = 1;
  areaDetails: any;  
  stateParentAreaId: number;
  distParentAreaId:number;
  selectedDistrict: string;
  selectedState: string;
  dataSection: any[] = [];
  staticService: StaticPageService;
  constructor(private staticServiceProvider:StaticPageService, private sanitizer: DomSanitizer) {
    this.staticService = staticServiceProvider;
   }

   ngOnInit() {
    this.staticService.getAreaDetails().subscribe(data=>{
      this.areaDetails = data;      
    })   
    this.staticService.getData("Letters").subscribe(data=>{
      this.staticService.reinitializeData(data);
          this.selectedItem = this.staticService.contentDetails[0];     
      this.getYearData(0, event, this.selectedItem );
    })
  
    }

  selectState(selectedState: IArea){
      this.stateParentAreaId = selectedState.areaId;    
      this.selectedState = selectedState.areaName;
      this.selectedDistrict = '';   

      //filter state data
      this.dataSection =  this.subSection.filter(eachSection => eachSection.parentAreaId === this.stateParentAreaId
         || eachSection.areaId === this.stateParentAreaId);
      if(Object.keys(this.dataSection[0]).length === 0){
          this.showOrderData = false;
     }else{
          this.showOrderData = true;
      }

  }
  selectDistrict(selectedDist: IArea){
      this.distParentAreaId = selectedDist.areaId;
      this.selectedDistrict = selectedDist.areaName;

       //filter district data
       this.dataSection =  this.subSection.filter(eachSection => eachSection.areaId === this.distParentAreaId);
       if(Object.keys(this.dataSection[0]).length === 0){
        this.showOrderData = false;
      }else{
        this.showOrderData = true;
      }
  }
  downloadFiles(fileName){  
    window.location.href = Constants.HOME_URL + 'cms/downloadCmsDoc?fileName='+fileName;
  }

  openPDF(category){
    this.url = this.sanitizer.bypassSecurityTrustResourceUrl(Constants.HOME_URL + 'cms/downloadCmsDoc?fileName= '+category + '&inline='+ true);
    $("#myModal").modal("show");
  }

  getYearData(index: any, event, newValue){
    this.stateParentAreaId = undefined;    
    this.selectedState = '';
    this.selectedDistrict = '';   
    this.distParentAreaId = undefined;
    this.p = 0;
    this.searchTexts = "";
    this.selectedItem = newValue; 
    this.subSection = this.staticService.nestedSection[index];
    this.dataSection = this.subSection.slice().reverse();
    if(Object.keys(this.subSection[0]).length === 0){
      this.showOrderData = false;
    }else{
      this.showOrderData = true;
    }
   }

  // sort(property){
  //   this.isDesc = !this.isDesc; //change the direction    
  //   this.column = property;
  //   let direction = this.isDesc ? 1 : -1;
  
  //   this.staticService.nestedSection.sort(function(a, b){
  //       if(a[property] < b[property]){
  //           return -1 * direction;
  //       }
  //       else if( a[property] > b[property]){
  //           return 1 * direction;
  //       }
  //       else{
  //           return 0;
  //       }
  //   });
  // };

// sorting table
sortTable(f,n){
  var rows = $('#mytable tbody  tr').get();
  rows.sort(function(a, b) {
    let A = getVal(a);
    let B = getVal(b);
    if(A < B) {
      return -1*f;
    }
    if(A > B) {
      return 1*f;
    }
    return 0;
  });

  function getVal(elm){
    var v = $(elm).children('td').eq(n).text().toUpperCase();
    if($.isNumeric(v)){
      v = parseInt(v,10);
    }
    return v;
  }
  $.each(rows, function(index, row) {
    $('#mytable').children('tbody').append(row);
  });
}
sort(publishedDate){
    this.f_sl *= -1;
    var n = $(this).prevAll().length;
    this.sortTable(this.f_sl,n);
}






}
