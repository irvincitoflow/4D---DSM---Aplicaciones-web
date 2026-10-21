package mx.edu.utez.proyecto1D.controller;

import jakarta.validation.Valid;
import mx.edu.utez.proyecto1D.controller.dto.RequestBodyDTO;
import mx.edu.utez.proyecto1D.controller.dto.RequestCalculadoraDTO;
import mx.edu.utez.proyecto1D.controller.dto.ResponseCalculadoraDTO;
import mx.edu.utez.proyecto1D.service.MyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;

@RestController
@CrossOrigin({"*"})
@RequestMapping("/my-services")

public class MyController {

    private final MyService myService;

    public MyController(MyService myService){
        this.myService=myService;
    }

    @GetMapping
    public String miPrimerServicio() {

        System.out.println("Hello world!");
        return "Hello world!";
    }
    @GetMapping("/segundo-servicio")
    public String servicio2() {
        return "Este es mi segundo servicio";
    }

    @PostMapping
    public String servicio3() {
        return "3er servicio";
    }

    ///
    @GetMapping("/path-variable/{id}")
    public String pathVariable(@PathVariable String id) {
        System.out.println("El id es: " + id);
        return "El id es: " + id;
    }

    @PostMapping("/request-body")
    public ResponseEntity <RequestBodyDTO> requestBody(@RequestBody @Valid RequestBodyDTO paylod) {
        System.out.println(paylod.getNombre());
        System.out.println(paylod.getEdad());
        System.out.println(paylod.getCorreo());
        System.out.println();

        return ResponseEntity
                .status(201)
                .body(paylod);
    }
    @PostMapping("/calculadora")
    public ResponseEntity<ResponseCalculadoraDTO> calculadora (@RequestBody @Valid RequestCalculadoraDTO payload){
     return ResponseEntity.status(200).body(myService.calculadora(payload));
    }
}