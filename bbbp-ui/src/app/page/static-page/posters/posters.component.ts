import { Component, OnInit } from '@angular/core';
import { Constants } from '../../../constants';
import { StaticPageService } from '../services/static-page.service';
import { DomSanitizer } from '../../../../../node_modules/@angular/platform-browser';
declare var $: any;

@Component({
  selector: 'app-posters',
  templateUrl: './posters.component.html',
  styleUrls: ['./posters.component.scss']
})
export class PostersComponent implements OnInit {

  postersData:any;
  postersDetails: any[] = [];
  postersSection: any[] = [];
  isDesc:any;
  column :any;
  direction:number;
  p: number = 1;
  url: any;
  searchTexts: any;
  staticService: StaticPageService;
 constructor(private staticServiceProvider:StaticPageService, private sanitizer: DomSanitizer) {
   this.staticService = staticServiceProvider;
  }

//  ngOnInit() {
//   this.posterservices.getData("Posters").subscribe(data=>{
//     this.postersData=  data;
//     this.postersDetails = this.postersData.viewContent.english;
//     for(var i=0;i<this.postersDetails.length;i++)
//     {
//       this. postersSection = this.postersDetails[i].data;
//     }
// })
// }
ngOnInit() {
  this.staticService.getData("Posters").subscribe(data=>{
    this.postersData =  data;
    this.staticService.reinitializeData(data);
})
}
downloadFiles(fileName){  
    window.location.href = Constants.HOME_URL + 'cms/downloadCmsDoc?fileName='+fileName;
  }
  openPDF(category,title){
    this.staticService.fileTitle = title.substring(title.lastIndexOf('/') + 1);
    this.url = this.sanitizer.bypassSecurityTrustResourceUrl(Constants.HOME_URL + 'cms/downloadCmsDoc?fileName= '+category + '&inline='+ true);
    $("#myModal").modal("show");
  }

sort(property){
  this.isDesc = !this.isDesc; //change the direction    
  this.column = property;
  let direction = this.isDesc ? 1 : -1;

  this.postersSection.sort(function(a, b){
      if(a[property] < b[property]){
          return -1 * direction;
      }
      else if( a[property] > b[property]){
          return 1 * direction;
      }
      else{
          return 0;
      }
  });
};

}
