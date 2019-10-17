import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'contribution'
})
export class ContributionPipe implements PipeTransform {

  transform(contributeSection: any, args?: any): any {
    return  contributeSection.sort(function(a, b){
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
