package mx.edu.utez.u2actividad5.controller.partida;


import jakarta.validation.Valid;
import mx.edu.utez.u2actividad5.dto.partida.RequestIniciarPartidaDTO;
import mx.edu.utez.u2actividad5.model.Partida;
import mx.edu.utez.u2actividad5.model.tiro.Tiro;
import mx.edu.utez.u2actividad5.service.partida.PartidaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/partida")
public class PartidaController {

    private final PartidaService partidaService;

    public PartidaController(PartidaService partidaService) {
        this.partidaService = partidaService;
    }

    @PostMapping("/iniciar")
    public ResponseEntity<Partida> iniciarPartida(@RequestBody @Valid RequestIniciarPartidaDTO payload) {
        return ResponseEntity
                .status(201)
                .body(partidaService.iniciarPartida(payload));
    }

    @PostMapping("/{id}/tirar")
    public ResponseEntity<Tiro> realizarTiro(@PathVariable Long id) {
        return ResponseEntity
                .status(200)
                .body(partidaService.realizarTiro(id));
    }

    @PutMapping("/{id}/finalizar")
    public ResponseEntity<Partida> finalizarPartida(@PathVariable Long id) {
        return ResponseEntity
                .status(200)
                .body(partidaService.finalizarPartidaManual(id));
    }

    @GetMapping("/historial/{jugadorId}")
    public ResponseEntity<List<Partida>> obtenerHistorial(@PathVariable Long jugadorId) {
        return ResponseEntity
                .status(200)
                .body(partidaService.obtenerHistorialPartida(jugadorId));
    }
}
