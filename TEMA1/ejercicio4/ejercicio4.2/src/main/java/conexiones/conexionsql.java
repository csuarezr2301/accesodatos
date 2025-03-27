package conexiones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

//conexion a base de datos MYSQL myworkbench
public class conexionsql {
    public static Connection conexion=null;

    public static Connection getConexion () {

        Properties properties = new Properties();
        Connection con = null;
        String url = "jdbc:mysql://localhost:3307/concursomusica";
        properties.put("user", "root");
        properties.put("password", "root");
        properties.put("useSSL", "false");
        properties.put("allowPublicKeyRetrival", "true");
        properties.put("serverTimezone", "UTC");

        try {
            con = DriverManager.getConnection(url, properties);

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