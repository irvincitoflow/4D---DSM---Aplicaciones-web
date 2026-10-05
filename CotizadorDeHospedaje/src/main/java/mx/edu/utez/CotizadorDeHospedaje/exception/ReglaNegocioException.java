package mx.edu.utez.CotizadorDeHospedaje.exception;

// Se lanza cuando la petición es válida pero no cumple una regla de negocio
public class ReglaNegocioException extends RuntimeException {

    public ReglaNegocioException(String mensaje) {
        super(mensaje);
    }
}
