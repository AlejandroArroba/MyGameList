package mygamelist.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RawgJuego {

    private int id;
    private String name;

    @JsonProperty("released")
    private String released;

    @JsonProperty("background_image")  // Este mapea correctamente la imagen
    private String backgroundImage;

    private double rating;

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getReleased() { return released; }
    public void setReleased(String released) { this.released = released; }

    public String getBackgroundImage() { return backgroundImage; }
    public void setBackgroundImage(String backgroundImage) { this.backgroundImage = backgroundImage; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
}
