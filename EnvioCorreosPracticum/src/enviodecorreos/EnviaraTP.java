package enviodecorreos;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

import datos.*;
import parser.ParserConfiguracion;

public class EnviaraTP {
	private static ParserConfiguracion pc;
	private static String mandatoThunderbird;

	public static void main(String[] args) {
		mandatoThunderbird = args[0];
		String inputFile = args[2];
		String inputFileConf = args[1];
		BaseDeAlumnos bDeAlumnos = new BaseDeAlumnos();

		System.out.println("Working Directory = " + System.getProperty("user.dir"));

		try {
			bDeAlumnos.getAlumnosConDestinoFromFile(inputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			pc = new ParserConfiguracion(inputFileConf);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		Scanner sc = new Scanner(System.in);
		for (TutorProfesional tProfesional : bDeAlumnos.getbTutoresProfesionales()) {
			enviarCorreoAUnTutor(tProfesional, bDeAlumnos);
			sc.nextLine();
		}
		sc.close();
		System.out.println("Terminado!");
	}

	private static void enviarCorreoAUnTutor(TutorProfesional tProfesional, BaseDeAlumnos bDeAlumnos) {
		LinkedList<Alumno> alumnos = tProfesional.getTutorados();

		String subject = pc.getAsunto();
		String attachmentRoute = pc.getAdjuntos();
		String bodyText = pc.getTextoPersonalizado(null, tProfesional, alumnos);

		System.out.println("TP: " + tProfesional.getNombre() + "\nCorreo:\n" + bodyText);

		try {
			// Runtime.getRuntime()
			// 		.exec(new String[] { "thunderbird", "-compose", "to='" + tProfesional.getEmail() + "'," + "subject='"
			// 				+ subject + "'," + "attachment='" + attachmentRoute + "'," + "body='" + bodyText + "'" });
			Runtime.getRuntime()
					.exec(new String[] { mandatoThunderbird, "-compose", "to='" + tProfesional.getEmail() + "'," + "subject='"
							+ subject + "'," + ((attachmentRoute!="")?("attachment='" + attachmentRoute):("")) + "'," + "body='" + bodyText + "'" });
		} catch (IOException e1) {
			System.out.println("IOException");
			e1.printStackTrace();
		}
	}
}