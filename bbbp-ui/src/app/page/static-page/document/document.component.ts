import { Component, OnInit } from '@angular/core';

import { DomSanitizer } from '../../../../../node_modules/@angular/platform-browser';
import { Constants } from '../../../constants';
import { StaticPageService } from '../services/static-page.service';

declare var $: any;

@Component({
  selector: 'app-document',
  templateUrl: './document.component.html',
  styleUrls: ['./document.component.scss']
})
export class DocumentComponent implements OnInit {
  subSection: any[] = [];
  selectedItem: any;
  isDesc: any;
  showSelectedData: Boolean;
  showOrderData: Boolean;
  column: any;
  direction: number;
  p: number = 1;
  url: any;
  searchTexts: any;
  previewFile: boolean = true;
  staticService: StaticPageService;
  f_sl:number = 1;
  constructor(private documentService: StaticPageService, private sanitizer: DomSanitizer,
    private staticServiceProvider: StaticPageService) {
      this.staticService = staticServiceProvider;
     }

  ngOnInit() {
    this.staticService.getData("Documents").subscribe(data=>{
      this.staticService.reinitializeData(data);
          this.selectedItem = this.staticService.contentDetails[0];     
          this.getDocData(0, event, this.selectedItem );
    })
    }
  downloadFiles(fileName) {
    window.location.href = Constants.HOME_URL + 'cms/downloadCmsDoc?fileName=' + fileName;
  }
  openPDF(category) {
    this.url = this.sanitizer.bypassSecurityTrustResourceUrl(Constants.HOME_URL + 'cms/downloadCmsDoc?fileName= ' + category + '&inline=' + true);
    $("#myModal").modal("show");
  }

 
  getDocData(index: any, event, newValue){
    this.p = 0;
    this.searchTexts = "";
    this.selectedItem = newValue; 
    this.subSection = this.staticService.nestedSection[index];
    if(Object.keys(this.subSection[0]).length === 0){
      this.showOrderData = false;
    }else{
      this.showOrderData = true;
    }
   }

  // sort(property) {
  //   this.isDesc = !this.isDesc; //change the direction    
  //   this.column = property;
  //   let direction = this.isDesc ? 1 : -1;

  //   this.staticService.nestedSection.sort(function (a, b) {
  //     if (new Date(a[property].replace(/(\d{2})-(\d{2})-(\d{4})/, "$2/$1/$3")) < new Date(b[property].replace(/(\d{2})-(\d{2})-(\d{4})/, "$2/$1/$3"))) {
  //       return -1 * direction;
  //     }
  //     else if (new Date(a[property].replace(/(\d{2})-(\d{2})-(\d{4})/, "$2/$1/$3")) > new Date(b[property].replace(/(\d{2})-(\d{2})-(\d{4})/, "$2/$1/$3"))) {
  //       return 1 * direction;
  //     }
  //     else {
  //       return 0;
  //     }
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
