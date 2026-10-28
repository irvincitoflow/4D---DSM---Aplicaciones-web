package mx.edu.utez.u2a9prestamos_y_libros.controller.lector;

import jakarta.validation.Valid;
import mx.edu.utez.u2a9prestamos_y_libros.controller.lector.dto.LectorDTO;
import mx.edu.utez.u2a9prestamos_y_libros.model.lector.Lector;
import mx.edu.utez.u2a9prestamos_y_libros.service.lector.LectorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lector")
@CrossOrigin("*")
public class LectorController {

    private final LectorService lectorService;

    public LectorController(LectorService lectorService) {
        this.lectorService = lectorService;
    }

    @PostMapping
    public ResponseEntity<Lector> crear(@Valid @RequestBody LectorDTO dto) {
        return ResponseEntity.status(201).body(lectorService.crearLector(dto));
    }

    @PutMapping("/{id}/rehabilitar")
    public Lector rehabilitar(@PathVariable Long id) {
        return lectorService.rehabilitarLector(id);
    }
}
