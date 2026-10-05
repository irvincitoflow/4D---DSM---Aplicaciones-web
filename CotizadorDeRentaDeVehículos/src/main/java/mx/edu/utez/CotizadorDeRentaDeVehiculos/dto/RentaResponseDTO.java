package mx.edu.utez.CotizadorDeRentaDeVehiculos.dto;

public record RentaResponseDTO(
        String nombreCliente,
        String tipoVehiculo,
        double costoRenta,
        double descuento,
        double kmAdicionales,
        double cargoKmAdicionales,
        double cargoPorEdad,
        double seguro,
        double totalAPagar
) {}
