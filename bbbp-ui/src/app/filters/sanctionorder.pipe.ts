import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'sanctionorder'
})
export class SanctionorderPipe implements PipeTransform {

  transform(SubSection: any, args?: any): any {
    return  SubSection .sort(function(a, b){
      if(a[args.property] < b[args.property]){
          return -1 * args.direction;
      }
      else if( a[args.property] > b[args.property]){
          return 1 * args.direction;
      }
      else{
          return 0;
      }
  });
  }

}
