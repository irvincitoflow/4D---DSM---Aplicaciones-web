package mx.edu.utez.proyecto1D.service.persona;

import mx.edu.utez.proyecto1D.controller.persona.dto.RequestUpdatePersonaDTO;
import mx.edu.utez.proyecto1D.exception.customExceptions.CustomNotFoundException;
import mx.edu.utez.proyecto1D.model.persona.Persona;
import mx.edu.utez.proyecto1D.repository.persona.PersonaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonaService {

    private final PersonaRepository personaRepository;

    public PersonaService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    @Transactional
    public Persona updatePersona(Long id, RequestUpdatePersonaDTO payload){
        // validar que la persona exista
        Persona persona = personaRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("No existe una persona con el id " + id));

        // actualizar los datos sobre la misma persona (el id no cambia)
        persona.setNombres(payload.getNombres());
        persona.setPrimerApellido(payload.getPrimerApellido());
        persona.setSegundoApellido(payload.getSegundoApellido());
        persona.setCorreo(payload.getCorreo());
        persona.setCurp(payload.getCurp());
        persona.setFechaNcimiento(payload.getFechaNcimiento());

        return personaRepository.save(persona);
    }
}
