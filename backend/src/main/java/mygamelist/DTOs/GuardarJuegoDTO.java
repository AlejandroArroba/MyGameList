package mygamelist.DTOs;

public class GuardarJuegoDTO {
    private Long usuarioId;
    private Long juegoId;
    private String estado;
    private int puntuacion;

    public GuardarJuegoDTO() {
    }

    public GuardarJuegoDTO(Long usuarioId, Long juegoId, String estado, int puntuacion) {
        this.usuarioId = usuarioId;
        this.juegoId = juegoId;
        this.estado = estado;
        this.puntuacion = puntuacion;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getJuegoId() {
        return juegoId;
    }

    public void setJuegoId(Long juegoId) {
        this.juegoId = juegoId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }
}
