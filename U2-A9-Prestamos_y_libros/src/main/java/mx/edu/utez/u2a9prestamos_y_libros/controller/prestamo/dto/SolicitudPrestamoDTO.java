package mx.edu.utez.u2a9prestamos_y_libros.controller.prestamo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SolicitudPrestamoDTO {
    @NotNull(message = "El id del lector es obligatorio")
    private Long lectorId;
    @NotNull(message = "El id del libro es obligatorio")
    private Long libroId;
}
