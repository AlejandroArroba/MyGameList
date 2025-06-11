import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, map, throwError } from 'rxjs';
import { Juego } from '../models/juego.model';          // Para juegos de tu base de datos
import { JuegoRawg } from '../models/juegoRawg.model';  // Para juegos de RAWG

@Injectable({
  providedIn: 'root'
})
export class JuegoService {
  private apiUrl = 'http://localhost:8080/api/juegos';
  private rawgApiKey = 'b64ea3a5ed08437e988d5aac94dc23b8';

  constructor(private http: HttpClient) {}

  // ✅ Obtener juegos de RAWG (para VideojuegosComponent)
  obtenerJuegosDesdeRawg(pagina: number): Observable<JuegoRawg[]> {
    return this.http.get<any>(`https://api.rawg.io/api/games?key=${this.rawgApiKey}&page=${pagina}`).pipe(
      map((response: any) =>
        response.results.map((juego: any) => ({
          id: juego.id,
          name: juego.name,
          released: juego.released,
          background_image: juego.background_image,
          rating: juego.rating
        }))
      )
    );
  }

  // ✅ Buscar juegos en RAWG
  buscarJuegosEnRawg(query: string, exacto: boolean = false): Observable<JuegoRawg[]> {
    const searchUrl = `https://api.rawg.io/api/games?key=${this.rawgApiKey}&search=${encodeURIComponent(query)}&search_exact=${exacto}`;
    return this.http.get<any>(searchUrl).pipe(
      map((response: any) =>
        response.results.map((juego: any) => ({
          id: juego.id,
          name: juego.name,
          released: juego.released,
          background_image: juego.background_image,
          rating: juego.rating
        }))
      )
    );
  }

  // 🔹 Juegos guardados del usuario (tu base de datos)
  obtenerJuegosPorEstado(estado: string): Observable<Juego[]> {
    return this.http.get<Juego[]>(`${this.apiUrl}/mis-juegos/${estado}`);
  }

  // 🔹 Juego guardado en BD (por id_rawg)
  obtenerJuego(id: number): Observable<Juego> {
    return this.http.get<Juego>(`${this.apiUrl}/${id}`);
  }

  // 🔹 Guardar juego en la BD
  guardarJuego(dto: any): Observable<any> {
    const token = localStorage.getItem('token');
    if (!token) {
      return throwError(() => new Error('Token no encontrado'));
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    return this.http.post('http://localhost:8080/api/juegos/guardar', dto, { headers });
  }

  obtenerJuegoPorId(id: number): Observable<any> {
    const url = `https://api.rawg.io/api/games/${id}?key=${this.rawgApiKey}`;
    return this.http.get<any>(url);
  }
}
