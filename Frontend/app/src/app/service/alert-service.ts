import { Injectable } from '@angular/core';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class AlertService {

  // Alerta de éxito
  success(title: string, message?: string) {
    return Swal.fire({
      icon: 'success',
      title: title,
      text: message,
      confirmButtonText: 'Aceptar',
      confirmButtonColor: '#28a745',
      timer: 3000,
      timerProgressBar: true
    });
  }

  // Alerta de error
  error(title: string, message?: string) {
    return Swal.fire({
      icon: 'error',
      title: title,
      text: message,
      confirmButtonText: 'Aceptar',
      confirmButtonColor: '#dc3545'
    });
  }

  // Alerta de confirmación
  confirm(title: string, message?: string) {
    return Swal.fire({
      icon: 'question',
      title: title,
      text: message,
      showCancelButton: true,
      confirmButtonText: 'Sí, continuar',
      cancelButtonText: 'Cancelar',
      confirmButtonColor: '#007bff',
      cancelButtonColor: '#6c757d'
    });
  }

  // Alerta de información
  info(title: string, message?: string) {
    return Swal.fire({
      icon: 'info',
      title: title,
      text: message,
      confirmButtonText: 'Entendido',
      confirmButtonColor: '#17a2b8'
    });
  }

  // Alerta de advertencia
  warning(title: string, message?: string) {
    return Swal.fire({
      icon: 'warning',
      title: title,
      text: message,
      confirmButtonText: 'Entendido',
      confirmButtonColor: '#ffc107'
    });
  }

  // Alerta de carga
  loading(title: string = 'Cargando...') {
    Swal.fire({
      title: title,
      allowOutsideClick: false,
      didOpen: () => {
        Swal.showLoading();
      }
    });
  }

  // Cerrar alerta de carga
  close() {
    Swal.close();
  }

  // Alerta personalizada para eliminar usuario
  confirmDelete(userName: string) {
    return Swal.fire({
      icon: 'warning',
      title: '¿Eliminar usuario?',
      text: `¿Estás seguro de que quieres eliminar a ${userName}? Esta acción no se puede deshacer.`,
      showCancelButton: true,
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar',
      confirmButtonColor: '#dc3545',
      cancelButtonColor: '#6c757d',
      reverseButtons: true
    });
  }

  // Alerta para validación de formulario
  validationError(message: string) {
    return Swal.fire({
      icon: 'error',
      title: 'Error de validación',
      text: message,
      confirmButtonText: 'Entendido',
      confirmButtonColor: '#dc3545'
    });
  }
}
