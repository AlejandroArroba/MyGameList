package mygamelist.dtos;

import mygamelist.model.JuegoModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PerfilDTO {
    private String nombreUsuario;
    private String email;
    private List<JuegoModel> gustados;
    private List<JuegoModel> pendientes;
    private List<JuegoModel> abandonados;

    public PerfilDTO(String nombreUsuario, String email, List<JuegoModel> gustados, List<JuegoModel> pendientes, List<JuegoModel> abandonados) {
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.gustados = gustados;
        this.pendientes = pendientes;
        this.abandonados = abandonados;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<JuegoModel> getGustados() {
        return gustados;
    }

    public void setGustados(List<JuegoModel> gustados) {
        this.gustados = gustados;
    }

    public List<JuegoModel> getPendientes() {
        return pendientes;
    }

    public void setPendientes(List<JuegoModel> pendientes) {
        this.pendientes = pendientes;
    }

    public List<JuegoModel> getAbandonados() {
        return abandonados;
    }

    public void setAbandonados(List<JuegoModel> abandonados) {
        this.abandonados = abandonados;
    }
}
