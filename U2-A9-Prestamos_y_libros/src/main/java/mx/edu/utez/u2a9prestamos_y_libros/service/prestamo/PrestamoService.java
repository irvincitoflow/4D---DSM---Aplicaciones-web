package mx.edu.utez.u2a9prestamos_y_libros.service.prestamo;

import mx.edu.utez.u2a9prestamos_y_libros.controller.prestamo.dto.SolicitudPrestamoDTO;
import mx.edu.utez.u2a9prestamos_y_libros.exception.RecursoNoEncontradoException;
import mx.edu.utez.u2a9prestamos_y_libros.exception.ReglasDeNegocioException;
import mx.edu.utez.u2a9prestamos_y_libros.model.lector.Lector;
import mx.edu.utez.u2a9prestamos_y_libros.model.libro.Libro;
import mx.edu.utez.u2a9prestamos_y_libros.model.prestamo.Estado;
import mx.edu.utez.u2a9prestamos_y_libros.model.prestamo.Prestamo;
import mx.edu.utez.u2a9prestamos_y_libros.repository.lector.LectorRepository;
import mx.edu.utez.u2a9prestamos_y_libros.repository.libro.LibroRepository;
import mx.edu.utez.u2a9prestamos_y_libros.repository.prestamo.PrestamoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final LectorRepository lectorRepository;
    private final LibroRepository libroRepository;

    public PrestamoService(PrestamoRepository prestamoRepository,
                           LectorRepository lectorRepository,
                           LibroRepository libroRepository) {
        this.prestamoRepository = prestamoRepository;
        this.lectorRepository = lectorRepository;
        this.libroRepository = libroRepository;
    }

    // Servicio 1 (50 pts): solicitarPrestamo
    @Transactional
    public Prestamo solicitarPrestamo(SolicitudPrestamoDTO dto) {
        Lector lector = lectorRepository.findById(dto.getLectorId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Lector no encontrado"));
        if (!lector.isActivo()) {
            throw new ReglasDeNegocioException("El lector no está activo");
        }

        Libro libro = libroRepository.findById(dto.getLibroId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Libro no encontrado"));
        if (libro.getEjemplaresDisponibles() < 1) {
            throw new ReglasDeNegocioException("No hay ejemplares disponibles de este libro");
        }

        long activos = prestamoRepository.countByLectorIdAndEstadoIn(
                lector.getId(), List.of(Estado.PRESTADO, Estado.VENCIDO));
        int limite = lector.isMembresiaPremium() ? 5 : 3;
        if (activos >= limite) {
            throw new ReglasDeNegocioException("El lector alcanzó su límite de " + limite + " préstamos activos");
        }

        libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() - 1);
        libroRepository.save(libro);

        Prestamo prestamo = new Prestamo();
        prestamo.setLector(lector);
        prestamo.setLibro(libro);
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setFechaDevolucionMaxima(LocalDate.now().plusDays(14));
        prestamo.setEstado(Estado.PRESTADO);
        return prestamoRepository.save(prestamo);
    }

    // Servicio 2 (30 pts): devolverLibro
    @Transactional
    public Prestamo devolverLibro(Long prestamoId) {
        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Préstamo no encontrado"));
        if (prestamo.getEstado() == Estado.DEVUELTO) {
            throw new ReglasDeNegocioException("El préstamo ya fue devuelto");
        }

        prestamo.setEstado(Estado.DEVUELTO);

        Libro libro = prestamo.getLibro();
        libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() + 1);
        libroRepository.save(libro);

        // entrega tardía: se bloquea al lector
        if (LocalDate.now().isAfter(prestamo.getFechaDevolucionMaxima())) {
            Lector lector = prestamo.getLector();
            lector.setActivo(false);
            lectorRepository.save(lector);
        }

        return prestamoRepository.save(prestamo);
    }

    // Servicio 3 (15 pts): renovarPrestamo
    @Transactional
    public Prestamo renovarPrestamo(Long prestamoId) {
        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Préstamo no encontrado"));
        if (prestamo.getEstado() == Estado.VENCIDO || prestamo.getEstado() == Estado.DEVUELTO) {
            throw new ReglasDeNegocioException("No se puede renovar un préstamo vencido o devuelto");
        }

        prestamo.setFechaDevolucionMaxima(prestamo.getFechaDevolucionMaxima().plusDays(7));
        return prestamoRepository.save(prestamo);
    }

    // Servicio 5 (10 pts): listarPrestamosActivosLector
    public List<Prestamo> listarPrestamosActivosLector(Long lectorId) {
        if (!lectorRepository.existsById(lectorId)) {
            throw new RecursoNoEncontradoException("Lector no encontrado");
        }
        return prestamoRepository.findByLectorIdAndEstadoIn(
                lectorId, List.of(Estado.PRESTADO, Estado.VENCIDO));
    }
}
