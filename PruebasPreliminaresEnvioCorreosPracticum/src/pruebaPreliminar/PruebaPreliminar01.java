package pruebaPreliminar;

import java.io.IOException;

public class PruebaPreliminar01 {

	public static void main(String[] args) {
		System.out.println("Working!");

		String mail = "l.garciag@alumnos.upm.es" + "," + "lucgarc97@gmail.com";
		String subject = "Testing,Subject";
		String attachmentRoute = "file:///home/luciano/career_path_2016_vweb.pdf" + "," + "file:///home/luciano/Dropbox/CPE confirmation of entry.pdf";
		String bodyText = "<h1>Heading 1</h1> <p>asdasdasdasd</p>"; // Body can be in HTML

		try {	
			Runtime.getRuntime().exec(new String[] {
				"thunderbird", 
				"-compose", 
				"to='" + mail + "'," + 
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
