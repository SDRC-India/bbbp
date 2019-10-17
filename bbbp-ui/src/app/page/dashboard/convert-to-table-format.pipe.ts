import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'convertToTableFormat'
})
export class ConvertToTableFormatPipe implements PipeTransform {

  transform(arr: any, indicatorName: any): any {

    let tableData: any[] = [];
    for (let i = 0; i < arr.length; i++) {
      const el = arr[i];
      tableData[i] = {'District': el.name};
      if(el.key != null)
        tableData[i][indicatorName] = el.key;
      else
        tableData[i][indicatorName] = 'Not Available';
    }
    return tableData;
  }

}
