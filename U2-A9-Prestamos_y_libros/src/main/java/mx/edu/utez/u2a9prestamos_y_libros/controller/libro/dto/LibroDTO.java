package mx.edu.utez.u2a9prestamos_y_libros.controller.libro.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LibroDTO {
    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @Min(value = 0, message = "Los ejemplares no pueden ser negativos")
    private int ejemplaresDisponibles;
}