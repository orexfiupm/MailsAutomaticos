package pruebaPreliminar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class PruebaPreliminar02ExcelConnector {
    
    public static Connection getConnection(String filename) throws Exception {
        //String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
        //String url = "jdbc:odbc:excelDB";
        //String username = "";
        //String password = "";
        //Class.forName(driver);
        //return DriverManager.getConnection(url, username, password);

        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        String myDB = "jdbc:odbc:Driver={Microsoft Excel Driver (*.xls)};DBQ=" + filename + ";" + "DriverID=22;READONLY=false";
        Connection con = DriverManager.getConnection(myDB, "", "");

        return con;


    }

	public static void main(String[] args) throws Exception {
		System.out.println("Working!");

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
    
        //conn = getConnection("/home/luciano/Dropbox/actualesUPM/beca/eclipse-workspace/PruebasPreliminaresEnvioCorreosPracticum/diseases.xls");
        conn = getConnection("./PruebasPreliminaresEnvioCorreosPracticum/diseases.xls");
        stmt = conn.createStatement();
        String excelQuery = "select symptoms_1 from [Sheet1$]";
        rs = stmt.executeQuery(excelQuery);
    
        while (rs.next()) {
          //Fill the data for your drop-down list
          System.out.println(rs.toString());
        }
    
        rs.close();
        stmt.close();
        conn.close();
	}
}
