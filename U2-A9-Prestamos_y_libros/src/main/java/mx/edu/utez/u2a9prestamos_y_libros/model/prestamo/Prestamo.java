package mx.edu.utez.u2a9prestamos_y_libros.model.prestamo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.u2a9prestamos_y_libros.model.lector.Lector;
import mx.edu.utez.u2a9prestamos_y_libros.model.libro.Libro;

import java.time.LocalDate;

@Entity
@Table(name = "prestamo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lector_id")
    private Lector lector;

    @ManyToOne
    @JoinColumn(name = "libro_id")
    private Libro libro;

    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionMaxima;

    @Enumerated(EnumType.STRING)
    private Estado estado;
}
