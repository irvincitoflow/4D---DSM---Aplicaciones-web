package mx.edu.utez.proyecto1D.model.direccion;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.proyecto1D.model.persona.Persona;
import org.hibernate.validator.constraints.ISBN;

@Entity
@Table(name = "direcciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Direccion {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private  String calle;
    private int noExterior;
    private String colonia;
    private String municipio;
    private String estado;
    private String codigoPostal;



}
