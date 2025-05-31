package mygamelist.controller;

import mygamelist.dtos.UpdatePerfilDTO;
import mygamelist.entities.Usuario;
import mygamelist.repositories.UsuarioRepository;
import mygamelist.response.AuthResponse;
import mygamelist.response.LoginRequest;
import mygamelist.response.RegistroRequest;
import mygamelist.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    @Autowired
    private UsuarioRepository usuarioRepository;


    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegistroRequest registro) {
        return ResponseEntity.ok(authService.register(registro));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest login) {
        return ResponseEntity.ok(authService.login(login));
    }

    @GetMapping("/test")
    public String test() {
        return "funciona";
    }

    @PutMapping("/perfil")
    public ResponseEntity<?> actualizarPerfil(
            @RequestBody UpdatePerfilDTO dto,
            Principal principal) {

        String emailUsuario = principal.getName();
        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(emailUsuario);

        if (optionalUsuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        Usuario usuario = optionalUsuario.get();

        // Solo actualizamos campos permitidos
        usuario.setNombre(dto.getNombre());
        usuario.setApellidos(dto.getApellidos());
        usuario.setTelefono(dto.getTelefono());

        usuarioRepository.save(usuario);

        return ResponseEntity.ok("Perfil actualizado correctamente");
    }


}
