package mygamelist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")  // Solo habilitar CORS para rutas que comienzan con /api/
                        .allowedOrigins("http://localhost:4200") // Permitir el frontend en localhost:4200
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos permitidos
                        .allowedHeaders("Authorization", "Content-Type") // Permitir Authorization y Content-Type en los encabezados
                        .allowCredentials(true); // Permitir credenciales (cookies, tokens)
            }
        };
    }
}
