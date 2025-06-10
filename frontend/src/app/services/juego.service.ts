import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, map, throwError} from 'rxjs';
import { Juego } from '../models/juego.model';

@Injectable({
  providedIn: 'root'
})
export class JuegoService {
  private apiUrl = 'http://localhost:8080/api/juegos';
  private rawgApiKey = 'b64ea3a5ed08437e988d5aac94dc23b8';

  constructor(private http: HttpClient) {}

  obtenerJuegos(pagina: number): Observable<Juego[]> {
    return this.http.get<any[]>(`${this.apiUrl}?page=${pagina}`).pipe(
      map((juegos: any[]) =>
        juegos.map(juego => ({
          id: juego.id,
          name: juego.name,
          released: juego.released,
          backgroundImage: juego.backgroundImage ?? juego.background_image,
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
          backgroundImage: juego.backgroundImage ?? juego.background_image,
          rating: juego.rating
        }))
      )
    );
  }

  obtenerJuego(id: number): Observable<Juego> {
    return this.http.get<Juego>(`${this.apiUrl}/${id}`);
  }

  obtenerJuegoPorId(id: number): Observable<any> {
    const url = `https://api.rawg.io/api/games/${id}?key=${this.rawgApiKey}`;
    return this.http.get<any>(url);
  }

  obtenerJuegosPorEstado(estado: string): Observable<Juego[]> {
    return this.http.get<Juego[]>(`${this.apiUrl}/mis-juegos/${estado}`);
  }
  guardarJuego(dto: any): Observable<any> {
    const token = localStorage.getItem('token');
    if (!token) {
      return throwError(() => new Error('Token no encontrado'));
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    return this.http.post('http://localhost:8080/api/juegos/guardar', dto, { headers });
  }

}
