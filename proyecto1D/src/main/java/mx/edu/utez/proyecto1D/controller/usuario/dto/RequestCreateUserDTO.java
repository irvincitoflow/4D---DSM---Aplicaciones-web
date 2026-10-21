package mx.edu.utez.proyecto1D.controller.usuario.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
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
public class RequestCreateUserDTO {
    @NotBlank(message = "El username es obligatorio")
    @Size(min=8, max =20, message = "El username debe contener entre 6 y 20 caracteres")
    private String username;

    @NotBlank(message = "Password es obligatorio")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d).{8,}$",
            message = "La contraseña debe tener al menos 8 caracteres, una mayúscula y un número"
    )
    private String password;

    private String descripcion;

    @NotBlank(message = "El rol es obligatorio")
    @Pattern(
            regexp = "^(ADMIN|PROFESOR|ESTUDIANTE)$",
            message = "El rol debe ser ADMIN, PROFESOR o ESTUDIANTE"
    )
    private String rol;
    //DATOS DE LA PERSONA
    private String nombres;
    private String primerApellido;
    private String segundoApellido;
    private String correo;
    private String curp;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNcimiento;

}
