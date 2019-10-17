import { Component, OnInit } from '@angular/core';
import { Constants } from '../../../constants';
import { HttpClient, HttpRequest, HttpEvent, HttpEventType, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
declare var $: any;
import saveAs from 'save-as';

@Component({
  selector: 'app-upload-csr',
  templateUrl: './upload-csr.component.html',
  styleUrls: ['./upload-csr.component.scss']
})
export class UploadCsrComponent implements OnInit {

  errorMessage: string;
  temlateDownloadDisclamer: string = 'Please download the template from here to update the data, only '
  + 'the template downloaded from here will accepted.';
  templateUploadDisclamer: string = 'Please upload the template downloaded '
  + 'from this page. Please add time period to update CSR data';
  progress: { percentage: number } = { percentage: 0 };
  currentFileUpload: boolean = false;
  fileName: string = '';
  file: any;
  finalUpload: any;
  validated: boolean = false;
  validateResponse: ResponseModel;
  uploadedResponse: ResponseModel;
  sucessMessage: string = '';
  infoMessage: string = ''
  message: any;
  // requestModel: RequestModel = new RequestModel();
  // userServices: UserManagementService;
  // constructor(private userManagementServiceProvider: UserManagementService,
  //   private commonServiceService: CommonServiceService, public messagingService: MessagingService) {
  //   this.userServices = userManagementServiceProvider;
  // }
  constructor( private http: HttpClient) {
    
  }

  ngOnInit() {

  }

  /* file selection */
  onFileChange($event) {
    if ($event.srcElement.files.length == 0) {
      this.fileName = undefined;
      this.file = undefined;
    } else {
      if ($event.srcElement.files[0]) {
        if (
          (($event.srcElement.files[0].name.split('.')[($event.srcElement.files[0].name.split('.') as string[]).length - 1] as String).toLocaleLowerCase() === 'xlsx')
          || (($event.srcElement.files[0].name.split('.')[($event.srcElement.files[0].name.split('.') as string[]).length - 1] as String).toLocaleLowerCase() === 'xls')
        ) {
          this.fileName = $event.srcElement.files[0].name;
          this.file = $event.srcElement.files[0];
          this.currentFileUpload = true;
          this.uploadBulkFile(this.file).subscribe(event => {
            if (event.type === HttpEventType.UploadProgress) {
              this.progress.percentage = Math.round(
                (100 * event.loaded) / event.total
              );
            } else if (event instanceof HttpResponse) {
              this.currentFileUpload = false;
              this.validateResponse = null;
              this.validated = false;
              this.finalUpload = event.body;
              if (this.finalUpload.length > 5) {
                this.currentFileUpload = false;
                this.validateResponse = null;
                this.validated = false;
                this.progress.percentage = 0;
                this.file = undefined;
                this.fileName = undefined;
                this.errorMessage = this.finalUpload;
                $('#errorModalLists').modal('show');
              }else if(this.finalUpload.length != 0){
                this.currentFileUpload = false;
                this.validateResponse = null;
                this.validated = false;
                this.progress.percentage = 0;
                this.file = undefined;
                this.fileName = undefined;
                $event.srcElement.value = null;
                this.errorMessage = this.finalUpload;
                $('#errorModal').modal('show');
              } else {
                this.sucessMessage = "Template uploaded successfully.";
                $('#successModal').modal('show');
              }
              this.progress.percentage = 0;
            }
          }, error => {
            this.currentFileUpload = false;
            this.validateResponse = null;
            this.validated = false;
            this.progress.percentage = 0;
            this.file = undefined;
            this.fileName = undefined;
            $event.srcElement.value = null;
            this.errorMessage = error.error.text == undefined ? error.error.message : error.error.text;
            $('#errorModal').modal('show');
          });
        } else {
          $event.srcElement.value = null;
          this.errorMessage = 'Upload a xlsx file';
          $('#errorModal').modal('show');
        }
      }
    }
  }
  uploadClicked() {
    $('#fileUpload').click();
  }

  resetField() {
    // this.currentFileUpload = false;
    // this.validateResponse = null;
    // this.validated = false;
    // this.progress.percentage = 0;
    // this.file = undefined;
    // this.fileName = undefined;
  }

  // download csr file
 
  downloadBulkUserFile() {
    this.downloadTemplateFile().subscribe(
      data => {
        saveAs(data, "CSR.xlsx");
      },
      error => {
        this.errorMessage = 'Some error occured';
        $('#errorModal').modal('show');
      }
    );
  }

  downloadTemplateFile() {
    return this.http.post(Constants.DASHBOARD_URL + 'downloadTemplate', '', {
      responseType: 'blob',
    });
  }

  uploadedFile() {
    // saveAs(this.file, this.fileName);
  }

  uploadBulkFile(file: File): Observable<HttpEvent<{}>> {
  const formdata: FormData = new FormData();
  formdata.append('templateFile', file);
  const req = new HttpRequest('POST', Constants.DASHBOARD_URL + 'uploadTemplate', formdata, {
    reportProgress: true
  });
  return this.http.request(req);
}

  downloadOutputFile() {
    // this.commonServiceService.downloadFile(fileData.filePath).subscribe(
    //   data => {
    //     saveAs(data, fileData.fileName);
    //   },
    //   error => {
    //     this.errorMessage = Constants.SERVER_ERROR_MESSAGE;
    //     $('#errorModal').modal('show');
    //   }
    // );
  }

  /* hide show left panel accroding to window size */
  showLists() {
    $(".left-list").attr("style", "display: block !important");
    $('.mob-left-list').attr("style", "display: none !important");
  }
  ngAfterViewInit() {
    $("input, textarea, .select-dropdown").focus(function () {
      $(this).closest(".input-holder").parent().find("> label").css({ "color": "#4285F4" })

    })
    $("input, textarea, .select-dropdown").blur(function () {
      $(this).closest(".input-holder").parent().find("> label").css({ "color": "#333" })
    })
    $('body,html').click(function (e) {
      if ((window.innerWidth) <= 767) {
        if (e.target.className == "mob-left-list") {
          return;
        } else {
          $(".left-list").attr("style", "display: none !important");
          $('.mob-left-list').attr("style", "display: block !important");
        }
      }
    });
  }

}
