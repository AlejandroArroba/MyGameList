package mygamelist.Controller;

import mygamelist.DTOs.GuardarJuegoDTO;
import mygamelist.Model.JuegoModel;
import mygamelist.Service.JuegoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/juegos")
public class JuegoController {

    @Autowired
    private JuegoService juegoService;

    @GetMapping
    public List<JuegoModel> obtenerJuegos() {
        return juegoService.obtenerJuegos();
    }

    @GetMapping("/{id}")
    public JuegoModel obtenerJuego(@PathVariable int id) {
        return juegoService.obtenerJuego(id);
    }

    @PostMapping("/guardar")
    public ResponseEntity<String> guardarJuego(@RequestBody GuardarJuegoDTO dto) {
        boolean guardado = juegoService.guardarJuegoUsuario(dto);
        if (guardado) {
            return ResponseEntity.ok("Juego guardado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al guardar el juego");
        }
    }

}
