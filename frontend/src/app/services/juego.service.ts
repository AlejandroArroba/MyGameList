import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { Juego } from '../models/juego.model';


@Injectable({
  providedIn: 'root'
})
export class JuegoService {
  private apiUrl = 'http://localhost:8080/api/juegos';

  constructor(private http: HttpClient) {}

  obtenerJuegos(pagina: number): Observable<Juego[]> {
    return this.http.get<any[]>(`${this.apiUrl}?page=${pagina}`).pipe(
      map((juegos: any[]) =>
        juegos.map(juego => ({
          id: juego.id,
          name: juego.name,
          released: juego.released,
          backgroundImage: juego.backgroundImage ?? juego.background_image, // ⚠️ acepta ambos
          rating: juego.rating
        }))
      )
    );
  }

  buscarJuegos(query: string, exacto: boolean = false): Observable<Juego[]> {
    return this.http.get<any[]>(`${this.apiUrl}/buscar?query=${encodeURIComponent(query)}&exacto=${exacto}`).pipe(
      map((juegos: any[]) =>
        juegos.map(juego => ({
          id: juego.id,
          name: juego.name,
          released: juego.released,
          backgroundImage: juego.backgroundImage ?? juego.background_image, // ⚠️ igual aquí
          rating: juego.rating
        }))
      )
    );
  }
}
