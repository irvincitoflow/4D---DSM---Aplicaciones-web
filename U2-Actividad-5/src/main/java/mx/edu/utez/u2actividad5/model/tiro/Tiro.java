package mx.edu.utez.u2actividad5.model.tiro;

import jakarta.persistence.*;
import lombok.*;
import mx.edu.utez.u2actividad5.model.Partida;

@Entity
@Table(name = "tiros")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "partida_id")
    private Partida partida;

    private int valorDado1;
    private int valorDado2;
    private int suma;
    private boolean esGanador;
}