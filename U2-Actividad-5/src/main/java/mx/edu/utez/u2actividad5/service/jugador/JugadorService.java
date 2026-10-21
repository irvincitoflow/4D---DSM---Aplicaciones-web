package mx.edu.utez.u2actividad5.service.jugador;
import mx.edu.utez.u2actividad5.dto.jugador.RequestCreateJugadorDTO;
import mx.edu.utez.u2actividad5.exception.CustomNotFoundException;
import mx.edu.utez.u2actividad5.model.jugador.Jugador;
import mx.edu.utez.u2actividad5.repository.jugador.JugadorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JugadorService {

    private final JugadorRepository jugadorRepository;

    public JugadorService(JugadorRepository jugadorRepository) {
        this.jugadorRepository = jugadorRepository;
    }

    public Jugador crearJugador(RequestCreateJugadorDTO payload) {
        Jugador nuevo = new Jugador();
        nuevo.setNombre(payload.getNombre());
        nuevo.setSaldo(payload.getSaldo());
        nuevo.setActivo(true);
        return jugadorRepository.save(nuevo);
    }

    @Transactional
    public Jugador recargarSaldo(Long jugadorId, double monto) {
        Jugador jugador = jugadorRepository.findById(jugadorId)
                .orElseThrow(() -> new CustomNotFoundException("El jugador no existe"));

        jugador.setSaldo(jugador.getSaldo() + monto);
        return jugadorRepository.save(jugador);
    }
}