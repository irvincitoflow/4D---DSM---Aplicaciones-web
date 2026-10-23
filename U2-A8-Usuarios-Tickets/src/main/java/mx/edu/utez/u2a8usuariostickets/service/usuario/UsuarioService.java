package mx.edu.utez.u2a8usuariostickets.service.usuario;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.u2a8usuariostickets.controller.dto.UsuarioRequestDTO;
import mx.edu.utez.u2a8usuariostickets.exception.RecursoNoEncontradoException;
import mx.edu.utez.u2a8usuariostickets.model.usuario.Usuario;
import mx.edu.utez.u2a8usuariostickets.repository.usuario.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public Usuario crearUsuario(UsuarioRequestDTO dto) {
        Usuario u = new Usuario();
        u.setNombre(dto.getNombre());
        u.setIsEnable(true);
        return usuarioRepository.save(u);
    }

    // 20 pts
    public Usuario desactivarUsuario(Long id) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("El usuario no existe"));
        u.setIsEnable(false);
        return usuarioRepository.save(u);
    }
}
