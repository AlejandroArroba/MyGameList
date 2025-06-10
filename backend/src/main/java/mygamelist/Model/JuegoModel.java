package mygamelist.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import mygamelist.entities.Juego;

public class JuegoModel {

    private int id;
    private String name;
    private String released;
    private String backgroundImage;
    private double rating;

    public JuegoModel() {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public JuegoModel(int id, String name, String backgroundImage, String released, double rating) {
        this.id = id;
        this.name = name;
        this.backgroundImage = backgroundImage;
        this.released = released;
        this.rating = rating;
    }

    public JuegoModel(Juego juego) {
        this.id = juego.getId();
        this.name = juego.getNombre();
        this.backgroundImage = juego.getImagenUrl();
        this.released = juego.getFechaLanzamiento() != null ? juego.getFechaLanzamiento().toString() : null;
        this.rating = juego.getRatingRawg() != null ? juego.getRatingRawg().doubleValue() : 0.0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String background_image) {
        this.backgroundImage = background_image;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
