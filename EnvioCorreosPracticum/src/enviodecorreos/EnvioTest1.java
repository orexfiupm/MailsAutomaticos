package enviodecorreos;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

import datos.Alumno;
import datos.BaseDeAlumnos;
import datos.TutorAcademico;
import datos.TutorProfesional;
import parser.ParserConfiguracion;

public class EnvioTest1 {

	private static ParserConfiguracion pc;

	public static void main(String[] args) {
		String inputFile = "/home/luciano/Dropbox/actualesUPM/beca/eclipse-workspace/EnvioCorreosPracticum/GII_PRACTICUM_2017-2018_1º S.xls";
		String inputFileConf = "/home/luciano/Dropbox/actualesUPM/beca/eclipse-workspace/EnvioCorreosPracticum/envioCorreo.conf";

		BaseDeAlumnos bDeAlumnos = new BaseDeAlumnos();
		try {
			bDeAlumnos.getAlumnosConDestinoFromFile(inputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// enviarCorreoATodosLosAlumnos(bDeAlumnos);

		// LinkedList<TutorAcademico> bTutorAcademicos = bDeAlumnos.getbTutoresAcademicos();
		// enviarCorreoAUnTutor(bTutorAcademicos.get((int) (Math.random()*10 + 1)), bDeAlumnos);

		try {
			pc = new ParserConfiguracion(inputFileConf);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		enviarCorreoAUnTutor(bDeAlumnos.getbTutoresAcademicos().get(3), bDeAlumnos);
	}

	private static void enviarCorreoAUnTutor(TutorAcademico tAcademico, BaseDeAlumnos bDeAlumnos) {
		String nombresAlumnos = "";
		LinkedList<Alumno> alumnos = tAcademico.getTutorados();
		for (Alumno a : alumnos) {
			nombresAlumnos += a.getNombre() + " " + a.getApellidos() + ", " + a.getEmail() + ",\n";
		}

		String subject = "ejemploTema";
		String attachmentRoute = "file:///home/luciano/career_path_2016_vweb.pdf";
		String bodyText = "<h1>Alumnos de este tutor:</h1> <p>{{SECUENCIADEALUMNOS}}</p>"; // Body can be in HTML

		bodyText = pc.getTextoPersonalizado(tAcademico, null, alumnos.getFirst());
		
		System.out.println("TA: " + tAcademico.getNombre() + "\nCorreo:\n" +  bodyText);

		try {
			Runtime.getRuntime().exec(new String[] {
				"thunderbird", 
				"-compose", 
				"to='" + tAcademico.getEmail() + "'," + 
				"subject='" + subject + "'," +
				"attachment='" + attachmentRoute + "'," + 
				"body='" + bodyText + "'"
			});
		} catch (IOException e1) {
			System.out.println("IOException");
			e1.printStackTrace();
		}
	}


	private static void enviarCorreoATodosLosAlumnos(BaseDeAlumnos bDeAlumnos) {
		String mails = "";
		LinkedList<Alumno> alumnos = bDeAlumnos.getAlumnos();
		for (Alumno a : alumnos) {
			mails += a.getEmail() + ",";
		}

		String subject = "ejemploTema";
		String attachmentRoute = "file:///home/luciano/career_path_2016_vweb.pdf" + "," + "file:///home/luciano/Dropbox/CPE confirmation of entry.pdf";
		String bodyText = "<h1>Heading 1</h1> <p>asdasdasd</p>"; // Body can be in HTML
		
		try {	
			Runtime.getRuntime().exec(new String[] {
				"thunderbird", 
				"-compose", 
				"to='" + mails + "'," + 
				"subject='" + subject + "'," +
				"attachment='" + attachmentRoute + "'," + 
				"body='" + bodyText + "'"
			});
		} catch (IOException e1) {
			System.out.println("IOException");
			e1.printStackTrace();
		}
	}

	

}
