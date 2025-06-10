package mygamelist.controller;

import mygamelist.dtos.UpdatePerfilDTO;
import mygamelist.entities.Usuario;
import mygamelist.repositories.UsuarioRepository;
import mygamelist.response.AuthResponse;
import mygamelist.response.LoginRequest;
import mygamelist.response.RegistroRequest;
import mygamelist.service.AuthService;
import mygamelist.service.JwtService;  // Asegúrate de importar el JwtService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;  // Asegúrate de declarar el JwtService

    @Autowired
    public AuthController(AuthService authService,
                          UsuarioRepository usuarioRepository,
                          PasswordEncoder passwordEncoder,
                          JwtService jwtService) {  // Asegúrate de inyectar el JwtService
        this.authService = authService;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;  // Inicializa el JwtService
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegistroRequest registro) {
        if (registro.getRol() == null || registro.getRol().isEmpty()) {
            registro.setRol("USER");
        }

        // Creamos el nuevo usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombreUsuario(registro.getNombreUsuario());
        nuevoUsuario.setEmail(registro.getEmail());

        // Encriptamos la contraseña antes de guardarla
        nuevoUsuario.setPassword(passwordEncoder.encode(registro.getContrasena()));

        nuevoUsuario.setRol(registro.getRol());

        usuarioRepository.save(nuevoUsuario);

        String token = jwtService.generateToken(nuevoUsuario); // Se genera el token
        return ResponseEntity.ok(new AuthResponse(token, nuevoUsuario.getNombreUsuario(), nuevoUsuario.getEmail(), nuevoUsuario.getRol()));
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
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Usuario no encontrado"));
        }

        Usuario usuario = optionalUsuario.get();

        if (!usuario.getNombreUsuario().equalsIgnoreCase(dto.getNombreUsuario()) &&
                usuarioRepository.existsByNombreUsuario(dto.getNombreUsuario())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("mensaje", "El nombre de usuario ya está en uso"));
        }

        usuario.setNombreUsuario(dto.getNombreUsuario());
        usuario.setNombre(dto.getNombre());
        usuario.setApellidos(dto.getApellidos());
        usuario.setTelefono(dto.getTelefono());

        usuarioRepository.save(usuario);

        return ResponseEntity
                .ok(Map.of("mensaje", "Perfil actualizado correctamente"));
    }

    @GetMapping("/perfil")
    public ResponseEntity<Usuario> obtenerPerfil(Principal principal) {
        String email = principal.getName();
        return usuarioRepository.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        // Verificar si el usuario existe
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        // Obtener el usuario
        Usuario usuario = usuarioOpt.get();

        // Generar una nueva contraseña aleatoria
        String nuevaContrasena = generarContrasenaAleatoria();

        // Establecer la nueva contraseña en el usuario
        usuario.setPassword(passwordEncoder.encode(nuevaContrasena));
        usuarioRepository.save(usuario);

        // Aquí devuelves el mensaje con la nueva contraseña en texto plano
        return ResponseEntity.ok("Usuario encontrado. La nueva contraseña es: " + nuevaContrasena);
    }

    private String generarContrasenaAleatoria() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(12); // Puedes cambiar el tamaño aquí
        for (int i = 0; i < 12; i++) {
            int index = random.nextInt(caracteres.length());
            password.append(caracteres.charAt(index));
        }
        return password.toString();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String email, @RequestParam String nuevaContrasena) {
        // Buscar el usuario por su email
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        // Obtener el usuario y cambiar su contraseña
        Usuario usuario = usuarioOpt.get();
        usuario.setPassword(passwordEncoder.encode(nuevaContrasena));
        usuarioRepository.save(usuario);

        return ResponseEntity.ok("Contraseña cambiada correctamente");
    }

    @PutMapping("/perfil/cambiar-contraseña")
    public ResponseEntity<Map<String, String>> cambiarContrasena(@RequestBody Map<String, String> passwords, Principal principal) {
        String currentPassword = passwords.get("currentPassword");
        String newPassword = passwords.get("newPassword");

        // Obtener el usuario desde el email
        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(principal.getName());
        if (optionalUsuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("mensaje", "Usuario no encontrado"));
        }

        Usuario usuario = optionalUsuario.get();

        // Verificar que la contraseña actual sea correcta
        if (!passwordEncoder.matches(currentPassword, usuario.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("mensaje", "Contraseña actual incorrecta"));
        }

        // Encriptar y actualizar la nueva contraseña
        usuario.setPassword(passwordEncoder.encode(newPassword)); // Aquí se usa el setter
        usuarioRepository.save(usuario);

        return ResponseEntity.ok(Map.of("mensaje", "Contraseña cambiada con éxito"));
    }
}
