import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export type ViewType = 'form' | 'table';

@Injectable({
  providedIn: 'root'
})
export class NavigationService {
  private currentViewSubject = new BehaviorSubject<ViewType>('form');
  public currentView$ = this.currentViewSubject.asObservable();

  showForm() {
    this.currentViewSubject.next('form');
  }

  showTable() {
    this.currentViewSubject.next('table');
  }

  getCurrentView(): ViewType {
    return this.currentViewSubject.value;
  }
}
