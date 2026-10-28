package mx.edu.utez.u2a9prestamos_y_libros.service.lector;

import mx.edu.utez.u2a9prestamos_y_libros.controller.lector.dto.LectorDTO;
import mx.edu.utez.u2a9prestamos_y_libros.exception.RecursoNoEncontradoException;
import mx.edu.utez.u2a9prestamos_y_libros.model.lector.Lector;
import mx.edu.utez.u2a9prestamos_y_libros.repository.lector.LectorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LectorService {

    private final LectorRepository lectorRepository;

    public LectorService(LectorRepository lectorRepository) {
        this.lectorRepository = lectorRepository;
    }

    @Transactional
    public Lector crearLector(LectorDTO dto) {
        Lector lector = new Lector();
        lector.setNombre(dto.getNombre());
        lector.setMembresiaPremium(dto.isMembresiaPremium());
        lector.setActivo(true); // los nuevos nacen activos
        return lectorRepository.save(lector);
    }

    // Servicio 4 (10 pts): rehabilitarLector
    @Transactional
    public Lector rehabilitarLector(Long lectorId) {
        Lector lector = lectorRepository.findById(lectorId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Lector no encontrado"));

        lector.setActivo(true);
        return lectorRepository.save(lector);
    }
}