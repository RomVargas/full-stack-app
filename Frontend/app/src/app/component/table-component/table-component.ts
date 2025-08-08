import { Component, inject, OnInit, Output, EventEmitter } from '@angular/core';
import { UserService } from '../../service/user-service';
import { User } from '../../model/User';
import { ButtonComponent } from '../button-component/button-component';
import { NavigationService } from '../../service/navigation-service';
import { AlertService } from '../../service/alert-service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-table-component',
  imports: [ButtonComponent, CommonModule],
  templateUrl: './table-component.html',
  styleUrl: './table-component.css'
})
export class TableComponent implements OnInit {
  @Output() editUser = new EventEmitter<User>();
  
  private userService = inject(UserService);
  private navigationService = inject(NavigationService);
  private alertService = inject(AlertService);
  
  users: User[] = [];
  loading = false;

  ngOnInit() {
    this.cargarUsuarios();
  }

  cargarUsuarios() {
    console.log('🔄 Iniciando carga de usuarios...');
    this.loading = true;
    this.userService.getUsers().subscribe({
      next: (users) => {
        this.users = users;
        this.loading = false;
        console.log('✅ Usuarios cargados exitosamente:', users.length, 'usuarios');
        console.log('📋 Lista actualizada:', users);
      },
      error: (error) => {
        console.error('❌ Error al cargar usuarios:', error);
        this.loading = false;
        this.alertService.error(
          'Error al cargar usuarios',
          'No se pudieron cargar los usuarios. Por favor, intenta nuevamente.'
        );
      }
    });
  }

  volverAlFormulario() {
    this.navigationService.showForm();
  }

  deleteUser(id: number) {
    const user = this.users.find(u => u.id === id);
    const userName = user ? `${user.firstName} ${user.lastName}` : 'este usuario';
    
    this.alertService.confirmDelete(userName).then((result) => {
      if (result.isConfirmed) {
        console.log('🗑️ Iniciando eliminación del usuario:', id, userName);
        
        // Mostrar alerta de carga
        this.alertService.loading('Eliminando usuario...');
        
        this.userService.deleteUser(id).subscribe({
          next: (response) => {
            // Si llegamos aquí, significa que la eliminación fue exitosa (200-299)
            console.log('✅ Usuario eliminado exitosamente');
            this.alertService.close();
            
            // Recargar la lista inmediatamente
            this.cargarUsuarios();
            
            // Mostrar mensaje de éxito
            this.alertService.success(
              'Usuario eliminado',
              `${userName} ha sido eliminado exitosamente.`
            );
          },
          error: (error) => {
            console.error('❌ Error al eliminar usuario:', error);
            this.alertService.close();
            this.alertService.error(
              'Error al eliminar usuario',
              'No se pudo eliminar el usuario. Por favor, intenta nuevamente.'
            );
          }
        });
      }
    });
  }

  updateUser(id: number) {
    const user = this.users.find(u => u.id === id);
    if (user) {
      console.log('📝 Editando usuario:', user);
      this.editUser.emit(user);
    } else {
      this.alertService.error(
        'Error',
        'Usuario no encontrado'
      );
    }
  }
}
