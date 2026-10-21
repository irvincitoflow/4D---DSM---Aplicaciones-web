package mx.edu.utez.proyecto1D.service;


import mx.edu.utez.proyecto1D.controller.dto.RequestCalculadoraDTO;
import mx.edu.utez.proyecto1D.controller.dto.ResponseCalculadoraDTO;
import mx.edu.utez.proyecto1D.exception.customExceptions.CustomBadRequestException;
import org.springframework.stereotype.Service;

@Service
public class MyService {
    public ResponseCalculadoraDTO calculadora (RequestCalculadoraDTO data){
        ResponseCalculadoraDTO respuesta = new ResponseCalculadoraDTO();

        if (
                !data.getOperacion().equals("SUMA")&&
                        !data.getOperacion().equals("RESTA")&&
                        !data.getOperacion().equals("MULTIPLICACION")&&
                        !data.getOperacion().equals("DIVISION")
        ){
            throw new CustomBadRequestException("La operacion solicitada no es valida");
        }


        switch (data.getOperacion()){
            case "Suma":
                respuesta.setResultado(
                        data.getNum1() + data.getNum2()
                );
                break;

            case "Resta":
                respuesta.setResultado(
                        data.getNum1() - data.getNum2()
                );


            case "Multiplicacion":
                respuesta.setResultado(
                        data.getNum1() * data.getNum2()
                );
                break;
        }
        respuesta.setOperacionRealizada(data.getOperacion());
        return respuesta;
    }
}
