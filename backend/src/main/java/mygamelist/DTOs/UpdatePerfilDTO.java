package mygamelist.dtos;

public class UpdatePerfilDTO {
    private String nombreUsuario;
    private String nombre;
    private String apellidos;
    private String telefono;

    public String getNombreUsuario() {return nombreUsuario;}

    public void setNombreUsuario(String nombreUsuario) {this.nombreUsuario = nombreUsuario;}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
