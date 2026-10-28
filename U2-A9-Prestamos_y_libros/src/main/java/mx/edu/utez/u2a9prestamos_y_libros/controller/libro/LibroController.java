package mx.edu.utez.u2a9prestamos_y_libros.controller.libro;

import jakarta.validation.Valid;
import mx.edu.utez.u2a9prestamos_y_libros.controller.libro.dto.LibroDTO;
import mx.edu.utez.u2a9prestamos_y_libros.model.libro.Libro;
import mx.edu.utez.u2a9prestamos_y_libros.service.libro.LibroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/libro")
@CrossOrigin("*")
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @PostMapping
    public ResponseEntity<Libro> crear(@Valid @RequestBody LibroDTO dto) {
        return ResponseEntity.status(201).body(libroService.crearLibro(dto));
    }

    @GetMapping
    public List<Libro> listar() {
        return libroService.listarLibros();
    }

    @GetMapping("/{id}")
    public Libro buscar(@PathVariable Long id) {
        return libroService.buscarPorId(id);
    }
}