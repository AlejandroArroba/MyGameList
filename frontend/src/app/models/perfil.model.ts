import { JuegoRawg } from './juegoRawg.model'; // Usa el modelo que ya tienes

export interface PerfilDTO {
  nombreUsuario: string;
  email: string;
  juegos: JuegoRawg[];  // <-- usa tu interfaz Juego aquí
}
