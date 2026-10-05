package mx.edu.utez.CotizadorDeHospedaje.service;

import mx.edu.utez.CotizadorDeHospedaje.dto.HospedajeRequestDTO;
import mx.edu.utez.CotizadorDeHospedaje.dto.HospedajeResponseDTO;
import mx.edu.utez.CotizadorDeHospedaje.exception.ReglaNegocioException;
import org.springframework.stereotype.Service;

@Service
public class HospedajeService {

    public HospedajeResponseDTO cotizar(HospedajeRequestDTO dto) {

        String habitacion = dto.tipoHabitacion();
        String temporada = dto.temporada();
        int noches = dto.numeroNoches();
        int huespedes = dto.numeroHuespedes();

        // reservaciones que no se aceptan
        if (noches > 30) {
            throw new ReglaNegocioException("No se aceptan reservaciones de más de 30 noches");
        }
        if (habitacion.equals("INDIVIDUAL") && huespedes > 1) {
            throw new ReglaNegocioException("Una habitación individual es para 1 persona");
        }
        if (habitacion.equals("DOBLE") && huespedes > 2) {
            throw new ReglaNegocioException("Una habitación doble es para máximo 2 personas");
        }
        if (habitacion.equals("SUITE") && huespedes > 4) {
            throw new ReglaNegocioException("Una suite es para máximo 4 personas");
        }

        // costo por noche según la habitación
        double costoNoche = switch (habitacion) {
            case "INDIVIDUAL" -> 700;
            case "DOBLE" -> 1100;
            default -> 1800; // SUITE
        };

        // 1. costo del hospedaje
        double costoHospedaje = costoNoche * noches;

        // 2, 3 y 4. ajuste por temporada
        double descuentoTemporada = 0;
        double cargoTemporada = 0;
        if (temporada.equals("BAJA")) {
            descuentoTemporada = costoHospedaje * 0.10;
        } else if (temporada.equals("ALTA")) {
            cargoTemporada = costoHospedaje * 0.25;
        }
        // REGULAR no lleva ni descuento ni cargo por temporada

        // 5. desayuno: $150 por huésped y por noche
        double desayuno = 0;
        if (dto.incluyeDesayuno()) {
            desayuno = huespedes * noches * 150;
        }

        // 6. estacionamiento: $100 por noche
        double estacionamiento = 0;
        if (dto.incluyeEstacionamiento()) {
            estacionamiento = noches * 100;
        }

        // 7. descuento de 8% por 7 noches o más (solo sobre el hospedaje)
        double descuentoEstancia = 0;
        if (noches >= 7) {
            descuentoEstancia = costoHospedaje * 0.08;
        }

        // 8. subtotal e impuesto del 4%
        double subtotal = costoHospedaje - descuentoTemporada + cargoTemporada
                - descuentoEstancia + desayuno + estacionamiento;
        double impuesto = subtotal * 0.04;

        // 9. total
        double total = subtotal + impuesto;

        return new HospedajeResponseDTO(dto.nombreHuesped(), habitacion, temporada,
                redondear(costoHospedaje), redondear(descuentoTemporada),
                redondear(cargoTemporada), redondear(descuentoEstancia),
                redondear(desayuno), redondear(estacionamiento),
                redondear(subtotal), redondear(impuesto), redondear(total));
    }

    private double redondear(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }
}
