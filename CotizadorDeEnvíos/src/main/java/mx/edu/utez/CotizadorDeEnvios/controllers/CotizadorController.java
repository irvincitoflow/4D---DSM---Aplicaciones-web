package mx.edu.utez.CotizadorDeEnvios.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/envios")
public class CotizadorController {

    // Lo que llega en el JSON
    record Paquete(String codigoPostal, double pesoKg, double largoCm,
                   double anchoCm, double altoCm, String tipoEnvio,
                   double valorDeclarado) {}

    @PostMapping("/cotizar")
    public ResponseEntity<?> cotizar(@RequestBody Paquete p) {

        List<String> errores = validar(p);
        if (!errores.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("errores", errores));
        }

        double volumen = p.largoCm() * p.anchoCm() * p.altoCm();

        double costo = 80;                       // regla 1: base
        costo += 12 * p.pesoKg();                // regla 2: $12 por kg

        if (volumen > 50000) {                   // regla 3: volumen
            costo += 100;
        }

        String tipo = p.tipoEnvio().toUpperCase();
        if (tipo.equals("EXPRESS")) {            // regla 4
            costo *= 1.40;
        } else if (tipo.equals("MISMO_DIA")) {   // regla 5
            costo *= 1.70;
        }

        double seguro = 0;
        if (p.valorDeclarado() > 10000) {        // regla 6: seguro 2%
            seguro = p.valorDeclarado() * 0.02;
            costo += seguro;
        }

        costo = Math.round(costo * 100.0) / 100.0;

        return ResponseEntity.ok(Map.of(
                "codigoPostal", p.codigoPostal(),
                "tipoEnvio", tipo,
                "volumenCm3", volumen,
                "seguro", seguro,
                "costoTotal", costo
        ));
    }

    private List<String> validar(Paquete p) {
        List<String> errores = new ArrayList<>();

        if (p.codigoPostal() == null || !p.codigoPostal().matches("\\d{5}")) {
            errores.add("El código postal debe tener 5 dígitos");
        }

        if (p.tipoEnvio() == null ||
                !List.of("ESTANDAR", "EXPRESS", "MISMO_DIA")
                        .contains(p.tipoEnvio().toUpperCase())) {
            errores.add("tipoEnvio inválido (ESTANDAR, EXPRESS o MISMO_DIA)");
        }

        if (p.pesoKg() <= 0 || p.largoCm() <= 0 || p.anchoCm() <= 0
                || p.altoCm() <= 0 || p.valorDeclarado() < 0) {
            errores.add("Peso y dimensiones deben ser mayores a 0 y el valor declarado no puede ser negativo");
        }

        if (p.pesoKg() > 50) {
            errores.add("No se aceptan paquetes de más de 50 kg");
        }

        if (p.largoCm() > 150 || p.anchoCm() > 150 || p.altoCm() > 150) {
            errores.add("Ninguna dimensión puede superar 150 cm");
        }

        if (p.largoCm() * p.anchoCm() * p.altoCm() > 1_000_000) {
            errores.add("El volumen no puede superar 1,000,000 cm³");
        }

        return errores;
    }
}