package mx.edu.utez.u2a9prestamos_y_libros.model.lector;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "lector")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Lector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private boolean activo;
    private boolean membresiaPremium;
}
