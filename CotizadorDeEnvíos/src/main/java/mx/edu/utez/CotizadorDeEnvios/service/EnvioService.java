package mx.edu.utez.CotizadorDeEnvios.service;

import mx.edu.utez.CotizadorDeEnvios.dto.EnvioRequestDTO;
import mx.edu.utez.CotizadorDeEnvios.dto.EnvioResponseDTO;
import mx.edu.utez.CotizadorDeEnvios.exception.ReglaNegocioException;
import org.springframework.stereotype.Service;

@Service
public class EnvioService {

    public EnvioResponseDTO cotizar(EnvioRequestDTO dto) {

        double volumen = dto.largoCm() * dto.anchoCm() * dto.altoCm();

        // paquetes que no se aceptan
        if (dto.pesoKg() > 50) {
            throw new ReglaNegocioException("No se aceptan paquetes de más de 50 kg");
        }
        if (dto.largoCm() > 150 || dto.anchoCm() > 150 || dto.altoCm() > 150) {
            throw new ReglaNegocioException("Ninguna dimensión puede superar 150 cm");
        }
        if (volumen > 1_000_000) {
            throw new ReglaNegocioException("El volumen no puede superar 1,000,000 cm³");
        }

        double costo = 80;                        // regla 1: base
        costo += 12 * dto.pesoKg();               // regla 2: $12 por kg

        if (volumen > 50000) {                    // regla 3: volumen
            costo += 100;
        }

        String tipo = dto.tipoEnvio();
        if (tipo.equals("EXPRESS")) {             // regla 4
            costo *= 1.40;
        } else if (tipo.equals("MISMO_DIA")) {    // regla 5
            costo *= 1.70;
        }

        double seguro = 0;
        if (dto.valorDeclarado() > 10000) {       // regla 6: seguro 2%
            seguro = dto.valorDeclarado() * 0.02;
            costo += seguro;
        }

        return new EnvioResponseDTO(dto.codigoPostal(), tipo, volumen,
                redondear(seguro), redondear(costo));
    }

    private double redondear(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }
}
