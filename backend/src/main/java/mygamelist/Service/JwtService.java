package mygamelist.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import mygamelist.entities.Usuario;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    // Cambia esta clave por una segura en producción (de al menos 256 bits)
    private static final String SECRET_KEY = "estaesunaclaveultrasecretaparajwtqueesdemoparadesarrollo";

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(Usuario usuario) {
        return Jwts.builder()
                .setSubject(usuario.getEmail()) // puedes usar id o nombreUsuario si prefieres
                .claim("rol", usuario.getRol())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 horas
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, Usuario usuario) {
        final String username = extractUsername(token);
        return (username.equals(usuario.getEmail())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateRecoveryToken(Usuario usuario) {
        return Jwts.builder()
                .setSubject(usuario.getEmail()) // Email como el sujeto del token
                .setIssuedAt(new Date(System.currentTimeMillis())) // Fecha de emisión
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15)) // Token válido por 15 minutos
                .claim("recovery", true) // Indicamos que es un token de recuperación
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Firma con la clave secreta
                .compact();
    }
}
