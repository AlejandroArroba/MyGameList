package mygamelist.service;

import mygamelist.dtos.GuardarJuegoDTO;
import mygamelist.entities.Juego;
import mygamelist.entities.Usuario;
import mygamelist.entities.UsuariosJuego;
import mygamelist.model.JuegoModel;
import mygamelist.repositories.JuegoRepository;
import mygamelist.repositories.UsuarioRepository;
import mygamelist.repositories.UsuariosJuegoRepository;
import mygamelist.response.RawgResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JuegoService {

    @Value("${rawg.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    private final String API_KEY = "b64ea3a5ed08437e988d5aac94dc23b8";

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JuegoRepository juegoRepository;

    @Autowired
    private UsuariosJuegoRepository usuariosJuegoRepository;

    public List<JuegoModel> obtenerJuegos(int page) {
        String url = "https://api.rawg.io/api/games?key=" + API_KEY + "&page=" + page + "&page_size=20";

        RawgResponse response = restTemplate.getForObject(url, RawgResponse.class);

        if (response != null && response.getResults() != null) {
            return response.getResults().stream()
                    .map(juego -> new JuegoModel(
                            juego.getId(),
                            juego.getName(),
                            juego.getBackgroundImage(),
                            juego.getReleased(),
                            juego.getRating()
                    ))
                    .toList();
        } else {
            return new ArrayList<>();
        }
    }

    public JuegoModel obtenerJuego(int id) {
        String url = "https://api.rawg.io/api/games/" + id + "?key=" + API_KEY;
        return restTemplate.getForObject(url, JuegoModel.class);
    }

    public boolean guardarJuegoUsuario(GuardarJuegoDTO dto) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(dto.getUsuarioId());
        Optional<Juego> juegoOpt = juegoRepository.findById(dto.getJuegoId());

        if (usuarioOpt.isPresent() && juegoOpt.isPresent()) {
            UsuariosJuego usuariosJuego = new UsuariosJuego();
            usuariosJuego.setUsuario(usuarioOpt.get());
            usuariosJuego.setJuego(juegoOpt.get());
            usuariosJuego.setEstado(dto.getEstado());
            usuariosJuego.setPuntuacion(dto.getPuntuacion());

            usuariosJuegoRepository.save(usuariosJuego);
            return true;
        }
        return false;
    }

    public List<JuegoModel> buscarJuegos(String search, boolean exacto) {
        String url = "https://api.rawg.io/api/games"
                + "?search=" + search
                + "&page_size=40"
                + "&key=" + API_KEY;

        RawgResponse response = restTemplate.getForObject(url, RawgResponse.class);

        if (response != null && response.getResults() != null) {
            String searchNormalizado = normalizar(search);

            return response.getResults().stream()
                    .filter(juego -> {
                        String nombreNormalizado = normalizar(juego.getName());
                        return exacto
                                ? nombreNormalizado.equals(searchNormalizado)
                                : nombreNormalizado.contains(searchNormalizado);
                    })
                    .map(juego -> new JuegoModel(
                            juego.getId(),
                            juego.getName(),
                            juego.getBackgroundImage(),
                            juego.getReleased(),
                            juego.getRating()
                    ))
                    .toList();
        } else {
            return List.of();
        }
    }

    private String normalizar(String texto) {
        if (texto == null) return null;
        return Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
                .toLowerCase();
    }
}
