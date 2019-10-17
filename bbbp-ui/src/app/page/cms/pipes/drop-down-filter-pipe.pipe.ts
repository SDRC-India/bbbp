import { Pipe, PipeTransform } from '@angular/core';
import { UtilService } from '../../../service/util.service';

@Pipe({
  name: 'dropDownFilterPipe'
})
export class DropDownFilterPipePipe implements PipeTransform {


  transform(areas: any, areaLevel: number, parentAreaId: number): IArea[] {

    
    if(areas != undefined && areas != null && areaLevel != undefined && areaLevel != null && parentAreaId != undefined && parentAreaId != null ){      
      
          return areas.filter(area => area.parentId === parentAreaId)
    }
    else {
      return [];
    }
  }

}
