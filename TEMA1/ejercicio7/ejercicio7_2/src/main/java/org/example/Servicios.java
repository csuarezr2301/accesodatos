package org.example;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Servicios {
    public static void pediruserycontrasena() {
        PreparedStatement st = null;
        ResultSet rs = null;
        Scanner tec = new Scanner(System.in);// Crear un objeto Scanner para leer la entrada del usuario

        if (Conexion.getConexion() != null) {
            String sql = "SELECT nombre, apellidos, num_accesos FROM usuarios WHERE usuario = ? AND contraseña = ?";
            try {
                st = Conexion.getConexion().prepareStatement(sql);

//ponemos dentro las preguntas para poder inicializar las varibales con lo que introducele usuario
                System.out.println("introduce tu nombre de usuario");
                String usuario = tec.nextLine();
                System.out.println("introducee tu contraseña");
                String contraseña = tec.nextLine();

                st.setString(1, usuario);
                st.setString(2, contraseña);
                rs = st.executeQuery();

                // Itera sobre el ResultSet
                if (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String apellidos = rs.getString("apellidos");
                    int num_accesos = rs.getInt("num_accesos");
                    System.out.println("sesion de usuario : " + nombre + " " + apellidos + " iniciada con exito");

                    aumentarnumeroacceso(usuario, contraseña);
                    System.out.println("ACCESO CONTABILIZADO CON EXITO");

                    obtener5preguntas();
                    masuntest(usuario);

                } else {
                    System.out.println("Usuario o contraseña incorrectos.");
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



    private static boolean aumentarnumeroacceso(String usuario, String contraseña) {
        PreparedStatement st = null;
        ResultSet rs = null;
        boolean correcto = false;

        if (Conexion.getConexion() != null) {
            String sql = "UPDATE usuarios SET num_accesos = num_accesos +1 WHERE usuario = ? AND contraseña = ?";
            try {
                st = Conexion.getConexion().prepareStatement(sql);
                st.setString(1, usuario);
                st.setString(2, contraseña);
                st.executeUpdate();
                correcto = true;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                cerrarStatement(st);
                Conexion.close_conexion();
            }
        }
        return correcto;
    }

    private static void obtener5preguntas() {
        PreparedStatement st = null;
        ResultSet rs = null;
        if (Conexion.getConexion() != null) {
            String sql = "SELECT enunciado, tipo, num_preg FROM preguntas WHERE tipo = 2 ORDER BY RAND() LIMIT 5";//PREGUNTAS DEL TIPO 2 LIMITADAS A 5 ALEATORIAS
            try {
                st = Conexion.getConexion().prepareStatement(sql);
                rs = st.executeQuery();

                // Itera sobre el ResultSet
                while (rs.next()) {
                    String enunciado = rs.getString("enunciado");
                    int tipo = rs.getInt("tipo");
                    int num_preg = rs.getInt("num_preg");

                    System.out.println("pregunta" + num_preg + ": " + enunciado);

                    preguntarrespuestas(num_preg);

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

    private static void preguntarrespuestas(int num_preg) {
        PreparedStatement st = null;
        ResultSet rs = null;
        if (Conexion.getConexion() != null) {
            String sql = "SELECT num_preg, num_resp, texto_resp,correcta FROM respuestas WHERE num_preg=?";
            try {
                st = Conexion.getConexion().prepareStatement(sql);
                st.setString(1, String.valueOf(num_preg));
                rs = st.executeQuery();

                // Itera sobre el ResultSet
                while (rs.next()) {
                    int num_resp = rs.getInt("num_resp");
                    String texto_resp = rs.getString("texto_resp"); // Asegúrate de que esto es correcto
                    int correcta = rs.getInt("correcta");

                    System.out.println("Respuesta:"+ texto_resp);
                    System.out.print("¿Es esta respuesta correcta (0 para Incorrecto, 1 para Correcto)? ");

                    Scanner teclado = new Scanner(System.in);
                    int respuestaUsuario = teclado.nextInt(); // Lee la respuesta del usuario

                    // Verificar si la respuesta del usuario coincide con la respuesta correcta
                    if (respuestaUsuario == correcta) {
                        System.out.println("Respuesta correcta!");
                    } else {
                        System.out.println("Respuesta incorrecta. La respuesta correcta era: " + correcta);
                    }
                    registrarrespuestas(num_preg);
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

    private static boolean registrarrespuestas(int num_preg) {
        PreparedStatement st1 = null;
        PreparedStatement st2 = null;

        boolean correcto = false;

        if (Conexion.getConexion() != null) {
            String sql1 = "UPDATE preguntas SET veces_formulada = veces_formulada +1, veces_acertada = veces_acertada +1 WHERE num_preg = ? ";
            String sql2 = "UPDATE respuestas SET veces_respondida = veces_respondida +1 WHERE num_preg = ? ";
            try {
                st1 = Conexion.getConexion().prepareStatement(sql1);
                st2 = Conexion.getConexion().prepareStatement(sql2);
                st1.setString(1, String.valueOf(num_preg));
                st2.setString(1, String.valueOf(num_preg));

                st1.executeUpdate();
                st2.executeUpdate();

                correcto = true;


            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                cerrarStatement(st1);
                cerrarStatement(st2);

            }
        }
        return correcto;
    }

    private static boolean masuntest(String usuario) {
        PreparedStatement st = null;
        boolean correcto = false;

        if (Conexion.getConexion() != null) {
            String sql1 = "UPDATE usuarios SET test_realizados = test_realizados +1 WHERE  usuario= ? ";

            try {
                st = Conexion.getConexion().prepareStatement(sql1);
                st.setString(1, String.valueOf(usuario));

                st.executeUpdate();

                correcto = true;


            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                cerrarStatement(st);

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
