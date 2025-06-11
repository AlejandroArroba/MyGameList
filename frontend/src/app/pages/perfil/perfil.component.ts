import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { HeaderComponent } from '../header/header.component';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';


@Component({
  standalone: true,
  selector: 'app-perfil',
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.css'],
  imports: [FormsModule, HeaderComponent, CommonModule] // <-- Añadir CommonModule aquí
})
export class PerfilComponent implements OnInit {
  usuario: any = null;
  gustados: any[] = [];
  pendientes: any[] = [];
  abandonados: any[] = [];

  // Variables para cambiar la contraseña
  currentPassword: string = '';
  newPassword: string = '';
  mostrarPasswordActual: boolean = false;
  mostrarPasswordNueva: boolean = false;


  constructor(private authService: AuthService, private http: HttpClient) {}

  ngOnInit(): void {
    this.authService.getPerfil().subscribe({
      next: data => {
        this.usuario = data;
      },
      error: err => {
        console.error('Error al cargar perfil:', err);
      }
    });

    this.cargarJuegos('gustado');
    this.cargarJuegos('pendiente');
    this.cargarJuegos('abandonado');
  }

  cargarJuegos(estado: string): void {
    this.http.get<any[]>(`http://localhost:8080/api/juegos/mis-juegos/${estado}`)
      .subscribe(juegos => {
        console.log(`📦 Juegos recibidos para estado ${estado}:`, juegos); // <-- AÑADE ESTE LOG

        if (estado === 'gustado') this.gustados = juegos;
        if (estado === 'pendiente') this.pendientes = juegos;
        if (estado === 'abandonado') this.abandonados = juegos;
      });
  }

  guardarCambios(): void {
    const datosActualizados = {
      nombreUsuario: this.usuario.nombreUsuario,
      nombre: this.usuario.nombre,
      apellidos: this.usuario.apellidos,
      telefono: this.usuario.telefono
    };

    this.authService.updatePerfil(datosActualizados).subscribe({
      next: () => {
        alert('Perfil actualizado correctamente');
      },
      error: (err) => {
        alert('Hubo un error al actualizar el perfil');
        console.error(err);
      }
    });
  }

  cambiarContrasena(): void {
    if (this.currentPassword && this.newPassword) {
      this.authService.changePassword({
        currentPassword: this.currentPassword,
        newPassword: this.newPassword
      }).subscribe({
        next: (response) => {
          alert(response.mensaje);  // Asegúrate de usar el campo "mensaje" de la respuesta
          this.currentPassword = '';
          this.newPassword = '';
        },
        error: (err) => {
          alert('Error al cambiar la contraseña');
          console.error(err);
        }
      });
    }
  }
}
