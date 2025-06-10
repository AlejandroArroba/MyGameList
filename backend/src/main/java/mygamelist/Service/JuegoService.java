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
import java.util.*;

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

    public Map<String, Object> obtenerDetallesJuego(int id) {
        String url = "https://api.rawg.io/api/games/" + id + "?key=" + API_KEY;
        return restTemplate.getForObject(url, Map.class);
    }

    public JuegoModel obtenerJuego(Integer id) {  // ← pon Integer aquí
        String url = "https://api.rawg.io/api/games/" + id + "?key=" + API_KEY;
        return restTemplate.getForObject(url, JuegoModel.class);
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

    public List<JuegoModel> obtenerJuegosPorEstado(String emailUsuario, String estado) {
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario).orElseThrow();
        List<UsuariosJuego> relaciones = usuariosJuegoRepository.findByUsuarioAndEstado(usuario, estado);
        return relaciones.stream()
                .map(rel -> obtenerJuego(rel.getJuego().getId()))
                .toList();
    }

    public boolean guardarJuegoUsuarioDesdeEmail(String email, Long juegoId, String estado, int puntuacion) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        Optional<Juego> juegoOpt = juegoRepository.findById(juegoId.intValue());  // 👈 aquí conviertes Long → int

        Juego juego;

        if (juegoOpt.isPresent()) {
            juego = juegoOpt.get();
        } else {
            Juego nuevoJuego = new Juego();
            nuevoJuego.setId(juegoId.intValue());  // 👈 tu campo id es Integer
            nuevoJuego.setNombre("Nombre temporal");  // luego lo puedes actualizar si quieres
            nuevoJuego.setImagenUrl("");  // también opcional

            juego = juegoRepository.save(nuevoJuego);
        }

        if (usuarioOpt.isPresent()) {
            UsuariosJuego usuariosJuego = new UsuariosJuego();
            usuariosJuego.setUsuario(usuarioOpt.get());
            usuariosJuego.setJuego(juego);
            usuariosJuego.setEstado(estado);
            usuariosJuego.setPuntuacion(puntuacion);

            usuariosJuegoRepository.save(usuariosJuego);
            return true;
        }

        return false;
    }
}
