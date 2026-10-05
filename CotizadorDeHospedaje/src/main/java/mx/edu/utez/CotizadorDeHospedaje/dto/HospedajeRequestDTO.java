package mx.edu.utez.CotizadorDeHospedaje.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record HospedajeRequestDTO(

        @NotBlank(message = "El nombre del huésped es obligatorio")
        String nombreHuesped,

        @NotBlank(message = "El tipo de habitación es obligatorio")
        @Pattern(regexp = "INDIVIDUAL|DOBLE|SUITE",
                message = "El tipo de habitación debe ser INDIVIDUAL, DOBLE o SUITE")
        String tipoHabitacion,

        @NotNull(message = "El número de noches es obligatorio")
        @Positive(message = "El número de noches debe ser mayor a 0")
        Integer numeroNoches,

        @NotNull(message = "El número de huéspedes es obligatorio")
        @Positive(message = "El número de huéspedes debe ser mayor a 0")
        Integer numeroHuespedes,

        @NotBlank(message = "La temporada es obligatoria")
        @Pattern(regexp = "BAJA|REGULAR|ALTA",
                message = "La temporada debe ser BAJA, REGULAR o ALTA")
        String temporada,

        @NotNull(message = "Debes indicar si incluye desayuno")
        Boolean incluyeDesayuno,

        @NotNull(message = "Debes indicar si incluye estacionamiento")
        Boolean incluyeEstacionamiento
) {}
