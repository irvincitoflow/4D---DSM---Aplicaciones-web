package mx.edu.utez.CotizadorDeHospedaje.dto;

public record HospedajeResponseDTO(
        String nombreHuesped,
        String tipoHabitacion,
        String temporada,
        double costoHospedaje,
        double descuentoTemporada,
        double cargoTemporada,
        double descuentoEstancia,
        double desayuno,
        double estacionamiento,
        double subtotal,
        double impuesto,
        double total
) {}
