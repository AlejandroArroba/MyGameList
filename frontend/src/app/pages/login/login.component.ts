import { Component } from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { LoginRequest } from '../../models/login-request';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  standalone: true,
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'], // 👈 aquí el cambio
  imports: [CommonModule, FormsModule, RouterLink]
})
export class LoginComponent {
  email = '';
  contrasena = '';
  error = '';

  constructor(
    private authService: AuthService,
    private router: Router // 👈 útil si vas a redirigir después del login
  ) {}

  irARegistro(): void {
    this.router.navigate(['/register']);
  }

  iniciarSesion(): void {
    const request: LoginRequest = {
      email: this.email,
      password: this.contrasena
    };

    this.authService.login(request).subscribe({
      next: (response) => {
        localStorage.setItem('token', response.token);
        localStorage.setItem('nombreUsuario', response.nombreUsuario); // 👈 nuevo
        console.log('Token guardado:', response.token);
        this.router.navigate(['/home']);
      },
      error: (err) => {
        this.error = 'Credenciales inválidas o error del servidor';
        console.error(err);
      }
    });

  }
}
