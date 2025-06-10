package mygamelist.repositories;

import mygamelist.entities.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

    @Query("SELECT c FROM Comentario c JOIN FETCH c.usuario WHERE c.juegoId = :juegoId")
    List<Comentario> findByJuegoId(@Param("juegoId") Integer juegoId);

}
