package mx.edu.utez.proyecto1D.exception.customExceptions;

public class CustomBadRequestException  extends RuntimeException {
    public CustomBadRequestException(String mensaje){
        super(mensaje);
    }

}
