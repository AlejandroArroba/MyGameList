package mygamelist.Service;

import mygamelist.DTOs.GuardarJuegoDTO;
import mygamelist.Entities.Juego;
import mygamelist.Entities.Usuario;
import mygamelist.Entities.UsuariosJuego;
import mygamelist.Model.JuegoModel;
import mygamelist.Repositories.JuegoRepository;
import mygamelist.Repositories.UsuarioRepository;
import mygamelist.Repositories.UsuariosJuegoRepository;
import mygamelist.Response.RawgResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    public List<JuegoModel> obtenerJuegos() {
        String url = "https://api.rawg.io/api/games?key=" + API_KEY;

        RawgResponse response = restTemplate.getForObject(url, RawgResponse.class);

        if (response != null && response.getResults() != null) {
            return response.getResults(); // devolvemos los juegos
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
}
