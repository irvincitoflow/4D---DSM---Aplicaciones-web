package mx.edu.utez.proyecto1D.controller.dto;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class RequestBodyDTO {
    @NotBlank (message = "El nombre es obligatorio")
    @Size (min = 3, message = "El nombre debe de tener al menos 3 caracteres")
    private String nombre;

    @Min(value = 18, message = "La edad debe ser de al menos 18")
    private int edad;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo no es valido")
    private String correo;

    @NotBlank(message = "El curp es obligatorio")
    @Pattern(
            regexp = "^[A-Z][AEIOUX][A-Z]{2}\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])[HM][A-Z]{2}[B-DF-HJ-NP-TV-Z]{3}[A-Z0-9]\\d$",
            message = "La CURP no tiene un formato válido"
    )
    private String curp;
}