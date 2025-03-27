package org.example;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class servicio {
    public static void mostrardatos() {

    }

    public static boolean eliminaractuaciones(String actuaciones) {
        boolean correcto = false; // Variable para indicar si la operación fue correcta o no
        PreparedStatement st = null;

        //eliminar primero los votos asociados a las canciones del grupo y luego eliminar las canciones
        if (Conexion.getConexion() != null) {
            String sql = "DELETE FROM votos WHERE cancion IN (SELECT numCancion FROM canciones INNER JOIN grupos ON grupo=codgrupo WHERE nombre = ?)";
            try {
                st = Conexion.getConexion().prepareStatement(sql);
                st.setString(1, actuaciones); // Asignar el nombre del grupo al primer parámetro de la consulta
                st.executeUpdate(); // Ejecutar la eliminación de los votos

                sql = "DELETE FROM canciones WHERE grupo = (SELECT codgrupo FROM grupos WHERE nombre = ?)";// SQL para eliminar las canciones asociadas al grupo
                st = Conexion.getConexion().prepareStatement(sql);
                st.setString(1, actuaciones);
                st.executeUpdate();
                correcto = true; // Si ambas operaciones se completaron correctamente, marcar como correcto
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                cerrarStatement(st);
                Conexion.close_conexion();
            }
        }
        return correcto;
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
    }
}



}