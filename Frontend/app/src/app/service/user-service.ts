import { inject, Injectable } from '@angular/core';
import { User } from '../model/User';
import { CreateUserRequest } from '../model/CreateUserRequest';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/internal/Observable';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/users`;
  private users: User[] = [];

  constructor() {
    console.log('Environment:', environment);
    console.log('API URL:', this.apiUrl);
  }

  getUsers(): Observable<User[]> {
    console.log('Fetching users from:', this.apiUrl);
    return this.http.get<User[]>(this.apiUrl);
  }

  addUser(user: User): Observable<User> {
    console.log('Adding user to:', this.apiUrl);
    // Crear un objeto sin el ID para evitar conflictos con la generación automática
    const createUserRequest: CreateUserRequest = {
      firstName: user.firstName,
      lastName: user.lastName,
      email: user.email
    };
    return this.http.post<User>(this.apiUrl, createUserRequest);
  }

  deleteUser(id: number): Observable<string> {
    console.log('Deleting user from:', `${this.apiUrl}/${id}`);
    return this.http.delete(`${this.apiUrl}/${id}`, { responseType: 'text' });
  }

  updateUser(user: User): Observable<User> {
    console.log('Updating user at:', `${this.apiUrl}/${user.id}`);
    // Para actualizar, enviamos el objeto completo pero en la URL especificamos el ID
    return this.http.put<User>(`${this.apiUrl}/${user.id}`, user);
  } 
}
