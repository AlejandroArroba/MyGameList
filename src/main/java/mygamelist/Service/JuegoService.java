package mygamelist.Service;

import mygamelist.Model.Juego;
import mygamelist.Response.RawgResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class JuegoService {

    private final WebClient webClient;

    public JuegoService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<List<Juego>> obtenerJuego() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/games")
                        .queryParam("key", "b64ea3a5ed08437e988d5aac94dc23b8")
                        .build())
                .retrieve().onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> {
                        System.out.println("Error en la solicitud: " + clientResponse.statusCode());
                        return Mono.error(new RuntimeException("Error al obtener los juegos"));
                        })
                .bodyToMono(RawgResponse.class)
                .doOnError(e -> {
                    System.out.println("Error al mapear la respuesta de Rawg: " + e.getMessage());
                })
                .map(RawgResponse::getResult)
                .onErrorResume(e -> Mono.empty());

    }

}
