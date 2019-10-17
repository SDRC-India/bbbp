import { Injectable } from '@angular/core';
import { Cookie } from 'ng2-cookies';
import { Observable, Subject, Subscription } from 'rxjs';

@Injectable()
export class SessionCheckService {

  private _count: number = 0;

  private _timeoutSeconds: number = 3600;
  private timerSubscription: Subscription;
  private timer: Observable<number>;
  private resetOnTrigger: boolean = false;
  public timeoutExpired: Subject<number> = new Subject<number>();
  
  constructor() { 

 
    this.timeoutExpired.subscribe(n => {
        Cookie.delete('access_token');
        Cookie.delete('JSESSIONID');
        Cookie.delete('XSRF-TOKEN')
    });

    // this.startTimer();
  }

//   public startTimer() {
//     if (this.timerSubscription) {
//         this.timerSubscription.unsubscribe();
//     }
 
//     this.timer = Observable.timer(this._timeoutSeconds * 1000);
//     this.timerSubscription = this.timer.subscribe(n => {
//         this.timerComplete(n);
//     });
//   }
  
//   public stopTimer() {
//       this.timerSubscription.unsubscribe();
//   }
  
//   public resetTimer() {
//       if (this.timerSubscription) {
//           this.timerSubscription.unsubscribe();
//       }
  
//       this.timer = Observable.timer(this._timeoutSeconds * 1000);
//       this.timerSubscription = this.timer.subscribe(n => {
//           this.timerComplete(n);
//       });
//   }

//   private timerComplete(n: number) {
//     this.timeoutExpired.next(++this._count);

//     if (this.resetOnTrigger) {
//         this.startTimer();
//     }
//   }
}