package datos;

public class TutorAcademico extends Persona {

    public TutorAcademico(String nombre, String apellidos, String email) {
        super(nombre, apellidos, email);
    }

    public boolean mismo(TutorAcademico a) {
        boolean equal = (super.getApellidos().equals(a.getApellidos()) && 
                        (super.getEmail().equals(a.getEmail())) &&
                        (super.getNombre().equals(a.getNombre())));
        return equal;
    }

    public String toString() {
        return "tutorAcademico: {" + getNombre() + " " + getApellidos() + " " + getEmail() + "}";
    }

}
