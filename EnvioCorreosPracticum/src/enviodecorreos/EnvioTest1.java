package enviodecorreos;

import java.io.IOException;

import datos.BaseDeAlumnos;

public class EnvioTest1 {

	public static void main(String[] args) {
		String inputFile = "/home/luciano/Dropbox/actualesUPM/beca/eclipse-workspace/EnvioCorreosPracticum/GII_PRACTICUM_2017-2018_1º S.xls";
		

		BaseDeAlumnos bDeAlumnos = new BaseDeAlumnos();
		try {
			bDeAlumnos.getAlumnosConDestinoFromFile(inputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
