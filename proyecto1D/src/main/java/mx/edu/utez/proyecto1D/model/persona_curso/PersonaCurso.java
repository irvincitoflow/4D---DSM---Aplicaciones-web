package mx.edu.utez.proyecto1D.model.persona_curso;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.proyecto1D.model.cursos.Curso;
import mx.edu.utez.proyecto1D.model.persona.Persona;

import java.time.LocalDate;

@Entity
@Table(name = "personas_cursos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonaCurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaIngreso;
    private double calificacion;

    @ManyToOne
    @JoinColumn(name = "persona_id")
    private Persona persona;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

}
