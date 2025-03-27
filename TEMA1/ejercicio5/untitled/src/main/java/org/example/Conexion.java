package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexion {
    public static Connection conexion=null;
        public static Connection getConexion () {

            Properties properties = new Properties();
            Connection con = null;
            String url = "jdbc:mysql://localhost:3307/";
            properties.put("user", "root");
            properties.put("password", "mysql");
            properties.put("allowMultiQueries", "true");
            properties.put("useSSL", "false");
            properties.put("useUnicode", "true");
            properties.put("allowPublicKeyRetrival", "true");
            properties.put("serverTimezone", "UTC");

            try {
                con = DriverManager.getConnection(url, properties);
                System.out.println("Conexión a SQLite establecida.");
            } catch (SQLException e) {
                throw new RuntimeException(e);

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

