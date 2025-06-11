package mygamelist.repositories;

import mygamelist.entities.Usuario;
import mygamelist.entities.Juego;
import mygamelist.entities.UsuariosJuego;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuariosJuegoRepository extends JpaRepository<UsuariosJuego, Long> {
    List<UsuariosJuego> findByUsuarioAndEstado(Usuario usuario, String estado);
    boolean existsByUsuarioAndJuegoAndEstado(Usuario usuario, Juego juego, String estado);
    List<UsuariosJuego> findByUsuarioAndJuegoAndEstado(Usuario usuario, Juego juego, String estado);
    Optional<UsuariosJuego> findByUsuarioAndJuego(Usuario usuario, Juego juego);
    Optional<UsuariosJuego> findByUsuarioIdAndJuegoId(Long usuarioId, Integer juegoId);
    List<UsuariosJuego> findByUsuario(Usuario usuario);
}
