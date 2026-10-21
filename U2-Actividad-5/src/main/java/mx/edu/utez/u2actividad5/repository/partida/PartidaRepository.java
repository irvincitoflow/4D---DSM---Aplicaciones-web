package mx.edu.utez.u2actividad5.repository.partida;

import mx.edu.utez.u2actividad5.model.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartidaRepository extends JpaRepository<Partida, Long> {
    // select * from partidas where jugador_id = ?
    List<Partida> findByJugadorId(Long jugadorId);
}