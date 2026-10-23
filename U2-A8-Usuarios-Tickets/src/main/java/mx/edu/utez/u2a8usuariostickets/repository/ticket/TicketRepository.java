package mx.edu.utez.u2a8usuariostickets.repository.ticket;

import mx.edu.utez.u2a8usuariostickets.model.ticket.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {
    List<Ticket> findByUsuarioId(Long usuarioId);
}
