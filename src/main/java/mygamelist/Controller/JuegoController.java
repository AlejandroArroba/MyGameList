package mygamelist.Controller;

import mygamelist.Model.Juego;
import mygamelist.Service.JuegoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/juegos")
public class JuegoController {

    @Autowired
    private JuegoService juegoService;

    @GetMapping
    public List<Juego> obtenerJuegos() {
        return juegoService.obtenerJuegos();
    }

    @GetMapping("/{id}")
    public Juego obtenerJuego(@PathVariable int id) {
        return juegoService.obtenerJuego(id);
    }

}
