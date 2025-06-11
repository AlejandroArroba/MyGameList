import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { JuegoService } from '../../services/juego.service';
import { JuegoRawg } from '../../models/juegoRawg.model';
import { HeaderComponent } from '../header/header.component';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-videojuegos',
  standalone: true,
  imports: [CommonModule, FormsModule, HeaderComponent, RouterLink],
  templateUrl: './videojuegos.component.html',
  styleUrls: ['./videojuegos.component.css']
})
export class VideojuegosComponent implements OnInit {
  juegos: JuegoRawg[] = [];
  busqueda: string = '';
  paginaActual: number = 1;
  totalPaginas: number = 30;
  paginas: number[] = [];

  constructor(private juegoService: JuegoService) {}

  ngOnInit(): void {
    this.obtenerJuegos(this.paginaActual);
  }

  generarPaginas(): void {
    const visiblePages = 5;
    const half = Math.floor(visiblePages / 2);

    let start = this.paginaActual - half;
    let end = this.paginaActual + half;

    if (start < 1) {
      end += 1 - start;
      start = 1;
    }

    if (end > this.totalPaginas) {
      start -= end - this.totalPaginas;
      end = this.totalPaginas;
      if (start < 1) start = 1;
    }

    this.paginas = Array.from({ length: end - start + 1 }, (_, i) => start + i);
  }

  obtenerJuegos(pagina: number): void {
    this.juegoService.obtenerJuegosDesdeRawg(pagina).subscribe({
      next: (data) => {
        this.juegos = data;
        this.paginaActual = pagina;
        this.generarPaginas();
      },
      error: (err) => console.error('Error al cargar juegos', err)
    });
  }

  irAPagina(pagina: number): void {
    this.obtenerJuegos(pagina);
  }

  siguientePagina(): void {
    if (this.paginaActual < this.totalPaginas) {
      this.obtenerJuegos(this.paginaActual + 1);
    }
  }

  anteriorPagina(): void {
    if (this.paginaActual > 1) {
      this.obtenerJuegos(this.paginaActual - 1);
    }
  }

  buscarJuegos(): void {
    const query = this.busqueda.trim();
    if (query === '') {
      this.obtenerJuegos(1);
    } else {
      this.juegoService.buscarJuegosEnRawg(query, false).subscribe({
        next: (data) => {
          this.juegos = data;
          this.totalPaginas = 1;
          this.paginaActual = 1;
          this.paginas = [1];
        },
        error: (err) => console.error('Error al buscar juegos', err)
      });
    }
  }

  buscarExacto(): void {
    const query = this.busqueda.trim();
    if (query !== '') {
      this.juegoService.buscarJuegosEnRawg(query, true).subscribe({
        next: (data) => {
          this.juegos = data;
          this.totalPaginas = 1;
          this.paginaActual = 1;
          this.paginas = [1];
        },
        error: (err) => console.error('Error al buscar exacto', err)
      });
    }
  }

  trackJuego(index: number, juego: JuegoRawg): number {
    return juego.id;
  }
}
