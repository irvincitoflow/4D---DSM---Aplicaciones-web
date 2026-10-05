package mx.edu.utez.CotizadorDeRentaDeVehiculos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // errores de @Valid en los DTO
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> manejarValidacion(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(e -> errores.put(e.getField(), e.getDefaultMessage()));

        return construirRespuesta(HttpStatus.BAD_REQUEST,
                "Hay errores en los datos enviados", errores);
    }

    // reglas de negocio que no se cumplen
    @ExceptionHandler(ReglaNegocioException.class)
    public ResponseEntity<Map<String, Object>> manejarReglaNegocio(ReglaNegocioException ex) {
        return construirRespuesta(HttpStatus.BAD_REQUEST, ex.getMessage(), null);
    }

    // JSON mal escrito o con tipos incorrectos (por ejemplo texto en vez de número)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> manejarJsonInvalido(HttpMessageNotReadableException ex) {
        return construirRespuesta(HttpStatus.BAD_REQUEST,
                "El JSON está mal formado o tiene un tipo de dato incorrecto", null);
    }

    private ResponseEntity<Map<String, Object>> construirRespuesta(HttpStatus status, String mensaje, Object errores) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", status.value());
        body.put("mensaje", mensaje);
        if (errores != null) {
            body.put("errores", errores);
        }
        body.put("fecha", LocalDateTime.now().toString());
        return ResponseEntity.status(status).body(body);
    }
}
