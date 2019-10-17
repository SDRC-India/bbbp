import { Component, OnInit } from '@angular/core';
import { StaticPageService } from '../services/static-page.service';
import { DomSanitizer } from '../../../../../node_modules/@angular/platform-browser';
import { Constants } from '../../../constants';
declare var $: any;

@Component({
  selector: 'app-others',
  templateUrl: './others.component.html',
  styleUrls: ['./others.component.scss']
})
export class OthersComponent implements OnInit {
  subSection: any[] = [];
  selectedItem: any;
  isDesc: any;
  column: any;
  direction: number;
  showOrderData: Boolean;
  p: number = 1;
  url: any;
  searchTexts: any;
  f_sl: number = 1;
  dataSection: any[] = [];
  filteredGalleryDetails: any[] = [];
  galleryDetails: any[] = [];
  field: any;
  staticService: StaticPageService;
  constructor(private staticServiceProvider: StaticPageService, private sanitizer: DomSanitizer) {
    this.staticService = staticServiceProvider;
  }

  ngOnInit() {
    this.staticService.getDataEntryTimeperiodSelection().subscribe(data => {
      // this.selectionFields = data; 
      this.field = data[0];
    });
    this.staticService.getData("Others").subscribe(data => {
      this.staticService.reinitializeData(data);
      this.selectedItem = this.staticService.contentDetails[0];
      this.getYearData(0, event, this.selectedItem);
    })

  }


  selectDropdown(selectedOption) {
    this.field.value = selectedOption.value;
    this.field.key = selectedOption.key;

    //filter state data
    this.dataSection = this.subSection.filter(eachSection => eachSection.year === selectedOption.value);
    if (Object.keys(this.dataSection[0]).length === 0) {
      this.showOrderData = false;
    } else {
      this.showOrderData = true;
    }

  }

  downloadFiles(fileName) {
    window.location.href = Constants.HOME_URL + 'cms/downloadCmsDoc?fileName=' + fileName;
  }
  openPDF(category) {
    this.url = this.sanitizer.bypassSecurityTrustResourceUrl(Constants.HOME_URL + 'cms/downloadCmsDoc?fileName= ' + category + '&inline=' + true);
    $("#myModal").modal("show");
  }


  getYearData(index: any, event, newValue) {
    this.p = 0;
    this.searchTexts = "";
    this.selectedItem = newValue;
    this.subSection = this.staticService.nestedSection[index];
    this.dataSection = this.subSection.slice().reverse();
    if(this.field){
      this.field.value = undefined;
    }
    if (Object.keys(this.subSection[0]).length === 0) {
      this.showOrderData = false;
    } else {
      this.showOrderData = true;
    }
  }

  // sorting table
  sortTable(f, n) {
    var rows = $('#mytable tbody  tr').get();
    rows.sort(function (a, b) {
      let A = getVal(a);
      let B = getVal(b);
      if (A < B) {
        return -1 * f;
      }
      if (A > B) {
        return 1 * f;
      }
      return 0;
    });

    function getVal(elm) {
      var v = $(elm).children('td').eq(n).text().toUpperCase();
      if ($.isNumeric(v)) {
        v = parseInt(v, 10);
      }
      return v;
    }
    $.each(rows, function (index, row) {
      $('#mytable').children('tbody').append(row);
    });
  }
  sort(publishedDate) {
    this.f_sl *= -1;
    var n = $(this).prevAll().length;
    this.sortTable(this.f_sl, n);
  }
}
