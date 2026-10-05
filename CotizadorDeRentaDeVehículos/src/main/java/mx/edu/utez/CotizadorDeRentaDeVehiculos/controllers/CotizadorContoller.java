package mx.edu.utez.CotizadorDeRentaDeVehiculos.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/renta")
public class CotizadorContoller {

    // Lo que llega en el JSON
    record Renta(String nombreCliente, int edadConductor, String tipoVehiculo,
                 int diasRenta, double kilometrosEstimados, boolean seguroCompleto) {}

    @PostMapping("/cotizar")
    public ResponseEntity<?> cotizar(@RequestBody Renta r) {

        List<String> errores = validar(r);
        if (!errores.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("errores", errores));
        }

        String tipo = r.tipoVehiculo().toUpperCase();

        // costo diario según el vehículo
        double costoDiario = switch (tipo) {
            case "COMPACTO" -> 550;
            case "SEDAN" -> 700;
            case "SUV" -> 950;
            default -> 1200; // CAMIONETA
        };

        // 1. costo de la renta
        double costoRenta = costoDiario * r.diasRenta();

        // 2 y 3. kilómetros incluidos y adicionales
        double kmIncluidos = r.diasRenta() * 100;
        double kmAdicionales = Math.max(0, r.kilometrosEstimados() - kmIncluidos);
        double cargoKm = kmAdicionales * 4;

        // 4. cargo por edad (18 a 24 años): 15% de renta + km adicionales
        double cargoEdad = 0;
        if (r.edadConductor() >= 18 && r.edadConductor() <= 24) {
            cargoEdad = (costoRenta + cargoKm) * 0.15;
        }

        // 5. seguro completo: $180 por día
        double seguro = 0;
        if (r.seguroCompleto()) {
            seguro = 180 * r.diasRenta();
        }

        // 6. descuento 10% solo sobre el costo de la renta (7 días o más)
        double descuento = 0;
        if (r.diasRenta() >= 7) {
            descuento = costoRenta * 0.10;
        }

        double total = costoRenta - descuento + cargoKm + cargoEdad + seguro;

        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("nombreCliente", r.nombreCliente());
        respuesta.put("tipoVehiculo", tipo);
        respuesta.put("costoRenta", redondear(costoRenta));
        respuesta.put("descuento", redondear(descuento));
        respuesta.put("kmAdicionales", kmAdicionales);
        respuesta.put("cargoKmAdicionales", redondear(cargoKm));
        respuesta.put("cargoPorEdad", redondear(cargoEdad));
        respuesta.put("seguro", redondear(seguro));
        respuesta.put("totalAPagar", redondear(total));

        return ResponseEntity.ok(respuesta);
    }

    private List<String> validar(Renta r) {
        List<String> errores = new ArrayList<>();

        if (r.nombreCliente() == null || r.nombreCliente().isBlank()) {
            errores.add("El nombre del cliente es obligatorio");
        }

        if (r.tipoVehiculo() == null ||
                !List.of("COMPACTO", "SEDAN", "SUV", "CAMIONETA")
                        .contains(r.tipoVehiculo().toUpperCase())) {
            errores.add("tipoVehiculo inválido (COMPACTO, SEDAN, SUV o CAMIONETA)");
        }

        if (r.diasRenta() <= 0 || r.kilometrosEstimados() < 0) {
            errores.add("Los días deben ser mayores a 0 y los kilómetros no pueden ser negativos");
        }

        // Rentas que no se aceptan
        if (r.edadConductor() < 18) {
            errores.add("El conductor debe ser mayor de edad");
        }

        if (r.diasRenta() > 30) {
            errores.add("La renta no puede superar 30 días");
        }

        if (r.kilometrosEstimados() > 5000) {
            errores.add("Los kilómetros estimados no pueden superar 5,000");
        }

        if (r.tipoVehiculo() != null && r.tipoVehiculo().equalsIgnoreCase("CAMIONETA")
                && r.edadConductor() < 25) {
            errores.add("Para rentar una CAMIONETA el conductor debe tener 25 años o más");
        }

        return errores;
    }

    private double redondear(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }
}