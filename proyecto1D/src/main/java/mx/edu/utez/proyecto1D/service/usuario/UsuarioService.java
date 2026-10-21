package mx.edu.utez.proyecto1D.service.usuario;

import mx.edu.utez.proyecto1D.controller.usuario.dto.RequestCreateUserDTO;
import mx.edu.utez.proyecto1D.controller.usuario.dto.RequestUpdateUserDTO;
import mx.edu.utez.proyecto1D.exception.customExceptions.CustomBadRequestException;
import mx.edu.utez.proyecto1D.exception.customExceptions.CustomNotFoundException;
import mx.edu.utez.proyecto1D.model.persona.Persona;
import mx.edu.utez.proyecto1D.model.usuario.Roles;
import mx.edu.utez.proyecto1D.model.usuario.Usuario;
import mx.edu.utez.proyecto1D.repository.persona.PersonaRepository;
import mx.edu.utez.proyecto1D.repository.usuario.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PersonaRepository personaRepository;


    public UsuarioService(UsuarioRepository usuarioRepository, PersonaRepository personaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.personaRepository = personaRepository;
    }

    //servicios
    public List<Usuario> getAllUsers(){
        return usuarioRepository.findAll();
    }

    @Transactional
    public  Usuario createUser(RequestCreateUserDTO payload){
        //valiadacions
        if (usuarioRepository.findByUsername(payload.getUsername()) .isPresent()){
            throw new CustomBadRequestException("El username ya esta en uso ");

        }
        //cREAR EL REGISTRO DE PERSONA
        Persona nuevaPersona = new Persona();
        nuevaPersona.setNombres(payload.getNombres());
        nuevaPersona.setPrimerApellido(payload.getPrimerApellido());
        nuevaPersona.setSegundoApellido(payload.getSegundoApellido());
        nuevaPersona.setCorreo(payload.getCorreo());
        nuevaPersona.setCurp(payload.getCurp());
        nuevaPersona.setFechaNcimiento(payload.getFechaNcimiento());

        nuevaPersona = personaRepository.save(nuevaPersona);

        //creae la instancia de usuario que vamos a guardar a partir de los datos de l dto
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsername(payload.getUsername());
        nuevoUsuario.setPassword(payload.getPassword());
        nuevoUsuario.setDescripcion(payload.getDescripcion());
        nuevoUsuario.setEnable(true);
        nuevoUsuario.setRol(Roles.valueOf(payload.getRol())
        );

        nuevoUsuario.setPersona(nuevaPersona);
        return usuarioRepository.save(nuevoUsuario);
    }

    // busca el usuario y si no esta en la bd manda 404
    private Usuario findUserOrThrow(Long id){
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("No existe un usuario con el id " + id));
    }

    public Usuario getUserById(Long id){
        return findUserOrThrow(id);
    }

    // sirve para activar (true) y desactivar (false)
    @Transactional
    public Usuario changeUserStatus(Long id, boolean enable){
        Usuario usuario = findUserOrThrow(id);
        usuario.setEnable(enable);
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario updateUser(Long id, RequestUpdateUserDTO payload){
        // 1. traer el usuario que YA existe (con su persona, password, rol, etc.)
        Usuario usuario = findUserOrThrow(id);

        // 2. el username nuevo no lo debe tener OTRO usuario
        Optional<Usuario> usuarioConEseUsername = usuarioRepository.findByUsername(payload.getUsername());
        if (usuarioConEseUsername.isPresent() && !usuarioConEseUsername.get().getId().equals(id)){
            throw new CustomBadRequestException("El username ya esta en uso");
        }

        // 3. cambiar SOLO lo que se pide, lo demas queda igual
        usuario.setUsername(payload.getUsername());
        usuario.setDescripcion(payload.getDescripcion());

        return usuarioRepository.save(usuario);
    }
}
