package mx.edu.utez.FizzBuzz.contollers.dto;

public class RequestBodyDTO {

    private String alumno;

    public RequestBodyDTO() {
    }

    public RequestBodyDTO(String alumno) {
        this.alumno = alumno;
    }

    public String getAlumno() {
        return alumno;
    }

    public void setAlumno(String alumno) {
        this.alumno = alumno;
    }
}