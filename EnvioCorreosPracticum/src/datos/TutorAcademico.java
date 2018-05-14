package datos;

import java.util.LinkedList;

public class TutorAcademico extends Persona {
    private LinkedList<Alumno> tutorados;

    public TutorAcademico(String nombre, String apellidos, String email) {
        super(nombre, apellidos, email);
        tutorados = new LinkedList<>();
    }

    public boolean mismo(TutorAcademico a) {
        boolean equal = (super.getApellidos().equals(a.getApellidos()) && 
                        (super.getEmail().equals(a.getEmail())) &&
                        (super.getNombre().equals(a.getNombre())));
        return equal;
    }

    public String toString() {
        String tutoradosString = "";
        for (Alumno a : this.tutorados) {
            tutoradosString += a.getNombre() + " " + a.getApellidos() + ", ";
        }
        return "tutorAcademico: {" + getNombre() + " " + getApellidos() + " " + getEmail() + " tutorados: " + tutoradosString + "}";
    }

    public LinkedList<Alumno> getTutorados() {
        return this.tutorados;
    }

    public boolean esVacio() {
        return (getNombre()==null && getApellidos()==null && getEmail()==null) 
            || (getNombre()=="" && getApellidos()=="" && getEmail()=="");
    }
}
