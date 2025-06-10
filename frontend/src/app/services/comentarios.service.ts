import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Comentario {
  nombreUsuario: string;
  comentario: string;
  fecha: string;
}

@Injectable({ providedIn: 'root' })
export class ComentariosService {
  private apiUrl = 'http://localhost:8080/api/comentarios';

  constructor(private http: HttpClient) {}

  obtenerComentariosPorJuego(juegoId: number): Observable<Comentario[]> {
    return this.http.get<Comentario[]>(`${this.apiUrl}/${juegoId}`);
  }

  agregarComentario(juegoId: number, comentario: string): Observable<Comentario> {
    return this.http.post<Comentario>(`${this.apiUrl}`, { juegoId, comentario });
  }
}
