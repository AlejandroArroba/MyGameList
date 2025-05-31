import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginRequest } from '../models/login-request';
import { RegistroRequest } from '../models/registro-request';
import { AuthResponse } from '../models/auth-response';

@Injectable({ providedIn: 'root' })
export class AuthService {

  private apiUrl = 'http://localhost:8080/auth';

  constructor(private http: HttpClient) {}

  login(request: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/login`, request);
  }

  register(request: RegistroRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/register`, request);
  }

  getToken(): string | null {
    if (typeof window !== 'undefined') {
      return localStorage.getItem('token');
    }
    return null;
  }

  updatePerfil(data: any) {
    const token = this.getToken();
    return this.http.put('http://localhost:8080/api/perfil', data, {
      headers: token ? { Authorization: `Bearer ${token}` } : {}
    });
  }

  getPerfil(): Observable<any> {
    const token = this.getToken();
    return this.http.get('http://localhost:8080/api/perfil', {
      headers: token ? { Authorization: `Bearer ${token}` } : {}
    });
  }
}
