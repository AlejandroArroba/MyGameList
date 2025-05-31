import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { RegistroRequest } from '../../models/registro-request';
import {Router, RouterModule} from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  imports: [CommonModule, FormsModule, RouterModule]
})
export class RegisterComponent {
  nombreUsuario = '';
  email = '';
  contrasena = '';
  error = '';
  exito = '';

  constructor(private authService: AuthService, private router: Router) {}

  registrar(): void {
    const request: RegistroRequest = {
      nombreUsuario: this.nombreUsuario,
      email: this.email,
      contrasena: this.contrasena
    };

    this.authService.register(request).subscribe({
      next: (response) => {
        localStorage.setItem('token', response.token);
        localStorage.setItem('nombreUsuario', response.nombreUsuario); // 👈 nuevo
        this.exito = 'Registro exitoso';
        this.error = '';
        this.router.navigate(['/']);
      },
      error: (err) => {
        this.error = 'Error al registrar usuario';
        this.exito = '';
        console.error(err);
      }
    });
  }
}
