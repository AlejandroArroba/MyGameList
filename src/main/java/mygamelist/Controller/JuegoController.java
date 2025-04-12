package mygamelist.Controller;

import mygamelist.Model.Juego;
import mygamelist.Service.JuegoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import reactor.core.publisher.Mono;

import java.util.List;

@Controller
public class JuegoController {

    private final JuegoService juegoService;

    public JuegoController(JuegoService juegoService) {
        this.juegoService = juegoService;
    }

    @GetMapping("/")
    public Mono<String> listarJuegos(Model model) {
        return juegoService.obtenerJuego()
                .doOnNext(juegos -> model.addAttribute("juegos", juegos))
                .thenReturn("index");}
}
