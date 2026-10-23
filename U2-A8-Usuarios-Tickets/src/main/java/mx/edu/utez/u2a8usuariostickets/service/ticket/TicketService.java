package mx.edu.utez.u2a8usuariostickets.service.ticket;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.u2a8usuariostickets.controller.dto.PrioridadRequestDTO;
import mx.edu.utez.u2a8usuariostickets.controller.dto.TicketRequestDTO;
import mx.edu.utez.u2a8usuariostickets.exception.RecursoNoEncontradoException;
import mx.edu.utez.u2a8usuariostickets.exception.TicketCerradoException;
import mx.edu.utez.u2a8usuariostickets.exception.UsuarioInactivoException;
import mx.edu.utez.u2a8usuariostickets.model.ticket.Estado;
import mx.edu.utez.u2a8usuariostickets.model.ticket.Ticket;
import mx.edu.utez.u2a8usuariostickets.model.usuario.Usuario;
import mx.edu.utez.u2a8usuariostickets.repository.ticket.TicketRepository;
import mx.edu.utez.u2a8usuariostickets.repository.usuario.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final UsuarioRepository usuarioRepository;

    // 25 pts
    public Ticket crearTicket(TicketRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RecursoNoEncontradoException("El usuario no existe"));

        if (!Boolean.TRUE.equals(usuario.getIsEnable())) {
            throw new UsuarioInactivoException("El usuario está inactivo");
        }

        Ticket ticket = new Ticket();
        ticket.setDescricion(dto.getDescripcion());
        ticket.setEstado(Estado.ABIERTO);
        ticket.setPrioridad(dto.getPrioridad());
        ticket.setUsuario(usuario);
        return ticketRepository.save(ticket);
    }

    // 25 pts
    public Ticket actualizarEstado(Long id) {
        Ticket ticket = buscarTicket(id);

        switch (ticket.getEstado()) {
            case ABIERTO -> ticket.setEstado(Estado.EN_PROCESO);
            case EN_PROCESO -> ticket.setEstado(Estado.CERRADO);
            case CERRADO -> throw new TicketCerradoException("El ticket está cerrado y no se puede modificar");
        }
        return ticketRepository.save(ticket);
    }

    // 20 pts
    public Ticket reasignarPrioridad(Long id, PrioridadRequestDTO dto) {
        Ticket ticket = buscarTicket(id);

        if (ticket.getEstado() == Estado.CERRADO) {
            throw new TicketCerradoException("No se puede cambiar la prioridad de un ticket cerrado");
        }
        ticket.setPrioridad(dto.getPrioridad());
        return ticketRepository.save(ticket);
    }

    // 10 pts
    public List<Ticket> obtenerTicketsUsuario(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new RecursoNoEncontradoException("El usuario no existe");
        }
        return ticketRepository.findByUsuarioId(usuarioId);
    }

    private Ticket buscarTicket(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("El ticket no existe"));
    }
}
