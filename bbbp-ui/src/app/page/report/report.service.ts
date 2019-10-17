import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Constants } from '../../constants';
declare var $: any;

@Injectable()
export class ReportService {

  areas: any;
  timeperiods: any;
  constructor(private http: HttpClient) { }

  getAreas() {
    return this.http.get(Constants.DASHBOARD_URL + 'getAreaList');
  }

  getTimeperiods() {
    return this.http.get(Constants.DASHBOARD_URL + 'getYearList');
  }

  downloadRawDataReport(stateId, districtId) {
    return this.http.get(Constants.HOME_URL + 'getRawDataReport?stateId=' + stateId +
     '&districtId=' + districtId, {
      responseType: "blob"
      });
  }

  /**
   * Download the report excel
   * @param data 
   */
  download(data) {
    if (data) {
      //data can be string of parameters or array/object
      data = typeof data == 'string' ? data : $.param(data);
      //split params into form inputs
      var inputs = '';
      let url = Constants.HOME_URL + 'downloadReport';
      $.each(data.split('&'), function () {
        var pair = this.split('=');
        inputs += '<input type="hidden" name="' + pair[0] + '" value="' + pair[1] + '" />';
      });
      //send request
      $('<form action="' + url + '" method="post">' + inputs + '</form>')
        .appendTo('body').submit().remove();
    };
  }
}
