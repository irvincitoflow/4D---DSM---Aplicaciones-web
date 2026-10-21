package mx.edu.utez.proyecto1D.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseCalculadoraDTO {
    private int resultado;
    private String operacionRealizada;

}
