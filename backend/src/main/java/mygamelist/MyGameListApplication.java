package mygamelist;

import mygamelist.service.JuegoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MyGameListApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyGameListApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(JuegoService juegoService) {
        return args -> {
            juegoService.obtenerJuegos(1);
        };
    }
}
