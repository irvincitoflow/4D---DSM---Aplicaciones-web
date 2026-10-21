package mx.edu.utez.u2actividad5.repository.jugador;

import mx.edu.utez.u2actividad5.model.jugador.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Long> {
}