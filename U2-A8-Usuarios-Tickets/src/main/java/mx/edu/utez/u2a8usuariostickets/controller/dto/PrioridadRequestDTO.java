package mx.edu.utez.u2a8usuariostickets.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import mx.edu.utez.u2a8usuariostickets.model.ticket.Prioridad;

@Getter
@Setter
public class PrioridadRequestDTO {
    @NotNull(message = "La prioridad es obligatoria")
    private Prioridad prioridad;
}
