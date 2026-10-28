package mx.edu.utez.u2a9prestamos_y_libros.repository.lector;

import mx.edu.utez.u2a9prestamos_y_libros.model.lector.Lector;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectorRepository extends JpaRepository<Lector,Long> {
}
