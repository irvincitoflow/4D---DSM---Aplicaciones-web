package mx.edu.utez.CotizadorDeEnvios.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record EnvioRequestDTO(

        @NotBlank(message = "El código postal es obligatorio")
        @Pattern(regexp = "\\d{5}", message = "El código postal debe tener 5 dígitos")
        String codigoPostal,

        @NotNull(message = "El peso es obligatorio")
        @Positive(message = "El peso debe ser mayor a 0")
        Double pesoKg,

        @NotNull(message = "El largo es obligatorio")
        @Positive(message = "El largo debe ser mayor a 0")
        Double largoCm,

        @NotNull(message = "El ancho es obligatorio")
        @Positive(message = "El ancho debe ser mayor a 0")
        Double anchoCm,

        @NotNull(message = "El alto es obligatorio")
        @Positive(message = "El alto debe ser mayor a 0")
        Double altoCm,

        @NotBlank(message = "El tipo de envío es obligatorio")
        @Pattern(regexp = "ESTANDAR|EXPRESS|MISMO_DIA",
                message = "El tipo de envío debe ser ESTANDAR, EXPRESS o MISMO_DIA")
        String tipoEnvio,

        @NotNull(message = "El valor declarado es obligatorio")
        @PositiveOrZero(message = "El valor declarado no puede ser negativo")
        Double valorDeclarado
) {}
