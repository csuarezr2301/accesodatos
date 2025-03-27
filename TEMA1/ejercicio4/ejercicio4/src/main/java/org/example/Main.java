package org.example;

import java.sql.SQLException;
import java.util.Scanner;

import conexionsql.conexionsql;
import servicios.Servicio;


public class Main {
    public static void main(String[] args) throws SQLException {
        Servicio servicios = new Servicio();  // Crear instancia de Servicio

        // Comprobamos si la conexión a la BD es válida
        if (conexionsql.getConexion() != null) {
            System.out.println("Conexión establecida");

            // Pedir al usuario el número de grupo
            System.out.println("Introduce el número de grupo que quieres ver: ");
            int grupo = new Scanner(System.in).nextInt();

            // Mostrar las canciones del grupo
            System.out.println("Canciones del grupo: " + grupo);
            Servicio.vercancionesgrupoyvotar(grupo);

            // Cerrar la conexión a la base de datos
            conexionsql.close_conexion();
        } else {
            System.out.println("No se pudo establecer la conexión a la base de datos.");
        }
    }
}





