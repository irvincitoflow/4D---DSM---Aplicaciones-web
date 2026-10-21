package mx.edu.utez.u2actividad5.model.jugador;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "jugadores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private double saldo;

    private boolean activo;
}