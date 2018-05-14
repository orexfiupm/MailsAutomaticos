package datos;

import java.util.LinkedList;

public class TutorProfesional extends Persona {
    private LinkedList<Alumno> tutorados;
    private String empresa;
    // TODO : complementar con nombre de la empresa y todo

    public TutorProfesional(String nombre, String apellidos, String email, String empresa, String telefono) {
        super(nombre, apellidos, email, telefono);
        tutorados = new LinkedList<>();
        this.empresa = empresa;
    }

    public boolean mismo(TutorProfesional a) {
        boolean equal = (super.getApellidos().equals(a.getApellidos()) && (super.getEmail().equals(a.getEmail()))
                && (super.getNombre().equals(a.getNombre())));
        return equal;
    }

    public String toString() {
        String tutoradosString = "";
        for (Alumno a : this.tutorados) {
            tutoradosString += a.getNombre() + " " + a.getApellidos() + ", ";
        }
        return "tutorProfesional: {" + getNombre() + " " + getApellidos() + " " + getEmail() + " tutorados: "
                + tutoradosString + "}";
    }

    public LinkedList<Alumno> getTutorados() {
        return this.tutorados;
    }

    public boolean esVacio() {
        return (getNombre() == null && getApellidos() == null && getEmail() == null)
                || (getNombre() == "" && getApellidos() == "" && getEmail() == "");
    }

    public String getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }
}
