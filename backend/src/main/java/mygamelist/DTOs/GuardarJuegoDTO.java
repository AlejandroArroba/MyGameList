package mygamelist.dtos;

public class GuardarJuegoDTO {
    private Integer juegoId;
    private String estado;
    private int puntuacion;
    private String nombre;
    private String imagenUrl;

    public GuardarJuegoDTO(String nombre, String imagenUrl) {
        this.nombre = nombre;
        this.imagenUrl = imagenUrl;
    }

    public GuardarJuegoDTO(Integer juegoId, String estado, int puntuacion, String nombre, String imagenUrl) {
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

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getnombre() {
        return nombre;
    }

    public void setnombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }
}
