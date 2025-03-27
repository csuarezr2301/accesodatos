package org.example;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexion {
    public static Connection conexion=null;

    public static Connection getConexion() {
        Connection con = null;

        String password = "root";
        String usuario = "root";
        String url = "jdbc:mysql://localhost:3307/";
        try {
            con = DriverManager.getConnection(url + "?useSSL=false&allowMultiQueries=true", usuario, password);
            if (con != null) {

                //System.out.println("Conectado a la base de datos");
            }
        } catch (SQLException e) {
            System.out.println("ERROR " + e.getErrorCode() + ":" + e.getMessage());
        }
        return con;
    }


    public static void close_conexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                conexion = null;
                System.out.println("Conexión cerrada.");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
