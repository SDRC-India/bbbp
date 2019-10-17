import { Pipe, PipeTransform } from '@angular/core';
import { UtilService } from '../service/util.service';

@Pipe({
  name: 'areaFilter'
})
export class AreaFilterPipe implements PipeTransform {


  transform(areas: any, areaLevel: number, parentAreaId: number): IArea[] {

    
    if(areas != undefined && areas != null && areaLevel != undefined && areaLevel != null && parentAreaId != undefined && parentAreaId != null ){      
      switch(areaLevel){
        case UtilService.areaLevels.national:
          return areas.NATIONAL.filter(area => area.parentAreaId === parentAreaId)
        case UtilService.areaLevels.state:
          return areas.STATE.filter(area => area.parentAreaId === parentAreaId)
        case UtilService.areaLevels.district:
          return areas.DISTRICT.filter(area => area.parentAreaId === parentAreaId)
      }      
    }
    else {
      return [];
    }
  }

}
