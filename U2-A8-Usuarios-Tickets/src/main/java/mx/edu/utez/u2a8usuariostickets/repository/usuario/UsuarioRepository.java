package mx.edu.utez.u2a8usuariostickets.repository.usuario;

import mx.edu.utez.u2a8usuariostickets.model.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

}
