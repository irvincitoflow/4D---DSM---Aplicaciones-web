package mx.edu.utez.u2a9prestamos_y_libros.service.libro;

import mx.edu.utez.u2a9prestamos_y_libros.controller.libro.dto.LibroDTO;
import mx.edu.utez.u2a9prestamos_y_libros.exception.RecursoNoEncontradoException;
import mx.edu.utez.u2a9prestamos_y_libros.model.libro.Libro;
import mx.edu.utez.u2a9prestamos_y_libros.repository.libro.LibroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroService {

    private final LibroRepository libroRepository;

    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    public Libro crearLibro(LibroDTO dto) {
        Libro libro = new Libro();
        libro.setTitulo(dto.getTitulo());
        libro.setEjemplaresDisponibles(dto.getEjemplaresDisponibles());
        return libroRepository.save(libro);
    }

    public List<Libro> listarLibros() {
        return libroRepository.findAll();
    }

    public Libro buscarPorId(Long id) {
        return libroRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Libro no encontrado"));
    }
}
