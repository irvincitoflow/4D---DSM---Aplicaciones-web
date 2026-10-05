package mx.edu.utez.CotizadorDeRentaDeVehiculos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record RentaRequestDTO(

        @NotBlank(message = "El nombre del cliente es obligatorio")
        String nombreCliente,

        @NotNull(message = "La edad del conductor es obligatoria")
        @Positive(message = "La edad debe ser mayor a 0")
        Integer edadConductor,

        @NotBlank(message = "El tipo de vehículo es obligatorio")
        @Pattern(regexp = "COMPACTO|SEDAN|SUV|CAMIONETA",
                message = "El tipo de vehículo debe ser COMPACTO, SEDAN, SUV o CAMIONETA")
        String tipoVehiculo,

        @NotNull(message = "Los días de renta son obligatorios")
        @Positive(message = "Los días de renta deben ser mayores a 0")
        Integer diasRenta,

        @NotNull(message = "Los kilómetros estimados son obligatorios")
        @PositiveOrZero(message = "Los kilómetros no pueden ser negativos")
        Double kilometrosEstimados,

        @NotNull(message = "Debes indicar si contrata el seguro completo")
        Boolean seguroCompleto
) {}
