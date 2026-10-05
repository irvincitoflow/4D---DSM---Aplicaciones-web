package mx.edu.utez.CotizadorDeHospedaje.controllers;

import jakarta.validation.Valid;
import mx.edu.utez.CotizadorDeHospedaje.dto.HospedajeRequestDTO;
import mx.edu.utez.CotizadorDeHospedaje.dto.HospedajeResponseDTO;
import mx.edu.utez.CotizadorDeHospedaje.service.HospedajeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hospedaje")
public class HospedajeController {

    private final HospedajeService hospedajeService;

    public HospedajeController(HospedajeService hospedajeService) {
        this.hospedajeService = hospedajeService;
    }

    @PostMapping("/cotizar")
    public ResponseEntity<HospedajeResponseDTO> cotizar(@Valid @RequestBody HospedajeRequestDTO dto) {
        return ResponseEntity.ok(hospedajeService.cotizar(dto));
    }
}
