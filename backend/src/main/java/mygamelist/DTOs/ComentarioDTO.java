package mygamelist.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComentarioDTO {
    private Long juegoId;
    private String comentario;

    public Long getJuegoId() {
        return juegoId;
    }

    public void setJuegoId(Long juegoId) {
        this.juegoId = juegoId;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}

