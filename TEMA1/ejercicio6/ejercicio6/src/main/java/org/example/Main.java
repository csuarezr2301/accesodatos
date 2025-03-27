package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (Conexion.getConexion() != null) { //comprobamos conexion a la BD,Si el método getConexion() devuelve algo distinto de null, significa que la conexión fue establecida correctamente
            System.out.println("Conexión establecida");
            servicio.mostrardatos();
            System.out.println();
            programaPrincipal(); //llamamos al menu de abajo
            System.out.println();
            System.out.println("DATOS ACTUALIZADOS");
            servicio.mostrardatos();
            Conexion.close_conexion();
        } else {
            System.out.println("ERROR al realizar la conexión");

        }
    }

    private static void programaPrincipal() {
           boolean salir = false;
            while (!salir) {
                System.out.println("MENU DE OPCIONES");
                System.out.println("""
                1.- Añadir personaje
                2.- Añadir película
                3.- Añadir actuación
                4.- Eliminar actuación
                0.- Salir
                Elige una opción:
                """);
                System.out.print("Introduce una opción: ");
                int opcion = new Scanner(System.in).nextInt();
                switch (opcion) {
                    case 1 -> addcharacter();
                    case 2 -> addfilm();
                    case 3 -> addact();
                    case 4 -> deleteact();
                    case 0 -> {
                        System.out.println("Saliendo...");
                        salir = true;
                    }
                    default -> System.out.println("Opción no válida. Intente de nuevo.");
                }
            }
        }

    private static void deleteact() {
        System.out.println("Eliminar Actuaciones");
        System.out.println("Introduce la actuacion a eliminar: ");
        String actuaciones = new Scanner(System.in).nextLine();
        if (servicio.eliminaractuaciones(actuaciones)) {
            System.out.println("actuacion eliminada");
        } else {
            System.out.println("No se pudo eliminar la actuacion");
        }
    }


    private static void addact() {
    }

    private static void addfilm() {
    }

    private static void addcharacter() {
    }
}
