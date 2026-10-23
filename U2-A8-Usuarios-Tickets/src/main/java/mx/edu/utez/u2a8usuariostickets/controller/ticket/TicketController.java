package mx.edu.utez.u2a8usuariostickets.controller.ticket;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mx.edu.utez.u2a8usuariostickets.controller.dto.PrioridadRequestDTO;
import mx.edu.utez.u2a8usuariostickets.controller.dto.TicketRequestDTO;
import mx.edu.utez.u2a8usuariostickets.model.ticket.Ticket;
import mx.edu.utez.u2a8usuariostickets.service.ticket.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<Ticket> crear(@Valid @RequestBody TicketRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.crearTicket(dto));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Ticket> actualizarEstado(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.actualizarEstado(id));
    }

    @PutMapping("/{id}/prioridad")
    public ResponseEntity<Ticket> reasignarPrioridad(@PathVariable Long id, @Valid @RequestBody PrioridadRequestDTO dto) {
        return ResponseEntity.ok(ticketService.reasignarPrioridad(id, dto));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Ticket>> ticketsDeUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(ticketService.obtenerTicketsUsuario(usuarioId));
    }
}
