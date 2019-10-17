import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
declare var $: any;

@Injectable()
export class SdrcLoaderService {
  public isLoading = new BehaviorSubject(false);  

  show(){
    this.isLoading.next(true);
    document.body.classList.add("loader-open");
    document.body.style.overflow = "hidden";
  }

  hide(){
    this.isLoading.next(false);
    document.body.classList.remove("loader-open");
    document.body.style.overflow = "auto";
  }
}

