package mx.edu.utez.proyecto1D.controller.persona.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestUpdatePersonaDTO {

    @NotBlank(message = "Los nombres son obligatorios")
    @Size(max = 50, message = "Los nombres no pueden pasar de 50 caracteres")
    private String nombres;

    @NotBlank(message = "El primer apellido es obligatorio")
    @Size(max = 50, message = "El primer apellido no puede pasar de 50 caracteres")
    private String primerApellido;

    @Size(max = 50, message = "El segundo apellido no puede pasar de 50 caracteres")
    private String segundoApellido;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo no tiene un formato valido")
    private String correo;

    @NotBlank(message = "La CURP es obligatoria")
    @Pattern(
            regexp = "^[A-Z]{4}\\d{6}[HM][A-Z]{5}[A-Z0-9]\\d$",
            message = "La CURP no tiene un formato valido (18 caracteres, en mayusculas)"
    )
    private String curp;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser una fecha pasada")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNcimiento;
}
