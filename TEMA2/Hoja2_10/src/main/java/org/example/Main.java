package org.example;


import basededatos.GestorBaseDatos;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Cancion;
import model.Grupo;
import model.Usuario;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        // Crear la fábrica de EntityManager
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("miUnidadDePersistencia");
        // Crear el EntityManager
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        // Crear un objeto Scanner para leer la entrada por consola
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        // Bucle del menú
        while (!salir) {
            mostrarMenu();
            int opcion = scanner.nextInt();
            scanner.nextLine();  // Limpiar el buffer del scanner

            switch (opcion) {
                case 1:
                    // Datos de alumno
                    listadousuarios(entityManager, scanner);
                    break;
                case 2:
                    // Datos de curso
                    gruposncanciones(entityManager, scanner);
                    break;
                case 3:
                    // Listado de cursos
                    cancionesxgrupo(entityManager,scanner);
                    break;
                case 4:
                    // Listado de alumnos de un curso
                    gruposlocalidad1disco(entityManager, scanner);
                    break;
                case 5:
                    gruposporlocalidad(entityManager, scanner);
                    break;
                case 6:
                    actualizarGrupo(entityManager,scanner);
                    break;
                case 7:
                    borrargrupo(entityManager,scanner);
                    break;
                case 0:
                    // Salir
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, ingrese una opción correcta.");
            }
        }
        entityManager.getTransaction().commit();


        // Cerrar recursos
        entityManager.close();
        entityManagerFactory.close();
        scanner.close();
    }

    private static void borrargrupo(EntityManager entityManager, Scanner scanner) {
        System.out.println("Introduce el ID de un grupo BORRAR");
        int id = scanner.nextInt();

     try {
         // Eliminar los votos asociados a las canciones del grupo
         entityManager.createQuery(
                         "DELETE FROM Voto v WHERE v.cancion.id IN (SELECT c.id FROM Cancion c WHERE c.grupo.codgrupo = :id)")
                 .setParameter("id", id)
                 .executeUpdate();
         /*
        WHERE v.cancion.id IN: Especifica que el id de los cancion relacionados con cada uno Voto debe coincidir con el id de la subconsulta.
        (SELECT c.id FROM Cancion c WHERE c.grupo.codgrupo = :id): selecciona el número id de las canciones (Cancion) donde es grupo.codgrupo es igual al parámetro (:id).
          */

         // Eliminar las canciones asociadas al grupo
         entityManager.createQuery(
                         "DELETE FROM Cancion c WHERE c.grupo.codgrupo = :id")
                 .setParameter("id", id)
                 .executeUpdate();

         // Eliminar el grupo
         entityManager.createQuery(
                         "DELETE FROM Grupo g WHERE g.codgrupo = :id")
                 .setParameter("id", id)
                 .executeUpdate();

         // Confirmar la transacción
         entityManager.getTransaction().commit();
         System.out.println("Grupo y sus registros asociados eliminados correctamente.");
     } catch (Exception e) {
         // En caso de error, hacer rollback
         if (entityManager.getTransaction().isActive()) {
             entityManager.getTransaction().rollback();
         }
         System.out.println("Error al eliminar el grupo y sus registros asociados: " + e.getMessage());
     }
    }
//DA ERROR DE CASCADAS

    private static void actualizarGrupo(EntityManager entityManager, Scanner scanner) {
        // Leer datos del usuario
        System.out.println("Introduce el nombre de un grupo para actualizar su nombre y localidad:");
        String nombre = scanner.nextLine();

       //Verificar si el grupo existe
        Grupo grupoExistente = entityManager.createQuery("SELECT g FROM Grupo g WHERE g.nombre = :nombre", Grupo.class)
                .setParameter("nombre", nombre)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);

        if (grupoExistente == null) {
            System.out.println("No se ha encontrado un grupo con el nombre: " + nombre);
            return;
        }

        // Solicitar nuevos datos
        System.out.println("Introduce el nuevo nombre:");
        String nuevoNombre = scanner.nextLine();

        System.out.println("Introduce la nueva localidad:");
        String nuevaLocalidad = scanner.nextLine();


        try {
            // Actualizar los campos del grupo
            entityManager.createQuery("UPDATE Grupo g SET g.nombre = :nuevoNombre, g.localidad = :nuevaLocalidad WHERE g.nombre = :nombre")
                    .setParameter("nuevoNombre", nuevoNombre)
                    .setParameter("nuevaLocalidad", nuevaLocalidad)
                    .setParameter("nombre", nombre)
                    .executeUpdate();

            // Confirmar la transacción
            entityManager.getTransaction().commit();

            // Mostrar mensaje de éxito
            System.out.println("Grupo actualizado correctamente.");

        } catch (Exception e) {
            // En caso de error, hacer rollback
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.out.println("Error al actualizar el grupo: " + e.getMessage());
        }
    }


    private static void listadousuarios(EntityManager entityManager, Scanner scanner) {

        // Crear la instancia de GestorBaseDatos
        GestorBaseDatos gestor = new GestorBaseDatos(entityManager);

        System.out.println("introduce un año");
        int ano = Integer.parseInt(new Scanner(System.in).nextLine());

        List<Usuario> usuarios = gestor.usuariosNacidosAnoMayor(ano);
        for (Usuario usuario : usuarios) {
            System.out.println(usuario.getNombre() + " " + usuario.getApellidos());
        }

    }

    private static void gruposncanciones(EntityManager entityManager, Scanner scanner) {
        GestorBaseDatos gestor = new GestorBaseDatos(entityManager);
        System.out.println("introduce un numero");
        String numero = scanner.nextLine();

        List<Grupo> grupos = gestor.gruposConNumeroCancionesMayor(Integer.parseInt(numero));
        // Mostrar los nombres de los grupos
        if (grupos.isEmpty()) {
            System.out.println("No hay grupos con más de " + numero + " canciones.");
        } else {
            System.out.println("Grupos con más de " + numero + " canciones:");
            for (Grupo grupo : grupos) {
                System.out.println(grupo.getNombre());
            }
        }
    }

    private static void cancionesxgrupo(EntityManager entityManager, Scanner scanner) {

        GestorBaseDatos gestor = new GestorBaseDatos(entityManager);
        System.out.println("introduce el nombre de varios grupos separados por comas");
        String input = scanner.nextLine();

        // Dividir la entrada en una lista de nombres
        List<String> nombres = Arrays.asList(input.split(","));
        // Eliminar posibles espacios en blanco de cada nombre
        nombres.stream().map(String::trim).collect(Collectors.toList());

        // Obtener las canciones de los grupos
        List<Object[]> canciones = gestor.getCancionesGrupos(nombres);

        // Verificar si se han encontrado canciones
        if (canciones.isEmpty()) {
            System.out.println("No hay canciones de estos grupos.");
        } else {
            // Mostrar los resultados
            System.out.println("Canciones de los grupos " + nombres + ":");
            for (Object[] resultado : canciones) {
                String tituloCancion = (String) resultado[0];  // Título de la canción
                String nombreGrupo = (String) resultado[1];    // Nombre del grupo
                System.out.println("Grupo: " + nombreGrupo + " | Canción: " + tituloCancion);
            }
        }
    }

    private static void gruposlocalidad1disco(EntityManager entityManager, Scanner scanner) {
        GestorBaseDatos gestor = new GestorBaseDatos(entityManager);
        // Pedir al usuario la localidad y el año
        System.out.println("Introduce la localidad:");
        String localidad = scanner.nextLine();

        System.out.println("Introduce el año:");
        int anno = scanner.nextInt();
        scanner.nextLine();  // Limpiar el buffer

        List<Grupo> grupos = gestor.gruposLocalidadAno(localidad, anno);
        // Mostrar los resultados
        if (grupos.isEmpty()) {
            System.out.println("No hay grupos de esa localidad que hayan grabado su primer disco antes de " + anno + ".");
        } else {
            System.out.println("Grupos de la localidad " + localidad + " que han grabado su primer disco antes del año " + anno + ":");
            for (Grupo grupo : grupos) {
                System.out.println(grupo.getNombre() + " (Año de grabación: " + grupo.getAnnoGrab() + ")");
            }
        }
    }

    private static void gruposporlocalidad(EntityManager entityManager, Scanner scanner) {
        // Realizamos la consulta para obtener las localidades
        List<Object[]> resultados = entityManager.createQuery(
                        "SELECT g.localidad, COUNT(g) FROM Grupo g GROUP BY g.localidad", Object[].class) //Usamos Object[]como tipo de resultado porque estamos seleccionando dos columnas (la localidad y el número de grupos).
                .getResultList();

        // Verificamos si hay resultados
        if (resultados.isEmpty()) {
            System.out.println("No hay grupos en la base de datos.");
        } else {
            // Recorremos los resultados y mostramos localidad y el número de grupos
            for (Object[] resultado : resultados) {
                String localidad = (String) resultado[0];  // La localidad
                Long conteoGrupos = (Long) resultado[1];   // El número de grupos
                System.out.println("Localidad: " + localidad + " | Número de grupos: " + conteoGrupos);
            }
        }
    }
    //seleccionando atributos específicos de la entidad, no la entidad completa.


    private static void mostrarMenu() {
        System.out.println("Menú:");
        System.out.println("1.- Listado de usuarios nacidos a partir de año");
        System.out.println("2.- Grupos que tienen mas de N canciones");
        System.out.println("3.- Canciones de grupos");
        System.out.println("4.- Grupos de localidad con primer disco en año antes de año");
        System.out.println("5.- Grupos por localidad");
        System.out.println("6.- actualizar grupo");
        System.out.println("7.- borrar grupo ");


        System.out.println("0.- Salir");

        System.out.print("Elija una opción: ");
    }

}
