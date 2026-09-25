package mx.edu.utez.Fibonacci.controllers;

import mx.edu.utez.Fibonacci.controllers.dto.RequestBodyDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/juegos")
public class FibonacciController {

    @GetMapping("/fibonacci/{n}")
    public RequestBodyDTO calcularFibonacci(@PathVariable int n) {

        long val1 = 0;
        long val2 = 1;

        for (int j = 0; j < n; j++) {
            if (j == 0) {
                System.out.println(val1);
            } else if (j == 1) {
                System.out.println(val2);
            } else {
                long nuevoValor = val1 + val2;
                System.out.println(nuevoValor);

                val1 = val2;
                val2 = nuevoValor;
            }
        }

        // Sustituye con tu nombre real
        return new RequestBodyDTO("TU_NOMBRE_COMPLETO_AQUÍ");
    }
}