package mx.edu.utez.CotizadorDeEnvios.controllers;

import jakarta.validation.Valid;
import mx.edu.utez.CotizadorDeEnvios.dto.EnvioRequestDTO;
import mx.edu.utez.CotizadorDeEnvios.dto.EnvioResponseDTO;
import mx.edu.utez.CotizadorDeEnvios.service.EnvioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/envios")
public class EnvioController {

    private final EnvioService envioService;

    public EnvioController(EnvioService envioService) {
        this.envioService = envioService;
    }

    @PostMapping("/cotizar")
    public ResponseEntity<EnvioResponseDTO> cotizar(@Valid @RequestBody EnvioRequestDTO dto) {
        return ResponseEntity.ok(envioService.cotizar(dto));
    }
}
