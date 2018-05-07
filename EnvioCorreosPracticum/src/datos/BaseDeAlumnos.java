package datos;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

public class BaseDeAlumnos {
    private LinkedList<Alumno> bAlumnos;
    private LinkedList<TutorAcademico> bTutoresAcademicos;
    //private LinkedList<TutorProfesional> bTutoresProfesionales;

    public BaseDeAlumnos() {
        bAlumnos = new LinkedList<>();
        bTutoresAcademicos = new LinkedList<>();
        bTutoresProfesionales = new LinkedList<>();
    }

    public void addAlumno(Alumno alumno) {
        bAlumnos.add(alumno);
    }

    public TutorAcademico addTutorAcademico(TutorAcademico tAcademico) {
        TutorAcademico tAcademicoYaExistente = null;
        for (TutorAcademico ta : bTutoresAcademicos) {
            if (ta.mismo(tAcademico)) {
                tAcademicoYaExistente = ta;
                break;
            }
        }
        if (tAcademicoYaExistente == null) {
            bTutoresAcademicos.add(tAcademico);
            return tAcademico;
        }
        else {
            bTutoresAcademicos.add(tAcademicoYaExistente);
            return tAcademicoYaExistente;
        }
    }

    public LinkedList<Alumno> getAlumnos() {
        return this.bAlumnos;
    }

    public TutorAcademico getTutorAcademicoDeAlumno(Alumno alumno) {
        TutorAcademico tAcademico = null;
        for (Alumno a : bAlumnos) {
            if (a.mismo(alumno)) {
                tAcademico = a.getTutorAcademico();
                break;
            }
        }
        return tAcademico;
    }

    public void getAlumnosConDestinoFromFile(String docPracticumPath) throws IOException {
		Workbook w;
		try {
            WorkbookSettings ws = new WorkbookSettings();
            ws.setEncoding("CP1250");
            w = Workbook.getWorkbook(new File(docPracticumPath), ws);
            Sheet sheet = w.getSheet(2); //Sheet numbers start by 1

            for (int i=0; i<sheet.getRows(); i++) {
                TutorAcademico tAcademico = addTutorAcademico(new TutorAcademico("", sheet.getCell(aOrden('U'), i).getContents(), sheet.getCell(aOrden('V'), i).getContents()));
                addAlumno(new Alumno(sheet.getCell(aOrden('B'), i).getContents(), sheet.getCell(aOrden('A'), i).getContents(), sheet.getCell(aOrden('C'), i).getContents(), tAcademico));
            }

            for (Alumno alumno : bAlumnos) {
                System.out.println(alumno.toString());
            }

            System.out.println("Ahora tutores");

            for (TutorAcademico tAcademico : bTutoresAcademicos) {
                System.out.println(tAcademico.toString());
            }

            //byte ptext[] = sheet.getCell(aOrden('U'), 0).getContents().getBytes("Cp1250"); 
            //String value = new String(ptext, "Cp1250"); 
            //System.out.println(value);
		} 
		catch(BiffException e) {
			e.printStackTrace();
		}
    }

    private int aOrden(char a) {
        return a - 'A';
    }
}
