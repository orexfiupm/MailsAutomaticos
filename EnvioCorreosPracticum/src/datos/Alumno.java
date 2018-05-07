package datos;

public class Alumno extends Persona {
    private TutorAcademico tAcademico;

    public Alumno(String nombre, String apellidos, String email, TutorAcademico tAcademico) {
        super(nombre, apellidos, email);
        this.tAcademico = tAcademico;
    }

    public TutorAcademico getTutorAcademico() {
        return this.tAcademico;
    }

    public boolean mismo(Alumno a) {
        boolean equal = (super.getApellidos().equals(a.getApellidos()) && 
                        (super.getEmail().equals(a.getEmail())) &&
                        (super.getNombre().equals(a.getNombre())) &&
                        (this.tAcademico.mismo(a.getTutorAcademico())));
        return equal;
    }

    public String toString() {
        return "alumno: {" + getNombre() + " " + getApellidos() + " " + getEmail() + " " + tAcademico.toString() + "}";
    }
}
