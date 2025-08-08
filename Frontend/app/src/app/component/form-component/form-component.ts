import { Component, inject, Input, Output, EventEmitter, OnChanges, SimpleChanges } from '@angular/core';
import { ButtonComponent } from '../button-component/button-component';
import { FormsModule } from '@angular/forms';
import { User } from '../../model/User';
import { UserService } from '../../service/user-service';
import { NavigationService } from '../../service/navigation-service';
import { AlertService } from '../../service/alert-service';

@Component({
  selector: 'app-form-component',
  imports: [ButtonComponent, FormsModule],
  templateUrl: './form-component.html',
  styleUrl: './form-component.css'
})
export class FormComponent implements OnChanges {
  @Input() userToEdit: User | null = null;
  @Input() isEditMode: boolean = false;
  @Output() userSaved = new EventEmitter<User>();
  @Output() cancelEdit = new EventEmitter<void>();

  user: User = {
    id: 0,
    firstName: '',
    lastName: '',
    email: ''
  };
  
  userService: UserService = inject(UserService);
  navigationService: NavigationService = inject(NavigationService);
  alertService: AlertService = inject(AlertService);

  ngOnChanges(changes: SimpleChanges) {
    if (changes['userToEdit'] && this.userToEdit) {
      this.user = { ...this.userToEdit };
      this.isEditMode = true;
    } else if (!this.userToEdit) {
      this.resetForm();
      this.isEditMode = false;
    }
  }

  private resetForm() {
    this.user = {
      id: 0,
      firstName: '',
      lastName: '',
      email: ''
    };
  }

  private handleError(error: any) {
    if (error.status === 400) {
      // Error de validación del backend
      let errorMessage = 'Error de validación: ';
      
      if (error.error && typeof error.error === 'object') {
        // Múltiples errores de validación
        const errors = Object.values(error.error).join(', ');
        errorMessage += errors;
      } else if (error.error && typeof error.error === 'string') {
        // Error específico (ej: email duplicado)
        errorMessage += error.error;
      } else {
        errorMessage += 'Datos inválidos';
      }
      
      this.alertService.validationError(errorMessage);
    } else if (error.status === 409) {
      // Conflicto (email duplicado)
      this.alertService.error(
        'Email duplicado',
        'Ya existe un usuario con ese email. Por favor, usa un email diferente.'
      );
    } else {
      // Error del servidor (500, etc.)
      this.alertService.error(
        'Error del servidor', 
        'Ha ocurrido un error interno del servidor. Por favor, intenta nuevamente más tarde.'
      );
    }
  }

  cancelar() {
    this.cancelEdit.emit();
  }


  guardar() {
    console.log('Datos del usuario:', this.user);
    
    // Validación de campos requeridos
    if (!this.user.firstName || !this.user.lastName || !this.user.email) {
      this.alertService.validationError('Por favor, completa todos los campos requeridos.');
      return;
    }

    // Validación básica de email
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(this.user.email)) {
      this.alertService.validationError('Por favor, ingresa un email válido.');
      return;
    }

    console.log('Formulario válido, enviando datos...');
    
    // Mostrar alerta de carga
    const loadingMessage = this.isEditMode ? 'Actualizando usuario...' : 'Guardando usuario...';
    this.alertService.loading(loadingMessage);
    
    if (this.isEditMode) {
      // Actualizar usuario existente
      this.userService.updateUser(this.user).subscribe({
        next: (response) => {
          console.log('✅ Usuario actualizado exitosamente:', response);
          this.alertService.close();
          
          // Mostrar mensaje de éxito
          this.alertService.success(
            '¡Usuario actualizado!', 
            `${this.user.firstName} ${this.user.lastName} ha sido actualizado exitosamente.`
          ).then(() => {
            // Emitir evento de usuario guardado
            this.userSaved.emit(response);
          });
        },
        error: (error) => {
          console.error('❌ Error al actualizar usuario:', error);
          this.alertService.close();
          this.handleError(error);
        }
      });
    } else {
      // Crear nuevo usuario
      this.userService.addUser(this.user).subscribe({
        next: (response) => {
          console.log('✅ Usuario guardado exitosamente:', response);
          this.resetForm();
          
          // Cerrar alerta de carga
          this.alertService.close();
          
          // Mostrar mensaje de éxito
          this.alertService.success(
            '¡Usuario guardado!', 
            `${this.user.firstName} ${this.user.lastName} ha sido registrado exitosamente.`
          ).then(() => {
            // Emitir evento de usuario guardado
            this.userSaved.emit(response);
          });
        },
        error: (error) => {
          console.error('❌ Error al guardar usuario:', error);
          this.alertService.close();
          this.handleError(error);
        }
      });
    }
  }


}
