package mx.edu.utez.u2a9prestamos_y_libros.controller.lector.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LectorDTO {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private boolean membresiaPremium;
}
