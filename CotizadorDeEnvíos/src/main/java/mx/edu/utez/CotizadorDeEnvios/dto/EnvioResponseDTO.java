package mx.edu.utez.CotizadorDeEnvios.dto;

public record EnvioResponseDTO(
        String codigoPostal,
        String tipoEnvio,
        double volumenCm3,
        double seguro,
        double costoTotal
) {}
