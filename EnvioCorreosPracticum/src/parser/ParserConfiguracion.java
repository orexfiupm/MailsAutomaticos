package parser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import datos.Alumno;
import datos.TutorAcademico;
import datos.TutorProfesional;

public class ParserConfiguracion {
    private String adjuntos = "";
    private String texto;
    private String asunto;

    public ParserConfiguracion(String path) throws FileNotFoundException {
        // BufferedReader bf = new BufferedReader(new FileReader(path));
        BufferedReader bf;
        try {
            // bf = new BufferedReader(new InputStreamReader(new FileInputStream(path),
            // "CP1252"));
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

    public String getTextoPersonalizado(TutorAcademico tAcademico, TutorProfesional tProfesional, LinkedList<Alumno> alumnos) {
        String[] splStrings = texto.split("\\|\\|");
        List<String> splStringsList = Arrays.asList(splStrings);
        List<String> splStringsFinalList = new LinkedList<>();
        for (String unit : splStringsList) {
            System.out.println("Unit es: " + unit);
            if (unit.startsWith("SECUENCIADEALUMNOS")) {
                if (alumnos!=null) {
                    String[] elemsAlumno = unit.replace("SECUENCIADEALUMNOS", "").replace("[", "").replace("]", "").split(",");
                    String beautifulStringAlumno = "\n";
                    for (Alumno a:alumnos) {
                        boolean nombre = false;
                        boolean first = true;
                        boolean directivaAlumno = false;
                        for (String elemAlumno:elemsAlumno) {
                            if ((nombre && elemAlumno.equals("APELLIDOS")) || first) {
                                if (!first) {
                                    beautifulStringAlumno += " ";
                                }
                                first = false;
                            }
                            else {
                                beautifulStringAlumno += ", ";
                            }

                            if (elemAlumno.equals("NOMBRE")) {
                                if (!directivaAlumno) {
                                    beautifulStringAlumno += "alumno: ";
                                    directivaAlumno = true;
                                }
                                beautifulStringAlumno += a.getNombre();
                                nombre = true;
                            }
                            if (elemAlumno.equals("APELLIDOS")) {
                                if (!directivaAlumno) {
                                    beautifulStringAlumno += "alumno: ";
                                    directivaAlumno = true;
                                }
                                beautifulStringAlumno += a.getApellidos();
                            }
                            if (elemAlumno.equals("mailAlumno")) {
                                beautifulStringAlumno += "email: " + a.getEmail();
                            }
                            if (elemAlumno.equals("MATRICULA")) {
                                beautifulStringAlumno += "matrícula: " + a.getNumeroMatricula();
                            }
                            if (elemAlumno.equals("EMPRESA")) {
                                beautifulStringAlumno += "empresa: " + a.getEmpresa();
                            }
                        }
                        beautifulStringAlumno += "\n";
                    }
                    splStringsFinalList.add(beautifulStringAlumno);
                }
            }
            else {
                if (unit.equals("TA")) {
                    if (tAcademico!=null) {
                        unit = tAcademico.getNombre();
                    }
                }
                else if (unit.equals("mailTA")) {
                    if (tAcademico!=null) {
                        unit = tAcademico.getEmail();
                    }
                }
                else if (unit.equals("TP")) {
                    if (tProfesional!=null) {
                        unit = tProfesional.getNombre();
                    }
                }
                else if (unit.equals("mailTP")) {
                    if (tProfesional!=null) {
                        unit = tProfesional.getEmail();
                    }
                }
                else if (unit.equals("telTP")) {
                    if (tProfesional!=null) {
                        unit = tAcademico.getTelefono();
                    }
                }
                else if (unit.equals("EMPRESA")) {
                    if (tProfesional!=null) {
                        unit = tProfesional.getEmpresa();
                    }
                }
                splStringsFinalList.add(unit);
            }
        }
        splStringsFinalList.removeIf(i -> i.equals("\\|\\|"));

        String textoRellenado = "";
        for (String unit : splStringsFinalList) {
            textoRellenado += unit;
        }
        return textoRellenado;
    }

    public String getTextoPersonalizado(TutorAcademico tAcademico, TutorProfesional tProfesional, Alumno alumno) {
        String[] splStrings = texto.split("\\|\\|");
        List<String> splStringsList = Arrays.asList(splStrings);
        List<String> splStringsFinalList = new LinkedList<>();
        for (String unit : splStringsList) {
            if (unit.equals("NOMBRE")) {
                if (alumno != null) {
                    unit = alumno.getNombre();
                }
            }
            else if (unit.equals("APELLIDOS")) {
                if (alumno != null) {
                    unit = alumno.getApellidos();
                }
            }
            else if (unit.equals("mailAlumno")) {
                if (alumno != null) {
                    unit = alumno.getEmail();
                }
            }
            else if (unit.equals("MATRICULA")) {
                if (alumno != null) {
                    unit = alumno.getNumeroMatricula().toString();
                }
            }
            else if (unit.equals("TA")) {
                if (tAcademico!=null) {
                    unit = tAcademico.getNombre();
                }
            }
            else if (unit.equals("mailTA")) {
                if (tAcademico!=null) {
                    unit = tAcademico.getEmail();
                }
            }
            else if (unit.equals("TP")) {
                if (tProfesional!=null) {
                    unit = tProfesional.getNombre();
                }
            }
            else if (unit.equals("mailTP")) {
                if (tProfesional!=null) {
                    unit = tProfesional.getEmail();
                }
            }
            else if (unit.equals("telTP")) {
                if (tProfesional!=null) {
                    unit = tAcademico.getTelefono();
                }
            }
            else if (unit.equals("EMPRESA")) {
                if (tProfesional!=null) {
                    unit = tProfesional.getEmpresa();
                }
                else if (alumno!=null) {
                    unit = alumno.getEmpresa();
                }
            }
            splStringsFinalList.add(unit);
            
        }
        splStringsFinalList.removeIf(i -> i.equals("\\|\\|"));

        String textoRellenado = "";
        for (String unit : splStringsFinalList) {
            textoRellenado += unit;
        }
        return textoRellenado;
    }
}