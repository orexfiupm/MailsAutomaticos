package parser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;

import datos.Alumno;
import datos.TutorAcademico;
import datos.TutorProfesional;

public class ParserConfiguracion {
    private String adjuntos;
    private String texto;
    private String asunto;

    public ParserConfiguracion(String path) throws FileNotFoundException {
        // BufferedReader bf = new BufferedReader(new FileReader(path));
        BufferedReader bf;
		try {
            // bf = new BufferedReader(new InputStreamReader(new FileInputStream(path), "CP1252"));
            bf = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            parseConfig(bf);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getAdjuntos() {
        return this.adjuntos;
    }

    public String getTexto() {
        return this.texto;
    }

    public String getAsunto() {
        return this.asunto;
    }

    private void parseConfig(BufferedReader bf) throws IOException {
        String line;
        while ((line = bf.readLine()) != null) {
            if (line.startsWith("ADJUNTOS:")) {
                String[] adjuntos = line.replace("ADJUNTOS:", "").trim().split(",");
                for (String s : adjuntos) {
                    s = "file://" + (s.trim());
                }
                String listaAdjuntos = "";
                if (adjuntos.length > 0) {
                    listaAdjuntos += adjuntos[0];
                }
                for (int i = 1; i < adjuntos.length; i++) {
                    listaAdjuntos += ", " + adjuntos[i];
                }
                this.adjuntos = listaAdjuntos;
            } else if (line.startsWith("TEXTO:")) {
                String texto = line.replace("TEXTO:", "").trim() + "\n";
                while ((line = bf.readLine()) != null) {
                    texto += line + "\n";
                }
                this.texto = texto;
            } else if (line.startsWith("ASUNTO:")) {
                // Tenemos en esta línea la tablaPath
                String path = line.replace("ASUNTO:", "").trim();
                this.asunto = path;
            }
        }
    }

    public String getTextoPersonalizado(TutorAcademico tAcademico, TutorProfesional tProfesional, Alumno alumno) {
        String text = this.texto;
        if (alumno != null && !alumno.esVacio()) {
            if (alumno.getApellidos() != null) {
                text = text.replace("{{APELLIDOS}}", alumno.getApellidos());
            }
            if (alumno.getNombre() != null) {
                text = text.replace("{{NOMBRE}}", alumno.getNombre());
            }
            if (alumno.getEmail() != null) {
                text = text.replace("{{emailalumno}}", alumno.getEmail());
            }
            if (alumno.getNumeroMatricula() != null) {
                text = text.replace("{{MATRICULA}}", alumno.getNumeroMatricula().toString());
            }

        }
        if (tAcademico != null && !tAcademico.esVacio()) {
            if (tAcademico.getNombre() != null) {
                text = text.replace("{{TA}}", tAcademico.getNombre());
            }
            if (tAcademico.getEmail() != null) {
                text = text.replace("{{emailalumno}}", tAcademico.getEmail());
            }
        }
        if (tProfesional != null && !tProfesional.esVacio()) {
            if (tProfesional.getEmpresa() != null) {
                text = text.replace("{{EMPRESA}}", tProfesional.getEmpresa());
            }
            if (tProfesional.getNombre() != null) {
                text = text.replace("{{TP}}", tProfesional.getNombre());
            }
            if (tProfesional.getEmail() != null) {
                text = text.replace("{{emailTP}}", tProfesional.getEmail());
            }
            if (tProfesional.getTelefono() != null) {
                text = text.replace("{{telTP}}", tProfesional.getTelefono());
            }
        }

        return null;
    }

    public String getTextoPersonalizado(TutorAcademico tAcademico, TutorProfesional tProfesional, LinkedList<Alumno> alumnos, boolean mostrarNumeroMatricula, boolean mostrarEmpresaAlumno) {
        String text = this.texto;
        // System.out.println(text);
        if (alumnos!=null) {
            String[] splitSecuencia = texto.split("_SECUENCIADEALUMNOS_");
            splitSecuencia[0] += "\n";
            for (Alumno alumno : alumnos) {
                splitSecuencia[0] += "\t" + alumno.toBeautifulStringSoloAlumno(mostrarNumeroMatricula, mostrarEmpresaAlumno) + "\n";
            }
            text = splitSecuencia[0] + splitSecuencia[1];
            // System.out.println(text);
        }
        if (tAcademico != null && !tAcademico.esVacio()) {
            if (tAcademico.getNombre() != null) {
                text = text.replace("{{TA}}", tAcademico.getNombre());
            }
            if (tAcademico.getEmail() != null) {
                text = text.replace("{{emailalumno}}", tAcademico.getEmail());
            }
        }
        if (tProfesional != null && !tProfesional.esVacio()) {
            if (tProfesional.getEmpresa() != null) {
                text = text.replace("{{EMPRESA}}", tProfesional.getEmpresa());
            }
            if (tProfesional.getNombre() != null) {
                text = text.replace("{{TP}}", tProfesional.getNombre());
            }
            if (tProfesional.getEmail() != null) {
                text = text.replace("{{emailTP}}", tProfesional.getEmail());
            }
            if (tProfesional.getTelefono() != null) {
                text = text.replace("{{telTP}}", tProfesional.getTelefono());
            }
        }

        return text;
    }
}
