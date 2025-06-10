import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router'; // Importar el Router para redirigir
import { CommonModule } from '@angular/common';
import {FormsModule} from '@angular/forms'; // Importar CommonModule para *ngIf

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css'],
  standalone: true,  // Esto hace que el componente sea independiente y no dependa de un módulo
  imports: [CommonModule, FormsModule] // Añadir CommonModule aquí
})
export class ForgotPasswordComponent {
  email: string = ''; // Esta es la variable donde el usuario ingresa su correo.
  mensaje: string = ''; // Aquí se mostrará el mensaje (por ejemplo, "Usuario encontrado. Puedes cambiar la contraseña").
  nuevaContrasenaGenerada: string = ''; // Aquí almacenaremos la contraseña generada aleatoriamente.
  mostrarFormularioCambio: boolean = false; // Controla si el formulario para cambiar la contraseña está visible.

  constructor(private authService: AuthService, private router: Router) {}

  solicitarRecuperacion(): void {
    if (this.email) {
      this.authService.forgotPassword(this.email).subscribe({
        next: (response: string) => {
          this.mensaje = ''; // Limpiar el mensaje previo
          this.nuevaContrasenaGenerada = response.split(": ")[1]; // Extraemos la nueva contraseña
        },
        error: (err) => {
          this.mensaje = 'Error al solicitar recuperación. Intenta de nuevo.';
          console.error(err);
        }
      });
    }
  }

  cambiarContrasena(): void {
    if (this.nuevaContrasenaGenerada) {
      this.authService.resetPassword(this.email, this.nuevaContrasenaGenerada).subscribe({
        next: (response) => {
          this.mensaje = 'Contraseña cambiada correctamente.';
        },
        error: (err) => {
          this.mensaje = 'Error al cambiar la contraseña. Intenta de nuevo.';
          console.error(err);
        }
      });
    }
  }

  irAlLogin(): void {
    this.router.navigate(['/login']); // Redirige al login
  }
}
