import { Component, signal, inject, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { FormComponent } from './component/form-component/form-component';
import { TableComponent } from './component/table-component/table-component';
import { NavigationService, ViewType } from './service/navigation-service';
import { User } from './model/User';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, FormComponent, TableComponent, CommonModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {
  protected readonly title = signal('app');
  private navigationService = inject(NavigationService);
  currentView: ViewType = 'form';
  userToEdit: User | null = null;
  isEditMode = false;

  ngOnInit() {
    this.navigationService.currentView$.subscribe(view => {
      this.currentView = view;
    });
  }

  onUserSaved(user: User) {
    this.userToEdit = null;
    this.isEditMode = false;
    this.navigationService.showTable();
  }

  onCancelEdit() {
    this.userToEdit = null;
    this.isEditMode = false;
    this.navigationService.showTable();
  }

  onEditUser(user: User) {
    this.userToEdit = user;
    this.isEditMode = true;
    this.navigationService.showForm();
  }
}
