package mx.edu.utez.u2actividad5.service.partida;

import mx.edu.utez.u2actividad5.dto.partida.RequestIniciarPartidaDTO;
import mx.edu.utez.u2actividad5.exception.CustomBadRequestException;
import mx.edu.utez.u2actividad5.exception.CustomNotFoundException;
import mx.edu.utez.u2actividad5.model.partida.EstadoPartida;
import mx.edu.utez.u2actividad5.model.jugador.Jugador;
import mx.edu.utez.u2actividad5.model.Partida;
import mx.edu.utez.u2actividad5.model.tiro.Tiro;
import mx.edu.utez.u2actividad5.repository.jugador.JugadorRepository;
import mx.edu.utez.u2actividad5.repository.partida.PartidaRepository;
import mx.edu.utez.u2actividad5.repository.tiro.TiroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class PartidaService {

    private final PartidaRepository partidaRepository;
    private final JugadorRepository jugadorRepository;
    private final TiroRepository tiroRepository;
    private final Random random = new Random();

    public PartidaService(PartidaRepository partidaRepository,
                          JugadorRepository jugadorRepository,
                          TiroRepository tiroRepository) {
        this.partidaRepository = partidaRepository;
        this.jugadorRepository = jugadorRepository;
        this.tiroRepository = tiroRepository;
    }

    @Transactional
    public Partida iniciarPartida(RequestIniciarPartidaDTO payload) {
        // validaciones
        Jugador jugador = jugadorRepository.findById(payload.getJugadorId())
                .orElseThrow(() -> new CustomNotFoundException("El jugador no existe"));

        if (!jugador.isActivo()) {
            throw new CustomBadRequestException("El jugador no está activo");
        }
        if (jugador.getSaldo() < payload.getApuesta()) {
            throw new CustomBadRequestException("Saldo insuficiente para realizar la apuesta");
        }

        // descontar apuesta
        jugador.setSaldo(jugador.getSaldo() - payload.getApuesta());
        jugadorRepository.save(jugador);

        // crear partida
        Partida partida = new Partida();
        partida.setJugador(jugador);
        partida.setApuesta(payload.getApuesta());
        partida.setFecha(LocalDateTime.now());
        partida.setEstado(EstadoPartida.EN_JUEGO);
        return partidaRepository.save(partida);
    }

    @Transactional
    public Tiro realizarTiro(Long partidaId) {
        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(() -> new CustomNotFoundException("La partida no existe"));

        if (partida.getEstado() != EstadoPartida.EN_JUEGO) {
            throw new CustomBadRequestException("La partida no está en juego");
        }

        // generar dados
        int dado1 = random.nextInt(6) + 1;
        int dado2 = random.nextInt(6) + 1;
        int suma = dado1 + dado2;
        boolean esGanador = false;

        if (suma == 7 || suma == 11) {
            // gana: finaliza y se paga el doble de la apuesta
            esGanador = true;
            partida.setEstado(EstadoPartida.FINALIZADA);
            Jugador jugador = partida.getJugador();
            jugador.setSaldo(jugador.getSaldo() + (partida.getApuesta() * 2));
            jugadorRepository.save(jugador);
            partidaRepository.save(partida);
        } else if (suma == 2 || suma == 3 || suma == 12) {
            // pierde: solo finaliza
            partida.setEstado(EstadoPartida.FINALIZADA);
            partidaRepository.save(partida);
        }
        // cualquier otra suma: la partida continúa

        // guardar el tiro como último paso
        Tiro tiro = new Tiro();
        tiro.setPartida(partida);
        tiro.setValorDado1(dado1);
        tiro.setValorDado2(dado2);
        tiro.setSuma(suma);
        tiro.setEsGanador(esGanador);
        return tiroRepository.save(tiro);
    }

    @Transactional
    public Partida finalizarPartidaManual(Long partidaId) {
        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(() -> new CustomNotFoundException("La partida no existe"));

        if (partida.getEstado() == EstadoPartida.FINALIZADA) {
            throw new CustomBadRequestException("La partida ya está finalizada");
        }

        partida.setEstado(EstadoPartida.FINALIZADA);
        return partidaRepository.save(partida);
    }

    public List<Partida> obtenerHistorialPartida(Long jugadorId) {
        if (!jugadorRepository.existsById(jugadorId)) {
            throw new CustomNotFoundException("El jugador no existe");
        }
        return partidaRepository.findByJugadorId(jugadorId);
    }
}