package org.example;

import conexiones.conexionsql;

import java.sql.*;
import java.util.Scanner;



public class Main {
    public static void main(String[] args) {
        Scanner tec = new Scanner(System.in);// Crear un objeto Scanner para leer la entrada del usuario
        System.out.println("10 ultimos votos");

        Connection con = conexionsql.getConexion();
        Statement st = null;
        ResultSet rs = null;

        if (con != null) { //comprobamos conexion a la BD,Si el método getConexion() devuelve algo distinto de null, significa que la conexión fue establecida correctamente
            String sql = "SELECT usuario,fecha,cancion FROM votos ORDER BY fecha DESC LIMIT 10";

            try {
                // Crear un Statement que permite la actualización de ResultSets
                st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                rs = st.executeQuery(sql);
                // Iterar sobre los resultados del ResultSet
                while (rs.next()) {
                    String usuario = rs.getString("usuario");
                    String fecha = rs.getString("fecha");
                    String cancion = rs.getString("cancion");

                    // Mostrar información del voto
                    System.out.println("Usuario: " + usuario + ", Fecha: " + fecha + ", Canción: " + cancion);
                    System.out.println("Opciones:");
                    System.out.println("1. Modificar Usuario");
                    System.out.println("2. Eliminar");
                    System.out.print("Selecciona una opción (1/2): ");
                    int opcion = tec.nextInt(); // Usar nextInt() para leer la opción
                    switch (opcion) {
                        case 1 -> {
                            System.out.print("Nuevo usuario: ");
                            String newUser = tec.next();
                            // Verificar si el nuevo usuario existe en la tabla 'usuarios'
                            if (!usuarioExiste(con, newUser, usuario)) {
                                System.out.print("Contraseña: ");
                                String contrasena = tec.next(); // Leer contraseña
                                System.out.print("Nombre: ");
                                String nombre = tec.next(); // Leer nombre
                                System.out.print("Apellidos: ");
                                String apellidos = tec.next(); // Leer apellidos
                                System.out.print("Fecha de Nacimiento (YYYY-MM-DD): ");
                                String fechanacStr = tec.next(); // Leer fecha de nacimiento

                                // Agregar el nuevo usuario a la base de datos
                                agregarUsuario(con, newUser, contrasena, nombre, apellidos, fechanacStr);
                                System.out.println("Usuario '" + newUser + "' añadido a la base de datos.");
                            }
                        
                            rs.updateString("usuario", newUser); // Actualizar usuario en el ResultSet
                            rs.updateRow();
                            System.out.println("usario modificado con exito en la base de datos");
                            actualizarVotosUsuario(con, usuario, -1);
                            actualizarVotosUsuario(con, newUser, 1);
                        }
                        case 2 -> {
                            int filaActual = rs.getRow();
                            rs.deleteRow();
                            if (filaActual > 1) {
                                rs.absolute(filaActual - 1);
                            } else {
                                rs.beforeFirst();
                            }
                            actualizarVotosUsuario(con, usuario, -1);
                            actualizarVotosCancion(con, cancion, -1);
                        }
                        default -> {
                            System.out.println("Opción no válida");
                            con.rollback();
                            return;
                        }
                    //tec.nextLine(); // borra el búfer después de leer una entrada de número entero.

                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // Cerrar recursos
                try {
                    if (st != null) st.close();
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("ERROR al realizar la conexión");
        }
        tec.close(); // Cerrar el scanner
    }

    private static boolean usuarioExiste(Connection con, String newUser, String usuario) {
       
            String sql = "SELECT COUNT(*) FROM usuarios WHERE user = ?";
            try (PreparedStatement st = con.prepareStatement(sql)) {

                st.setString(1, usuario);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Retorna true si el usuario existe
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        return false; // Retorna false si no existe
        }


    private static void agregarUsuario(Connection con, String nuevoUsuario, String contrasena, String nombre, String apellidos, String fechanacStr) throws SQLException {
        String sql = "INSERT INTO usuarios (user, contraseña, nombre, apellidos, fechanac, numvotos) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, nuevoUsuario);
            st.setString(2, contrasena); // Guardar la contraseña
            st.setString(3, nombre); // Guardar el nombre
            st.setString(4, apellidos); // Guardar los apellidos
            st.setDate(5, Date.valueOf(fechanacStr)); // Guardar la fecha de nacimiento
            st.setInt(6, 1); // Inicializar numvotos a 1 al añadir un nuevo usuario
            st.executeUpdate(); // Ejecutar la inserción
        }
    }



    // Método para actualizar los votos de un usuario
    private static void actualizarVotosUsuario(Connection con, String usuario, int incremento) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE user = ?";
        try (PreparedStatement st = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            st.setString(1, usuario);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                int votos = rs.getInt("numVotos") + incremento;  // Calcular el nuevo total de votos
                rs.updateInt("numVotos", votos);
                rs.updateRow();
            }
        }
    }

    // Método para actualizar los votos de una canción
    private static void actualizarVotosCancion(Connection con, String cancion, int incremento) throws SQLException {
        String sql = "SELECT * FROM canciones WHERE numCancion = ?";
        try (PreparedStatement st = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            st.setInt(1, Integer.parseInt(cancion));
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                int votos = rs.getInt("total_votos") + incremento; // Se obtiene el número actual de votos y se le suma el incremento
                rs.updateInt("total_votos", votos);  // Se actualiza el campo 'total_votos' en el ResultSet con el nuevo valor
                rs.updateRow(); // Se guarda el cambio en la base de datos
            }
        }
    }
    public static void cerrarResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void cerrarStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

/*
ELIMINAR FILAS
ResultSet r=st.executeQuery("select * from canciones INNER JOIN grupos ON grupo=codgrupo");
while (r.next()) {
	num=r.getInt("numcancion");  // Se obtiene el número de la canción de la fila actual
	t=r.getString("titulo"); // Se obtiene el título de la canción
	nom=r.getString("nombre");
	System.out.println(num + "    titulo : " + t + "    grupo : " +nom );
	System.out.println("BORRAR (pulsa S) MANTENER (otra tecla)");  // Se solicita al usuario que decida si borrar la canción o mantenerla
	String cad=t.readLine(); // Se lee la entrada del usuario desde la consola
	if(cad.toUpperCase().equalsIgnoreCase("S")) { // Se verifica si el usuario ha pulsado 'S' (sin importar mayúsculas o minúsculas)
		r.deleteRow();  // Se elimina la fila actual del ResultSet
	}

 */