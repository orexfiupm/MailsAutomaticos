package datos;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

public class BaseDeAlumnos {
    private LinkedList<Alumno> bAlumnos;
    private LinkedList<TutorAcademico> bTutoresAcademicos;
    private LinkedList<TutorProfesional> bTutoresProfesionales;
    private HashMap<String, Integer> correspondenciaColumna;

    public LinkedList<Alumno> getbAlumnos() {
        return bAlumnos;
    }

    public void setbAlumnos(LinkedList<Alumno> bAlumnos) {
        this.bAlumnos = bAlumnos;
    }

    public LinkedList<TutorAcademico> getbTutoresAcademicos() {
        return bTutoresAcademicos;
    }

    public LinkedList<TutorProfesional> getbTutoresProfesionales() {
        return bTutoresProfesionales;
    }

    public void setbTutoresAcademicos(LinkedList<TutorAcademico> bTutoresAcademicos) {
        this.bTutoresAcademicos = bTutoresAcademicos;
    }

    public BaseDeAlumnos() {
        bAlumnos = new LinkedList<>();
        bTutoresAcademicos = new LinkedList<>();
        bTutoresProfesionales = new LinkedList<>();
        correspondenciaColumna = new HashMap<>();
    }

    public void addAlumno(Alumno alumno) {
        bAlumnos.add(alumno);
    }

    public TutorAcademico addTutorAcademico(TutorAcademico tAcademico) {
        if (tAcademico.esVacio()) {
            return null;
        }
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
        } else {
            // bTutoresAcademicos.add(tAcademicoYaExistente);
            return tAcademicoYaExistente;
        }
    }

    public TutorProfesional addTutorProfesional(TutorProfesional tProfesional) {
        if (tProfesional.esVacio()) {
            return null;
        }
        TutorProfesional tProfesionalYaExistente = null;
        for (TutorProfesional ta : bTutoresProfesionales) {
            if (ta.mismo(tProfesional)) {
                tProfesionalYaExistente = ta;
                break;
            }
        }
        if (tProfesionalYaExistente == null) {
            bTutoresProfesionales.add(tProfesional);
            // System.out.print("Se añade TP ");
            // System.out.println(tProfesional.toString());
            return tProfesional;
        } else {
            // bTutoresAcademicos.add(tProfesionalYaExistente);
            // System.out.print("Se devuelve TP existente ");
            // System.out.println(tProfesional.toString());
            return tProfesionalYaExistente;
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
        Workbook w = null;
        try {
            WorkbookSettings ws = new WorkbookSettings();
            // ws.setEncoding("CP1250");
            ws.setEncoding("CP1252");
            try {
                w = Workbook.getWorkbook(new File(docPracticumPath), ws);
                System.out.println(docPracticumPath);
            }
            catch (IOException ioException) {
                System.out.println("No se puede encontrar el fichero de alumnos. Posiblemente bdalumnos.xls no exista o no esté en el sitio adecuado.");
                throw new IOException();
            }
            Sheet sheet = w.getSheet("alumnos con destino");
            

            // Read the first row to extract the names of the columns
            String cell;
            for (int i = 0; i < sheet.getColumns() && ((cell = sheet.getCell(i, 0).getContents()) != null); i++) {
                correspondenciaColumna.put(cell, i);
            }

            // Read rest of sheet to get all students and their mentors
            for (int i = 1; i < sheet.getRows(); i++) {
                TutorAcademico tAcademico = addTutorAcademico(
                        new TutorAcademico(sheet.getCell(correspondenciaColumna.get("TA"), i).getContents(), "",
                                sheet.getCell(correspondenciaColumna.get("mailTA"), i).getContents()));
                TutorProfesional tProfesional = addTutorProfesional(
                        new TutorProfesional(sheet.getCell(correspondenciaColumna.get("TP"), i).getContents(), "",
                                sheet.getCell(correspondenciaColumna.get("mailTP"), i).getContents(),
                                sheet.getCell(correspondenciaColumna.get("EMPRESA"), i).getContents(),
                                sheet.getCell(correspondenciaColumna.get("telTP"), i).getContents()));
                
                Alumno alumno = new Alumno(sheet.getCell(correspondenciaColumna.get("NOMBRE"), i).getContents(),
                        sheet.getCell(correspondenciaColumna.get("APELLIDOS"), i).getContents(),
                        sheet.getCell(correspondenciaColumna.get("mailAlumno"), i).getContents(), tAcademico,
                        tProfesional, sheet.getCell(correspondenciaColumna.get("EMPRESA"), i).getContents());
                if (!alumno.esVacio()) {
                    addAlumno(alumno);
                    tAcademico.getTutorados().add(alumno);
                    tProfesional.getTutorados().add(alumno);
                }

            }

            // for (Alumno alumno : bAlumnos) {
            // System.out.println(alumno.toString());
            // }

            // System.out.println("Ahora tutores");

            // for (TutorAcademico tAcademico : bTutoresAcademicos) {
            //     System.out.println(tAcademico.toString());
            // }

            // for (TutorProfesional tProfesional : bTutoresProfesionales) {
            //     System.out.println(tProfesional.toString());
            // }

            // byte ptext[] = sheet.getCell(aOrden('U'),
            // 0).getContents().getBytes("Cp1250");
            // String value = new String(ptext, "Cp1250");
            // System.out.println(value);
        } catch (

        BiffException e) {
            e.printStackTrace();
        }
    }

    // private int aOrden(char a) {
    // return a - 'A';
    // }
}
