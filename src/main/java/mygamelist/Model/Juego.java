package mygamelist.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class Juego {

    private int id;
    private String name;
    private String released;
    private String background_image;
    private double rating;

    public Juego() {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public Juego(int id, String name, String released, String background_image, double rating) {
        this.id = id;
        this.name = name;
        this.released = released;
        this.background_image = background_image;
        this.rating = rating;
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

    public String getBackground_image() {
        return background_image;
    }

    public void setBackground_image(String background_image) {
        this.background_image = background_image;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
