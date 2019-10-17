import { Component, OnInit } from '@angular/core';

import { FormDataSaveService } from '../../../service/form-data-save.service';

@Component({
  selector: 'app-uploading-modal',
  templateUrl: './uploading-modal.component.html',
  styleUrls: ['./uploading-modal.component.scss']
})
export class UploadingModalComponent implements OnInit {

  uploadedFilesNumber:number;
  TotalFilesNumber:number;
  formData: FormDataSaveService;
  constructor(private formDataSaveService: FormDataSaveService) { 
    this.formData = formDataSaveService;
  }


  ngOnInit() {
    this.uploadedFilesNumber = this.formDataSaveService.totalNumberFilesUploaded;
    this.TotalFilesNumber = this.formDataSaveService.selectedFilesToUpload.length;
  }


}
