package mx.edu.utez.u2a9prestamos_y_libros.repository.libro;

import mx.edu.utez.u2a9prestamos_y_libros.model.libro.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Libro,Long> {
}
