import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { HeaderComponent } from '../../components/header/header.component';

@Component({
  standalone: true,
  selector: 'app-perfil',
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.css'],
  imports: [CommonModule, FormsModule, HeaderComponent]
})
export class PerfilComponent implements OnInit {
  usuario: any = null;

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    this.authService.getPerfil().subscribe({
      next: data => {
        this.usuario = data;
      },
      error: err => {
        console.error('Error al cargar perfil:', err);
      }
    });
  }


  guardarCambios(): void {
    const datosActualizados = {
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
}
