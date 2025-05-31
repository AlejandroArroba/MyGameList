package mygamelist.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "juegos")
public class Juego {
    @Id
    @Column(name = "id_rawg", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "fecha_lanzamiento")
    private LocalDate fechaLanzamiento;

    @Size(max = 500)
    @Column(name = "imagen_url", length = 500)
    private String imagenUrl;

    @Column(name = "rating_rawg", precision = 3, scale = 1)
    private BigDecimal ratingRawg;

    @OneToMany(mappedBy = "juego")
    private Set<mygamelist.entities.UsuariosJuego> usuariosJuegos = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(LocalDate fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public BigDecimal getRatingRawg() {
        return ratingRawg;
    }

    public void setRatingRawg(BigDecimal ratingRawg) {
        this.ratingRawg = ratingRawg;
    }

    public Set<UsuariosJuego> getUsuariosJuegos() {
        return usuariosJuegos;
    }

    public void setUsuariosJuegos(Set<UsuariosJuego> usuariosJuegos) {
        this.usuariosJuegos = usuariosJuegos;
    }
}