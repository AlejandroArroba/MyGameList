import { Juego } from './juegoRawg.model'; // Usa el modelo que ya tienes

export interface PerfilDTO {
  nombreUsuario: string;
  email: string;
  juegos: Juego[];  // <-- usa tu interfaz Juego aquí
}
