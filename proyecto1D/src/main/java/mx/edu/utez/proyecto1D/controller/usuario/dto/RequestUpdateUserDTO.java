package mx.edu.utez.proyecto1D.controller.usuario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestUpdateUserDTO {

    @NotBlank(message = "El username es obligatorio")
    @Size(min = 8, max = 20, message = "El username debe contener entre 8 y 20 caracteres")
    private String username;

    @Size(max = 500, message = "La descripcion no puede pasar de 500 caracteres")
    private String descripcion;
}
