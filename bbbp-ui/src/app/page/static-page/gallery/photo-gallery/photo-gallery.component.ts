import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { StaticPageService } from '../../services/static-page.service';
import { Constants } from '../../../../constants';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-photo-gallery',
  templateUrl: './photo-gallery.component.html',
  styleUrls: ['./photo-gallery.component.scss']
})
export class PhotoGalleryComponent implements OnInit {

  galleryData: any;
  galleryDetails: any[] = [];
  gallerySection: any = {};
  photoCategory: any[] = [];
  p: number = 1;
  searchTexts: any;
  gateway=Constants.HOME_URL+Constants.CMS_URL;
  selectionFields: any;
  filteredGalleryDetails: any[] = [];
  submitModel: [{}];
  staticService: StaticPageService;
  constructor(private staticServiceProvider: StaticPageService, private router: Router, private http: HttpClient) {
    this.staticService = staticServiceProvider;
  }

  ngOnInit() {
    this.staticService.getDataEntryTimeperiodSelection().subscribe(data=>{
      this.selectionFields = data; 
    });
    this.staticService.getPhotogalleryData("Photo Gallery").subscribe(data => {
      this.staticService.reinitializeData(data);
      this.galleryData = data;
      this.galleryDetails = this.galleryData.viewContent[this.staticService.selectedLang];
      this.filteredGalleryDetails =  this.galleryDetails;
    });

   
  }

  getSubCategory(subPhoto) {
    this.staticService.imageSection = subPhoto.section;
    this.staticService.getSubPhotogalleryData(subPhoto.data)
      .toPromise()
      .then( 
      response => {
        this.staticService.imageCategory = response;
        this.router.navigate(['pages/sub-photo-gallery']);
      }
      );
  }

  // reset dropdown value 
  
  resetVal(modelVal){
    modelVal.forEach(element => {
      if(element.label == "Select Year"){
        element.value = undefined;
      }
      if(element.label == "Select Quarter"){
       element.value = undefined;
     }
    });
    this.staticService.getPhotogalleryData("Photo Gallery").subscribe(data => {
      this.staticService.reinitializeData(data);
      this.galleryData = data;
      this.galleryDetails = this.galleryData.viewContent[this.staticService.selectedLang];
      this.filteredGalleryDetails =  this.galleryDetails;
    });
   }



  selectDropdown(selectedOption, model, index){
    this.selectionFields[index].errorFound = false;
    this.selectionFields[index].errorMessage= "";
    this.selectionFields[index].value = selectedOption.value;
    this.selectionFields[index].key = selectedOption.key;
    this.filteredGalleryDetails = [];
  
    this.submitModel = [{}];
    let sectionCount = 0;
    let flag = false;
      this.galleryDetails.forEach(gd =>{
        // flag = false;
        gd.data.forEach(eachGd => {
          if ((eachGd.year && eachGd.quarter && (eachGd.year === model[0].value ) && eachGd.quarter === model[1].value) ||
          (!model[1].value && eachGd.year === selectedOption.value)
          || (!model[0].value && eachGd.quarter === selectedOption.value)) {

            let model ={
              'section' : gd.section
            }

            if(sectionCount == 0){
              this.submitModel[sectionCount]['section'] = gd.section;
            }
            else if (!this.submitModel[sectionCount]){
              this.submitModel.push(model)
            }
            // this.submitModel[sectionCount]['section'] = gd.section;
            if(this.submitModel[sectionCount]['data']){
             this.submitModel[sectionCount]['data'].push(eachGd);
            }else{
              this.submitModel[sectionCount]['data'] = [];
              this.submitModel[sectionCount]['data'].push(eachGd);
            }
            flag = true;
          }
        
        });
        sectionCount++;
      })
      if(flag)
      this.filteredGalleryDetails =this.submitModel;
    
  }

}
