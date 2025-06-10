import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {HeaderComponent} from '../../components/header/header.component';

@Component({
  selector: 'app-reset-password',
  standalone: true,
  imports: [
    FormsModule,
    CommonModule,  // Necesario para usar *ngIf
    HeaderComponent
  ],
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {
  token: string = '';  // El token que se obtendrá desde la URL
  newPassword: string = '';  // La nueva contraseña que el usuario ingresará
  mensaje: string = '';  // Mensaje que se mostrará después de intentar cambiar la contraseña

  constructor(
    private route: ActivatedRoute,  // Para obtener los parámetros de la URL
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    // Obtener el token de la URL
    this.token = this.route.snapshot.queryParamMap.get('token')!;
  }

  restablecerContrasena(): void {
    if (this.token && this.newPassword) {
      this.authService.resetPassword(this.token, this.newPassword).subscribe({
        next: (response) => {
          this.mensaje = 'Contraseña restablecida correctamente';
        },
        error: (err) => {
          this.mensaje = 'Error al restablecer la contraseña';
          console.error(err);
        }
      });
    }
  }
}
