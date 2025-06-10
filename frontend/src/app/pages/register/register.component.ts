import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common'; // Asegúrate de importar CommonModule
import { AuthService } from '../../services/auth.service';
import { RegistroRequest } from '../../models/registro-request';

@Component({
  standalone: true,
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  imports: [FormsModule, RouterModule, CommonModule] // Agrega CommonModule aquí
})
export class RegisterComponent {
  nombreUsuario = '';
  email = '';
  contrasena = '';
  confirmContrasena = '';
  rol = 'USER';  // Valor por defecto es 'USER'
  error = '';
  exito = '';

  constructor(private authService: AuthService, private router: Router) {}

  registrar(): void {
    if (this.contrasena !== this.confirmContrasena) {
      this.error = 'Las contraseñas no coinciden';
      return;
    }

    const request: RegistroRequest = {
      nombreUsuario: this.nombreUsuario,
      email: this.email,
      contrasena: this.contrasena,
      rol: this.rol  // Aquí se agrega el rol
    };

    this.authService.register(request).subscribe({
      next: (response) => {
        localStorage.setItem('token', response.token);
        localStorage.setItem('nombreUsuario', response.nombreUsuario);
        this.exito = 'Registro exitoso';
        this.error = '';
        this.router.navigate(['/']);
      },
      error: (err) => {
        this.error = 'Error al registrar usuario';
        this.exito = '';
        console.error(err);
      },
    });
  }
}
