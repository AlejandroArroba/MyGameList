import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';  // Importa CommonModule aquí
import { AuthService } from '../../services/auth.service';
import { Router, RouterLink } from '@angular/router';

@Component({
  standalone: true,  // Si es un componente standalone, usa este decorador
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  imports: [FormsModule, CommonModule, RouterLink]  // Asegúrate de incluir CommonModule
})
export class LoginComponent {
  email = '';
  password = '';
  error = '';
  mostrarPassword: boolean = false;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  irARegistro(): void {
    this.router.navigate(['/register']);
  }

  iniciarSesion(): void {
    const request = {
      email: this.email,
      password: this.password
    };

    this.authService.login(request).subscribe({
      next: (response) => {
        localStorage.setItem('token', response.token);
        localStorage.setItem('nombreUsuario', response.nombreUsuario);
        this.router.navigate(['/home']);
      },
      error: (err) => {
        this.error = 'Credenciales inválidas o error del servidor';
        console.error(err);
      }
    });
  }
}
