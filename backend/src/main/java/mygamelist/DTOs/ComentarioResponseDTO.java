package mygamelist.dtos;

import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
public class ComentarioResponseDTO {
    private String nombreUsuario;
    private String comentario;
    private LocalDateTime fecha;

    public ComentarioResponseDTO(String nombreUsuario, String comentario, LocalDateTime fecha) {
        this.nombreUsuario = nombreUsuario;
        this.comentario = comentario;
        this.fecha = fecha;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
