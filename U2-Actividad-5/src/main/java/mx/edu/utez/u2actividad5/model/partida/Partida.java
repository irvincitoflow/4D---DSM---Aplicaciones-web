package mx.edu.utez.u2actividad5.model;

import jakarta.persistence.*;
import lombok.*;
import mx.edu.utez.u2actividad5.model.jugador.Jugador;
import mx.edu.utez.u2actividad5.model.partida.EstadoPartida;

import java.time.LocalDateTime;

@Entity
@Table(name = "partidas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;

    @Enumerated(EnumType.STRING)
    private EstadoPartida estado;

    @ManyToOne
    @JoinColumn(name = "jugador_id")
    private Jugador jugador;

    private double apuesta;
}