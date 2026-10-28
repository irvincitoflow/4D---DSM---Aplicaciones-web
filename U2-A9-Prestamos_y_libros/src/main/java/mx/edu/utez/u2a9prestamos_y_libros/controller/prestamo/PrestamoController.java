package mx.edu.utez.u2a9prestamos_y_libros.controller.prestamo;

import jakarta.validation.Valid;
import mx.edu.utez.u2a9prestamos_y_libros.controller.prestamo.dto.SolicitudPrestamoDTO;
import mx.edu.utez.u2a9prestamos_y_libros.model.prestamo.Prestamo;
import mx.edu.utez.u2a9prestamos_y_libros.service.prestamo.PrestamoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prestamo")
@CrossOrigin("*")
public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @PostMapping
    public ResponseEntity<Prestamo> solicitar(@Valid @RequestBody SolicitudPrestamoDTO dto) {
        return ResponseEntity.status(201).body(prestamoService.solicitarPrestamo(dto));
    }

    @PutMapping("/{id}/devolver")
    public Prestamo devolver(@PathVariable Long id) {
        return prestamoService.devolverLibro(id);
    }

    @PutMapping("/{id}/renovar")
    public Prestamo renovar(@PathVariable Long id) {
        return prestamoService.renovarPrestamo(id);
    }

    @GetMapping("/lector/{id}/activos")
    public List<Prestamo> activos(@PathVariable Long id) {
        return prestamoService.listarPrestamosActivosLector(id);
    }
}
