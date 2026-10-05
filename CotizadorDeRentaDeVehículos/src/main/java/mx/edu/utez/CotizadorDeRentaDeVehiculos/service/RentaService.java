package mx.edu.utez.CotizadorDeRentaDeVehiculos.service;

import mx.edu.utez.CotizadorDeRentaDeVehiculos.dto.RentaRequestDTO;
import mx.edu.utez.CotizadorDeRentaDeVehiculos.dto.RentaResponseDTO;
import mx.edu.utez.CotizadorDeRentaDeVehiculos.exception.ReglaNegocioException;
import org.springframework.stereotype.Service;

@Service
public class RentaService {

    public RentaResponseDTO cotizar(RentaRequestDTO dto) {

        String tipo = dto.tipoVehiculo();
        int edad = dto.edadConductor();
        int dias = dto.diasRenta();

        // rentas que no se aceptan
        if (edad < 18) {
            throw new ReglaNegocioException("El conductor debe ser mayor de edad");
        }
        if (dias > 30) {
            throw new ReglaNegocioException("La renta no puede superar 30 días");
        }
        if (dto.kilometrosEstimados() > 5000) {
            throw new ReglaNegocioException("Los kilómetros estimados no pueden superar 5,000");
        }
        if (tipo.equals("CAMIONETA") && edad < 25) {
            throw new ReglaNegocioException("Para rentar una CAMIONETA el conductor debe tener 25 años o más");
        }

        // costo diario según el vehículo
        double costoDiario = switch (tipo) {
            case "COMPACTO" -> 550;
            case "SEDAN" -> 700;
            case "SUV" -> 950;
            default -> 1200; // CAMIONETA
        };

        // 1. costo de la renta
        double costoRenta = costoDiario * dias;

        // 2 y 3. kilómetros incluidos y adicionales
        double kmIncluidos = dias * 100;
        double kmAdicionales = Math.max(0, dto.kilometrosEstimados() - kmIncluidos);
        double cargoKm = kmAdicionales * 4;

        // 4. cargo por edad (18 a 24 años): 15% de renta + km adicionales
        double cargoEdad = 0;
        if (edad >= 18 && edad <= 24) {
            cargoEdad = (costoRenta + cargoKm) * 0.15;
        }

        // 5. seguro completo: $180 por día
        double seguro = 0;
        if (dto.seguroCompleto()) {
            seguro = 180 * dias;
        }

        // 6. descuento 10% solo sobre el costo de la renta (7 días o más)
        double descuento = 0;
        if (dias >= 7) {
            descuento = costoRenta * 0.10;
        }

        double total = costoRenta - descuento + cargoKm + cargoEdad + seguro;

        return new RentaResponseDTO(dto.nombreCliente(), tipo,
                redondear(costoRenta), redondear(descuento), kmAdicionales,
                redondear(cargoKm), redondear(cargoEdad), redondear(seguro),
                redondear(total));
    }

    private double redondear(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }
}
