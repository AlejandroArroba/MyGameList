package mygamelist.controller;

import mygamelist.dtos.GuardarJuegoDTO;
import mygamelist.entities.Usuario;
import mygamelist.entities.UsuariosJuego;
import mygamelist.model.JuegoModel;
import mygamelist.repositories.UsuarioRepository;
import mygamelist.repositories.UsuariosJuegoRepository;
import mygamelist.service.JuegoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/juegos")
public class JuegoController {

    @Autowired
    private UsuariosJuegoRepository usuariosJuegoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JuegoService juegoService;

    @GetMapping
    public List<JuegoModel> obtenerJuegos(@RequestParam(defaultValue = "1") int page) {
        return juegoService.obtenerJuegos(page);
    }

    @GetMapping("/{id}")
    public JuegoModel obtenerJuego(@PathVariable int id) {
        return juegoService.obtenerJuego(id);
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> guardarJuego(@RequestBody GuardarJuegoDTO dto, Principal principal) {
        System.out.println("Estado recibido: '" + dto.getEstado() + "'");
        String emailUsuario = principal.getName();
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario).orElse(null);

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Usuario no autenticado"));
        }

        try {
            juegoService.guardarJuegoUsuario(dto, usuario);

            return ResponseEntity.ok(Map.of("mensaje", "Juego guardado correctamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/buscar")
    public List<JuegoModel> buscarJuegos(@RequestParam String query, @RequestParam(defaultValue = "false") boolean exacto) {
        return juegoService.buscarJuegos(query, exacto);
    }

    @GetMapping("/mis-juegos/{estado}")
    public List<JuegoModel> obtenerJuegosPorEstado(@PathVariable String estado) {
        String emailUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario).orElseThrow();

        List<UsuariosJuego> relaciones = usuariosJuegoRepository.findByUsuarioAndEstado(usuario, estado);

        return relaciones.stream()
                .map(rel -> new JuegoModel(
                        rel.getJuego().getId(),
                        rel.getJuego().getNombre(),
                        rel.getJuego().getImagenUrl(),
                        rel.getJuego().getFechaLanzamiento() != null ? rel.getJuego().getFechaLanzamiento().toString() : null,
                        rel.getJuego().getRatingRawg() != null ? rel.getJuego().getRatingRawg().doubleValue() : 0.0
                ))
                .toList();
    }
}
