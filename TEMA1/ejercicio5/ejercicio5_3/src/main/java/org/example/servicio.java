package org.example;

import java.sql.*;

public class servicio {
    public static void numjornadamay() {
        PreparedStatement st = null;
        ResultSet rs = null;
        if (Conexion.getConexion() != null) {
            String sql = "SELECT max(numjornada) AS numnewjornada FROM partidos"; //AS numnewjornada está asignando un alias a la columna resultante. Es decir, el resultado de max(numjornada)se etiquetará como numnewjornada
            try {
                st = Conexion.getConexion().prepareStatement(sql);
                rs = st.executeQuery();

                // Itera sobre el ResultSet
                while (rs.next()) {
                    int numjornadamayor = rs.getInt("numnewjornada");
                    System.out.println("numero de jornada mayor: " + numjornadamayor);
                    generapartidos(numjornadamayor);


                }
            } catch (SQLException e) {

                e.printStackTrace(); // Imprime la excepción si hay un error
            } finally {
                cerrarResultSet(rs);
                cerrarStatement(st);


            }
        } else {
            System.out.println("Error: No se pudo establecer conexión con la base de datos.");
        }
    }

    private static void generapartidos(int numjornada) {
        CallableStatement stm = null;

        if (Conexion.getConexion() != null) {
            String sql = "{call siguientejornada(?)}";

            try {
                stm = Conexion.getConexion().prepareCall(sql);
                stm.setInt(1, numjornada);
                stm.execute();

                 mostrarpartidos(numjornada + 1);

            } catch (SQLException e) {
                e.printStackTrace(); // Imprime la excepción si hay un error
            } finally {
                cerrarStatement(stm);


            }
        } else {
            System.out.println("Error: No se pudo establecer conexión con la base de datos.");
        }
    }

    private static void mostrarpartidos(int numnewjornada) {
        PreparedStatement st = null;
        ResultSet rs = null;
        if (Conexion.getConexion() != null) {
            String sql = "SELECT *  FROM partidos WHERE numjornada = ?";
            try {
                st = Conexion.getConexion().prepareStatement(sql);

                st.setInt(1, numnewjornada);
                rs = st.executeQuery();
                System.out.println("Partidos de la jornada " + numnewjornada + ":");

                // Itera sobre el ResultSet
                while (rs.next()) {
                    int numjornada = rs.getInt("numjornada");
                    int numpartido = rs.getInt("numpartido");
                    String eqloc = rs.getString("eqloc");
                    String eqvis = rs.getString("eqvis");
                    Date fecha = rs.getDate("fecha");
                    System.out.println("numero de jornada: " + numjornada +
                            " | numpartido: " + numpartido +
                            " | equipo local: " + eqloc +
                            " | equipo visitante: " + eqvis +
                            " | fecha: " + fecha);

                }
            } catch (SQLException e) {
                e.printStackTrace(); // Imprime la excepción si hay un error
            } finally {
                cerrarResultSet(rs);
                cerrarStatement(st);
                Conexion.close_conexion(); // Cierra la conexión

            }
        } else {
            System.out.println("Error: No se pudo establecer conexión con la base de datos.");
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
        }
    }
}


