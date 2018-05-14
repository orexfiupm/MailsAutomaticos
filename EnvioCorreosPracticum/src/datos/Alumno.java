package datos;

public class Alumno extends Persona {
    private TutorAcademico tAcademico;
	private Integer numeroMatricula;

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

    public String toBeautifulStringSoloAlumno(boolean mostrarMatricula) {
        return getApellidos() + ", " + getNombre() + ", email: " + getEmail() + ((getNumeroMatricula()!=null && mostrarMatricula)?(", número de matrícula: " + getNumeroMatricula()):(""));
    }

    public boolean esVacio() {
        return (getNombre()==null && getApellidos()==null && getEmail()==null) 
            || (getNombre()=="" && getApellidos()=="" && getEmail()=="");
    }

    public Integer getNumeroMatricula() {
		return this.numeroMatricula;
	}

	public void setNumeroMatricula(Integer numeroMatricula) {
		this.numeroMatricula = numeroMatricula;
	}
}
