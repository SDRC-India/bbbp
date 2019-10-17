import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'objIterate'
})
export class ObjIteratePipe implements PipeTransform {

  transform(value: any, args: any[] = null): any {
    return Object.keys(value).map(key => Object.assign({ key }, value[key]));
}

}
