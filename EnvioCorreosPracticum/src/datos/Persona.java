package datos;

public class Persona {
	private String nombre;
	private String apellidos;
	private String email;
	private String telefono;

	public Persona(String nombre, String apellidos, String email) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
	}

	public Persona(String nombre, String apellidos, String email, String telefono) {
		// this.nombre = nombre.replaceAll("\u0015E", "A");
		this.nombre = nombre;

		this.apellidos = apellidos;
		this.email = email;
		this.telefono = telefono;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
}
