package mx.edu.utez.u2a8usuariostickets.controller.usuario;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mx.edu.utez.u2a8usuariostickets.controller.dto.UsuarioRequestDTO;
import mx.edu.utez.u2a8usuariostickets.model.usuario.Usuario;
import mx.edu.utez.u2a8usuariostickets.service.usuario.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> crear(@Valid @RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.crearUsuario(dto));
    }

    @PutMapping("/{id}/desactivar")
    public ResponseEntity<Usuario> desactivar(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.desactivarUsuario(id));
    }
}
