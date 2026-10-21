package mx.edu.utez.u2actividad5.controller.jugador;


import jakarta.validation.Valid;
import mx.edu.utez.u2actividad5.dto.jugador.RequestCreateJugadorDTO;
import mx.edu.utez.u2actividad5.dto.tiro.RequestRecargarSaldoDTO;
import mx.edu.utez.u2actividad5.model.jugador.Jugador;
import mx.edu.utez.u2actividad5.service.jugador.JugadorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/jugador")
public class JugadorController {

    private final JugadorService jugadorService;

    public JugadorController(JugadorService jugadorService) {
        this.jugadorService = jugadorService;
    }

    @PostMapping
    public ResponseEntity<Jugador> crearJugador(@RequestBody @Valid RequestCreateJugadorDTO payload) {
        return ResponseEntity
                .status(201)
                .body(jugadorService.crearJugador(payload));
    }

    @PutMapping("/{id}/recargar")
    public ResponseEntity<Jugador> recargarSaldo(@PathVariable Long id,
                                                 @RequestBody @Valid RequestRecargarSaldoDTO payload) {
        return ResponseEntity
                .status(200)
                .body(jugadorService.recargarSaldo(id, payload.getMonto()));
    }
}