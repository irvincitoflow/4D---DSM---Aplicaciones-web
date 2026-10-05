package mx.edu.utez.CotizadorDeRentaDeVehiculos.controllers;

import jakarta.validation.Valid;
import mx.edu.utez.CotizadorDeRentaDeVehiculos.dto.RentaRequestDTO;
import mx.edu.utez.CotizadorDeRentaDeVehiculos.dto.RentaResponseDTO;
import mx.edu.utez.CotizadorDeRentaDeVehiculos.service.RentaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/renta")
public class RentaController {

    private final RentaService rentaService;

    public RentaController(RentaService rentaService) {
        this.rentaService = rentaService;
    }

    @PostMapping("/cotizar")
    public ResponseEntity<RentaResponseDTO> cotizar(@Valid @RequestBody RentaRequestDTO dto) {
        return ResponseEntity.ok(rentaService.cotizar(dto));
    }
}
