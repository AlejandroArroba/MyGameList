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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.time.Instant;
import java.time.LocalDate;
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

        System.out.println("🟡 Estado: " + estado + " → juegos encontrados: " + relaciones.size());

        return relaciones.stream()
                .map(rel -> {
                    Juego juego = rel.getJuego();
                    System.out.println("🎮 Accediendo a juego: " + juego.getNombre());
                    return new JuegoModel(
                            juego.getId(),
                            juego.getNombre(),
                            juego.getImagenUrl(),
                            juego.getFechaLanzamiento() != null ? juego.getFechaLanzamiento().toString() : null,
                            juego.getRatingRawg() != null ? juego.getRatingRawg().doubleValue() : 0.0
                    );
                })
                .toList();
    }

    public void guardarJuegoUsuario(GuardarJuegoDTO dto, Usuario usuario) {
        // Buscar juego en BD
        Juego juego = juegoRepository.findById(dto.getJuegoId()).orElse(null);

        // Si no existe en BD → obtenerlo desde RAWG y guardarlo
        if (juego == null) {
            try {
                String url = "https://api.rawg.io/api/games/" + dto.getJuegoId() + "?key=b64ea3a5ed08437e988d5aac94dc23b8";
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
                System.out.println("Respuesta RAWG para juego ID: " + dto.getJuegoId() + " → " + response.getBody());


                if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                    Map body = response.getBody();
                    Juego nuevo = new Juego();
                    nuevo.setId(dto.getJuegoId());

                    Object nombreObj = body.get("name");
                    nuevo.setNombre(nombreObj != null ? nombreObj.toString() : "Juego sin nombre");

                    Object imagenObj = body.get("background_image");
                    nuevo.setImagenUrl(imagenObj != null ? imagenObj.toString() : "");

                    String fecha = (String) body.get("released");
                    if (fecha != null && !fecha.isEmpty()) {
                        nuevo.setFechaLanzamiento(LocalDate.parse(fecha));
                    }

                    Double rating = body.get("rating") instanceof Number ? ((Number) body.get("rating")).doubleValue() : 0;
                    nuevo.setRatingRawg(BigDecimal.valueOf(rating));

                    juego = juegoRepository.save(nuevo);

                } else {
                    // No se pudieron obtener los datos del juego → abortar
                    throw new RuntimeException("No se pudieron obtener los datos del juego desde RAWG");
                }

            } catch (Exception e) {
                e.printStackTrace();
                // Abortar también si hubo excepción en la llamada
                throw new RuntimeException("Error al obtener los datos del juego desde RAWG", e);
            }
        }

        // Comprobar si ya existe relación usuario-juego (sin importar estado)
        Optional<UsuariosJuego> existente = usuariosJuegoRepository.findByUsuarioAndJuego(usuario, juego);

        if (existente.isPresent()) {
            UsuariosJuego ujExistente = existente.get();

            if (ujExistente.getEstado().equals(dto.getEstado())) {
                // Mismo estado → error
                throw new RuntimeException("El juego ya estaba en la lista para ese estado");
            } else {
                // Cambiar el estado y la puntuación → UPDATE
                ujExistente.setEstado(dto.getEstado());
                ujExistente.setPuntuacion(dto.getPuntuacion());
                ujExistente.setFechaAgregado(Instant.now());  // o LocalDate.now() según tu campo

                usuariosJuegoRepository.save(ujExistente);
                return; // Fin
            }
        }

        // Si no existía → guardar nuevo
        UsuariosJuego uj = new UsuariosJuego();
        uj.setJuego(juego);
        uj.setUsuario(usuario);
        uj.setEstado(dto.getEstado());
        uj.setPuntuacion(dto.getPuntuacion());
        uj.setFechaAgregado(Instant.now());  // o LocalDate.now() según tu campo

        usuariosJuegoRepository.save(uj);
    }

    public List<JuegoModel> obtenerTodosLosJuegosDeUsuario(String emailUsuario) {
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario).orElseThrow();
        List<UsuariosJuego> relaciones = usuariosJuegoRepository.findByUsuario(usuario);

        return relaciones.stream()
                .map(rel -> {
                    Juego juego = rel.getJuego();
                    return new JuegoModel(
                            juego.getId(),
                            juego.getNombre(),
                            juego.getImagenUrl(),
                            juego.getFechaLanzamiento() != null ? juego.getFechaLanzamiento().toString() : null,
                            juego.getRatingRawg() != null ? juego.getRatingRawg().doubleValue() : 0.0
                    );
                })
                .toList();
    }

    public List<JuegoModel> obtenerJuegosDeUsuario(Usuario usuario) {
        List<UsuariosJuego> relaciones = usuariosJuegoRepository.findByUsuario(usuario);

        return relaciones.stream()
                .map(rel -> {
                    Juego juego = rel.getJuego();
                    return new JuegoModel(
                            juego.getId(),
                            juego.getNombre(),
                            juego.getImagenUrl(),
                            juego.getFechaLanzamiento() != null ? juego.getFechaLanzamiento().toString() : null,
                            juego.getRatingRawg() != null ? juego.getRatingRawg().doubleValue() : 0.0
                    );
                })
                .toList();
    }
}
