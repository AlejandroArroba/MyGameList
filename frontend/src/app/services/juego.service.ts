import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Juego {
  id: number;
  name: string;
  released: string;
  background_image: string;
  rating: number;
}

@Injectable({
  providedIn: 'root'
})
export class JuegoService {

  private baseUrl = 'http://localhost:8080/api/juegos';

  constructor(private http: HttpClient) {}

  obtenerJuegos(): Observable<Juego[]> {
    return this.http.get<Juego[]>(this.baseUrl);
  }
}
