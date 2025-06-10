package mygamelist.controller;

import mygamelist.dtos.ComentarioDTO;
import mygamelist.dtos.ComentarioResponseDTO;
import mygamelist.entities.Comentario;
import mygamelist.entities.Usuario;
import mygamelist.repositories.ComentarioRepository;
import mygamelist.repositories.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/comentarios")
public class ComentariosController {

    private final ComentarioRepository comentarioRepository;
    private final UsuarioRepository usuarioRepository;

    public ComentariosController(ComentarioRepository comentarioRepository, UsuarioRepository usuarioRepository) {
        this.comentarioRepository = comentarioRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/{juegoId}")
    public List<ComentarioResponseDTO> obtenerComentarios(@PathVariable Long juegoId) {
        List<Comentario> comentarios = comentarioRepository.findByJuegoId(juegoId.intValue());
        List<ComentarioResponseDTO> response = new ArrayList<>();

        for (Comentario comentario : comentarios) {
            String nombreUsuario = comentario.getUsuario().getNombreUsuario();

            response.add(new ComentarioResponseDTO(
                    nombreUsuario,
                    comentario.getComentario(),
                    comentario.getFecha()
            ));
        }

        return response;
    }

    @PostMapping
    public ResponseEntity<?> agregarComentario(
            @RequestBody ComentarioDTO dto,
            @AuthenticationPrincipal UserDetails userDetails) {

        Usuario usuario = usuarioRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // VALIDAMOS QUE SEA PREMIUM
        if (!usuario.getRol().equalsIgnoreCase("PREMIUM")) {
            return ResponseEntity.status(403).body("Solo los usuarios PREMIUM pueden comentar.");
        }

        Comentario comentario = new Comentario();
        comentario.setJuegoId(dto.getJuegoId().intValue());
        comentario.setComentario(dto.getComentario());
        comentario.setFecha(LocalDateTime.now());
        comentario.setUsuario(usuario);

        comentarioRepository.save(comentario);

        // Opcional: devolver el DTO del comentario creado
        ComentarioResponseDTO response = new ComentarioResponseDTO(
                usuario.getNombreUsuario(),
                comentario.getComentario(),
                comentario.getFecha()
        );

        return ResponseEntity.ok(response);
    }
}
