package mygamelist.response;

import lombok.AllArgsConstructor;

public class AuthResponse {
    private String token;
    private String nombreUsuario;
    private String email;
    private String rol;

    public AuthResponse() {
    }

    public AuthResponse(String token, String nombreUsuario, String email, String rol) {
        this.token = token;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.rol = rol;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
