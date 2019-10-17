import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'bestpratice'
})
export class BestpraticePipe implements PipeTransform {

  transform(bestpraticeSection: any, args?: any): any {
    return  bestpraticeSection .sort(function(a, b){
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
