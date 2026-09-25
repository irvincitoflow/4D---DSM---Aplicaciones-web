package mx.edu.utez.FizzBuzz.contollers;

import mx.edu.utez.FizzBuzz.contollers.dto.RequestBodyDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tareas")
public class FizzBuzzController {

    @GetMapping("/juego1/fizzbuzz/{cantidad}")
    public RequestBodyDTO resolverFizzBuzz(@PathVariable int cantidad) {

        for (int x = 1; x <= cantidad; x++) {
            if (x % 15 == 0) {
                System.out.println("FizzBuzz");
            } else if (x % 3 == 0) {
                System.out.println("Fizz");
            } else if (x % 5 == 0) {
                System.out.println("Buzz");
            } else {
                System.out.println(x);
            }
        }
        // Sustituye con tu nombre real
        return new RequestBodyDTO("TU_NOMBRE_COMPLETO_AQUÍ");
    }

    @GetMapping("/juego2/fibonacci/{cantidad}")
    public RequestBodyDTO resolverFibonacci(@PathVariable int cantidad) {

        long val1 = 0;
        long val2 = 1;

        for (int j = 0; j < cantidad; j++) {
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