package mygamelist.dtos;

public class GuardarJuegoDTO {
    private Integer juegoId;
    private String estado;
    private Integer puntuacion;
    private String nombre;
    private String imagenUrl;

    public GuardarJuegoDTO() { }

    public GuardarJuegoDTO(String nombre, String imagenUrl) {
        this.nombre = nombre;
        this.imagenUrl = imagenUrl;
    }

    public GuardarJuegoDTO(Integer juegoId, String estado, Integer puntuacion, String nombre, String imagenUrl) {
        this.juegoId = juegoId;
        this.estado = estado;
        this.puntuacion = puntuacion;
        this.nombre = nombre;
        this.imagenUrl = imagenUrl;
    }

    public Integer getJuegoId() {
        return juegoId;
    }

    public void setJuegoId(Integer juegoId) {
        this.juegoId = juegoId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }
}
