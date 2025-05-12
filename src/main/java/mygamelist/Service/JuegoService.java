package mygamelist.Service;

import mygamelist.Model.Juego;
import mygamelist.Response.RawgResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class JuegoService {

    @Value("${rawg.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    private final String API_KEY = "b64ea3a5ed08437e988d5aac94dc23b8";

    public List<Juego> obtenerJuegos() {
        String url = "https://api.rawg.io/api/games?key=" + API_KEY;

        RawgResponse response = restTemplate.getForObject(url, RawgResponse.class);

        if (response != null && response.getResults() != null) {
            return response.getResults(); // devolvemos los juegos
        } else {
            return new ArrayList<>();
        }
    }

    public Juego obtenerJuego(int id) {
        String url = "https://api.rawg.io/api/games/" + id + "?key=" + API_KEY;
        return restTemplate.getForObject(url, Juego.class);
    }
}
