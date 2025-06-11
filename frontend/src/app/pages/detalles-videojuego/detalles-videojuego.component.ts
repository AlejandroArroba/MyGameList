import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JuegoService } from '../../services/juego.service';
import { Comentario, ComentariosService } from '../../services/comentarios.service';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from '../header/header.component';
import { FormsModule } from '@angular/forms';

interface JuegoDetalle {
  id: number;
  name: string;
  slug: string;
  description_raw: string;
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
  imports: [CommonModule, HeaderComponent, FormsModule],
  templateUrl: './detalles-videojuego.component.html',
  styleUrls: ['./detalles-videojuego.component.css']
})
export class DetallesVideojuegoComponent implements OnInit {
  juego: JuegoDetalle | null = null;
  mostrarSinopsisCompleta = false;

  comentarios: any[] = [];
  nuevoComentario: string = '';
  cargandoComentarios = false;
  esPremium = false;  // NUEVO

  constructor(
    private route: ActivatedRoute,
    private juegoService: JuegoService,
    private comentariosService: ComentariosService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.juegoService.obtenerJuegoPorId(id).subscribe({
        next: (data) => this.juego = data,
        error: (err) => console.error('Error al obtener el juego:', err)
      });
      this.cargarComentarios(id);
    }

    this.comprobarSiEsPremium();  // NUEVO
  }

  comprobarSiEsPremium(): void {
    const token = localStorage.getItem('token');
    if (!token) {
      this.esPremium = false;
      return;
    }

    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      this.esPremium = payload.rol === 'PREMIUM';
    } catch (e) {
      console.error('Error al decodificar el token:', e);
      this.esPremium = false;
    }
  }

  cargarComentarios(juegoId: number): void {
    this.cargandoComentarios = true;
    this.comentariosService.obtenerComentariosPorJuego(juegoId).subscribe({
      next: (comentarios: Comentario[]) => {
        this.comentarios = comentarios;
        this.cargandoComentarios = false;
      },
      error: (err: any) => {
        console.error('Error al cargar comentarios', err);
        this.cargandoComentarios = false;
      }
    });
  }

  enviarComentario(): void {
    if (!this.juego || !this.nuevoComentario.trim()) return;

    this.comentariosService.agregarComentario(this.juego.id, this.nuevoComentario).subscribe({
      next: (comentarioGuardado: Comentario) => {
        this.comentarios.unshift(comentarioGuardado);
        this.nuevoComentario = '';
      },
      error: (err: any) => alert('Error al enviar comentario')
    });
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
      puntuacion: null
    };
    this.juegoService.guardarJuego(dto).subscribe({
      next: () => alert(`Juego guardado como "${estado}" correctamente.`),
      error: (err) => {
        console.error(err);
        alert('Error al guardar el juego.');
      }
    });
  }
}
