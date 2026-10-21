package mx.edu.utez.proyecto1D.repository.usuario;

import mx.edu.utez.proyecto1D.model.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository <Usuario, Long> {

    //select * from usuuarios whereusuario== ?
    Optional<Usuario> findByUsername(String username);
}
