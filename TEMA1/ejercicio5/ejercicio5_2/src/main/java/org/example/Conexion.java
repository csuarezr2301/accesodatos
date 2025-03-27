package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexion {
    public static Connection conexion = null;

    public static Connection getConexion() {

        Properties properties = new Properties();

        String url = "jdbc:postgresql://192.168.0.27:5432/heroes";//donde va local host en clase se mete el numero de la maquina virtual que creamos

        properties.put("user", "postgres");
        properties.put("password", "postgres");
        properties.put("allowMultiQueries", "true");
        properties.put("useSSL", "false");
        properties.put("useUnicode", "true");
        properties.put("allowPublicKeyRetrival", "true");
        properties.put("serverTimezone", "UTC");

        try {
            conexion = DriverManager.getConnection(url, properties);

        } catch (Exception e) {
            throw new RuntimeException(e);

        }return conexion;
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


