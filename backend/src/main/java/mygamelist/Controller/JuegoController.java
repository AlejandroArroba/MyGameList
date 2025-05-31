package mygamelist.controller;

import mygamelist.dtos.GuardarJuegoDTO;
import mygamelist.model.JuegoModel;
import mygamelist.service.JuegoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/juegos")
public class JuegoController {

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
    public ResponseEntity<String> guardarJuego(@RequestBody GuardarJuegoDTO dto) {
        boolean guardado = juegoService.guardarJuegoUsuario(dto);
        if (guardado) {
            return ResponseEntity.ok("Juego guardado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al guardar el juego");
        }
    }

    @GetMapping("/buscar")
    public List<JuegoModel> buscarJuegos(@RequestParam String query, @RequestParam(defaultValue = "false") boolean exacto) {
        return juegoService.buscarJuegos(query, exacto);
    }

}
