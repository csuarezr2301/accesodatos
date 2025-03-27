package servicios;

import conexionsql.conexionsql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Servicio {

    public static void vercancionesgrupoyvotar(int grupo) {
        PreparedStatement st = null;
        ResultSet rs = null;
        String sql = "SELECT numCancion, titulo, total_votos FROM canciones WHERE grupo = ?";

        Connection connection = conexionsql.getConexion();

        if (connection != null) {
            try {
                st = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                st.setInt(1, grupo); // Asignar el valor del grupo en la consulta
                rs = st.executeQuery();

                boolean hayCanciones = false;

                while (rs.next()) {
                    hayCanciones = true; // Al menos hay una canción
                    int numCancion = rs.getInt("NumCancion");
                    String titulo = rs.getString("titulo");
                    int totalVotos = rs.getInt("total_votos");

                    // Mostrar información de la canción
                    System.out.println("Canción #" + numCancion + ": " + titulo + " | Votos: " + totalVotos);

                    // Preguntar si desea incrementar los votos
                    System.out.print("¿Deseas incrementar los votos? (si/no): ");
                    String respuesta = new Scanner(System.in).nextLine();

                    if (respuesta.equalsIgnoreCase("si")) {
                        // Incrementar los votos
                        totalVotos++;
                        rs.updateInt("total_votos", totalVotos); // Actualizar el valor en el ResultSet
                        rs.updateRow(); // Persistir el cambio en la base de datos
                        System.out.println("Votos actualizados a: " + totalVotos);
                    }
                }
                if (!hayCanciones) {
                    System.out.println("No hay canciones para el grupo indicado.");
                }
                // Cerrar recursos
                try {
                    if (rs != null) rs.close();
                    if (st != null) st.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("No se pudo establecer la conexión a la base de datos.");
        }
    }


private static void cerrarStatement(PreparedStatement st) {
    if (st != null) {
        try {
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

private static void cerrarResultSet(ResultSet rs) {
    if (rs != null) {
        try {
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }}
}
