package mx.edu.utez.u2actividad5.repository.tiro;


import mx.edu.utez.u2actividad5.model.tiro.Tiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TiroRepository extends JpaRepository<Tiro, Long> {
}