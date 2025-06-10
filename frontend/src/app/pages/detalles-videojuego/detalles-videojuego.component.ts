import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JuegoService } from '../../services/juego.service';
import { CommonModule } from '@angular/common';
import {HeaderComponent} from '../../components/header/header.component'; // ✅ Import necesario para *ngIf, *ngFor

interface Plataforma {
  platform: { name: string };
}

interface JuegoDetalle {
  id: number;
  name: string;
  slug: string;
  description_raw: string; // ✅ sinopsis
  released: string;
  background_image: string;
  rating: number;
  metacritic: number;
  playtime: number;

  esrb_rating?: { name: string };
  platforms?: { platform: { name: string } }[];
  genres?: { name: string }[];
  developers?: { name: string }[];
  publishers?: { name: string }[];
}

@Component({
  selector: 'app-detalles-videojuego',
  standalone: true,
  imports: [CommonModule, HeaderComponent], // ✅ Muy importante
  templateUrl: './detalles-videojuego.component.html',
  styleUrls: ['./detalles-videojuego.component.css']
})
export class DetallesVideojuegoComponent implements OnInit {
  juego: JuegoDetalle | null = null;
  mostrarSinopsisCompleta = false;

  constructor(
    private route: ActivatedRoute,
    private juegoService: JuegoService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.juegoService.obtenerJuegoPorId(id).subscribe({
        next: (data) => this.juego = data,
        error: (err) => console.error('Error al obtener el juego:', err)
      });
    }
  }

  guardarJuego(estado: string): void {
    if (!this.juego) {
      alert('El juego aún no se ha cargado.');
      return;
    }

    const token = localStorage.getItem('token');
    if (!token) {
      alert('Debes iniciar sesión para guardar juegos.');
      return;
    }

    const dto = {
      juegoId: this.juego.id,
      nombre: this.juego.name,
      imagenUrl: this.juego.background_image,
      estado: estado,
      puntuacion: 0
    };

    // No es necesario agregar el token manualmente si usas el interceptor
    // Si usas el interceptor, esta parte se maneja automáticamente

    this.juegoService.guardarJuego(dto).subscribe({
      next: () => {
        alert(`Juego guardado como "${estado}" correctamente.`);
      },
      error: (err) => {
        console.error(err);
        alert('Error al guardar el juego.');
      }
    });
  }
}
