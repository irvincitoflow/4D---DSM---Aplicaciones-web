package mx.edu.utez.CotizadorDeHospedaje.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/hospedaje")
public class HospedajeController {

    // Lo que llega en el JSON
    record Reservacion(String nombreHuesped, String tipoHabitacion, int numeroNoches,
                       int numeroHuespedes, String temporada,
                       boolean incluyeDesayuno, boolean incluyeEstacionamiento) {}

    @PostMapping("/cotizar")
    public ResponseEntity<?> cotizar(@RequestBody Reservacion r) {

        List<String> errores = validar(r);
        if (!errores.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("errores", errores));
        }

        String habitacion = r.tipoHabitacion().toUpperCase();
        String temporada = r.temporada().toUpperCase();

        // costo por noche según la habitación
        double costoNoche = switch (habitacion) {
            case "INDIVIDUAL" -> 700;
            case "DOBLE" -> 1100;
            default -> 1800; // SUITE
        };

        // 1. costo del hospedaje
        double costoHospedaje = costoNoche * r.numeroNoches();

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
        if (r.incluyeDesayuno()) {
            desayuno = r.numeroHuespedes() * r.numeroNoches() * 150;
        }

        // 6. estacionamiento: $100 por noche
        double estacionamiento = 0;
        if (r.incluyeEstacionamiento()) {
            estacionamiento = r.numeroNoches() * 100;
        }

        // 7. descuento de 8% por 7 noches o más (solo sobre el hospedaje)
        double descuentoEstancia = 0;
        if (r.numeroNoches() >= 7) {
            descuentoEstancia = costoHospedaje * 0.08;
        }

        // 8. subtotal e impuesto del 4%
        double subtotal = costoHospedaje - descuentoTemporada + cargoTemporada
                - descuentoEstancia + desayuno + estacionamiento;
        double impuesto = subtotal * 0.04;

        // 9. total
        double total = subtotal + impuesto;

        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("nombreHuesped", r.nombreHuesped());
        respuesta.put("tipoHabitacion", habitacion);
        respuesta.put("temporada", temporada);
        respuesta.put("costoHospedaje", redondear(costoHospedaje));
        respuesta.put("descuentoTemporada", redondear(descuentoTemporada));
        respuesta.put("cargoTemporada", redondear(cargoTemporada));
        respuesta.put("descuentoEstancia", redondear(descuentoEstancia));
        respuesta.put("desayuno", redondear(desayuno));
        respuesta.put("estacionamiento", redondear(estacionamiento));
        respuesta.put("subtotal", redondear(subtotal));
        respuesta.put("impuesto", redondear(impuesto));
        respuesta.put("total", redondear(total));

        return ResponseEntity.ok(respuesta);
    }

    private List<String> validar(Reservacion r) {
        List<String> errores = new ArrayList<>();

        if (r.nombreHuesped() == null || r.nombreHuesped().isBlank()) {
            errores.add("El nombre del huésped es obligatorio");
        }

        boolean habitacionValida = r.tipoHabitacion() != null &&
                List.of("INDIVIDUAL", "DOBLE", "SUITE").contains(r.tipoHabitacion().toUpperCase());
        if (!habitacionValida) {
            errores.add("tipoHabitacion inválido (INDIVIDUAL, DOBLE o SUITE)");
        }

        if (r.temporada() == null ||
                !List.of("BAJA", "REGULAR", "ALTA").contains(r.temporada().toUpperCase())) {
            errores.add("temporada inválida (BAJA, REGULAR o ALTA)");
        }

        if (r.numeroNoches() <= 0 || r.numeroHuespedes() <= 0) {
            errores.add("Las noches y los huéspedes deben ser mayores a 0");
        }

        // Reservaciones que no se aceptan
        if (r.numeroNoches() > 30) {
            errores.add("No se aceptan reservaciones de más de 30 noches");
        }

        if (habitacionValida) {
            String hab = r.tipoHabitacion().toUpperCase();

            if (hab.equals("INDIVIDUAL") && r.numeroHuespedes() > 1) {
                errores.add("Una habitación individual es para 1 persona");
            }
            if (hab.equals("DOBLE") && r.numeroHuespedes() > 2) {
                errores.add("Una habitación doble es para máximo 2 personas");
            }
            if (hab.equals("SUITE") && r.numeroHuespedes() > 4) {
                errores.add("Una suite es para máximo 4 personas");
            }
        }

        return errores;
    }

    private double redondear(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }
}