package org.example;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Servicios {

    public static void textopreguntas() {
        PreparedStatement st = null;
        ResultSet rs = null;
        Scanner tec = new Scanner(System.in);// Crear un objeto Scanner para leer la entrada del usuario

        if (Conexion.getConexion() != null) {
            String sql = "SELECT enunciado, num_preg FROM preguntas WHERE categoria = ?";
            try {
                st = Conexion.getConexion().prepareStatement(sql);

                System.out.print("Introduce la categoría de preguntas que quieres consultar: ");
                String categoria = tec.nextLine(); // Leer la categoría de preguntas desde la entrada del usuario

                st.setString(1, categoria); //asigna el grupo al primer resultado
                rs = st.executeQuery();

                // Itera sobre el ResultSet
                while (rs.next()) {
                    int num_preg = rs.getInt("num_preg");
                    String pregunta = rs.getString("enunciado");
                    System.out.println("nº de pregunta: " + num_preg + ", Pregunta: " + pregunta); // Mostrar la pregunta

                    // Mostrar las respuestas para la pregunta actual
                    numtexpreguntas(num_preg); //añadimos el metodo y entre parenteesis llamamos al conector entre este metodo y el texto preguntas, en este caso el nº de la preg

                    // Esperar a que el usuario presione ENTER para continuar
                    System.out.println("Presiona ENTER para continuar...");
                    tec.nextLine();
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


public static void numtexpreguntas(int num_preg) {// añadimos la variable en el metodo
    PreparedStatement st = null;
    ResultSet rs = null;
    if (Conexion.getConexion() != null) {
        String sql = "SELECT num_resp, texto_resp FROM respuestas WHERE num_preg = ?";
        try {
            st = Conexion.getConexion().prepareStatement(sql);

            st.setInt(1, num_preg); //asigna num_preg al primer resultado
            rs = st.executeQuery();

            while (rs.next()) {
                int idRespuesta = rs.getInt("num_resp");
                String textoRespuesta = rs.getString("texto_resp");
                System.out.println("Respuesta " + idRespuesta + ": " + textoRespuesta );
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