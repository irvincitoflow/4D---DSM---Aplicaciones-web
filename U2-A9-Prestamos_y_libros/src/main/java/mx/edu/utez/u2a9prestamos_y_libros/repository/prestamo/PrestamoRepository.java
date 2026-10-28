package mx.edu.utez.u2a9prestamos_y_libros.repository.prestamo;

import mx.edu.utez.u2a9prestamos_y_libros.model.prestamo.Estado;
import mx.edu.utez.u2a9prestamos_y_libros.model.prestamo.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    long countByLectorIdAndEstadoIn(Long lectorId, List<Estado> estados);

    List<Prestamo> findByLectorIdAndEstadoIn(Long lectorId, List<Estado> estados);
}
