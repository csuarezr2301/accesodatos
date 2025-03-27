package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jdk.swing.interop.SwingInterOpUtils;
import org.example.modelo.Alumno;
import org.example.modelo.Curso;
import org.example.modelo.Profesor;

import javax.xml.namespace.QName;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("miUnidadDePersistencia");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

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
                    mostrarDatosAlumno(entityManager, scanner);
                    break;
                case 2:
                    // Datos de curso
                    mostrarDatosCurso(entityManager, scanner);
                    break;
                case 3:
                    // Listado de cursos
                    mostrarListadoCursos(entityManager);
                    break;
                case 4:
                    // Listado de alumnos de un curso
                    mostrarAlumnosDeCurso(entityManager, scanner);
                    break;
                case 5:
                    modificarnombrecurso(entityManager, scanner);
                    break;
                case 6:
                    añadircurso(entityManager, scanner);
                    break;
                case 7:
                    añadiromodificarcurso(entityManager, scanner);
                    break;
                case 8:
                    modificartutordecurso(entityManager, scanner);
                    break;
                case 9:
                    eliminaralumno(entityManager, scanner);
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

    private static void eliminaralumno(EntityManager entityManager, Scanner scanner) {

        System.out.println("Introduce el ID del alumno a eliminar:");
        int idAlumno = scanner.nextInt();
        scanner.nextLine(); //limpiar buffer

        Alumno alumno = entityManager.find(Alumno.class, idAlumno);

        if (alumno != null) {
            try {
                entityManager.remove(alumno);  // Remove the student from the database
                entityManager.getTransaction().commit();  // Commit the transaction
                System.out.println("Alumno eliminado correctamente.");
            } catch (Exception e) {
                System.out.println("Error al eliminar al alumno: " + e.getMessage());
                entityManager.getTransaction().rollback();  // Rollback the transaction if an error occurs
            }
        } else {
            System.out.println("Alumno no encontrado.");
        }
    }

    private static void modificartutordecurso(EntityManager entityManager, Scanner scanner) {

        System.out.println("Introduce el ID del curso:");
        int idCurso = scanner.nextInt();
        scanner.nextLine(); //limpiar buffer

        Curso curso = entityManager.find(Curso.class, idCurso);

        if (curso != null) {
            System.out.println("Curso actual: " + curso.getNombre());
            System.out.println("Tutor actual: " + curso.getProfesor().getNombre());

            // Solicitar el ID del nuevo profesor (tutor)
            System.out.println("Introduce el ID del nuevo profesor (tutor):");
            int idProfesor = scanner.nextInt();
            scanner.nextLine(); //limpiar buffer

            // Buscar el profesor por ID
            Profesor nuevoProfesor = entityManager.find(Profesor.class, idProfesor);

            if (nuevoProfesor != null) {
                // Verificar si el profesor ya está asignado a otro curso
                Query query = entityManager.createQuery("SELECT c FROM Curso c WHERE c.profesor = :profesor");
                query.setParameter("profesor", nuevoProfesor);

                List<Curso> cursosConProfesor = query.getResultList();
                if (!cursosConProfesor.isEmpty()) {
                    System.out.println("El profesor ya es tutor de otro curso. No se puede asignar.");
                } else {
                    // Asignar el nuevo profesor al curso
                    curso.setProfesor(nuevoProfesor);
                    entityManager.merge(curso);  // Actualizar el curso con el nuevo tutor
                    entityManager.getTransaction().commit();  // Confirmar la transacción
                    System.out.println("Tutor actualizado correctamente a: " + nuevoProfesor.getNombre());
                }
            } else {
                System.out.println("Profesor no encontrado.");
            }
        } else {
            System.out.println("Curso no encontrado.");
        }
    }


    private static void añadiromodificarcurso(EntityManager entityManager,  Scanner scanner) {

        System.out.println("Introduce el ID del curso (4 caracteres):");
        String idCurso = scanner.nextLine();
        scanner.nextLine(); //limpiar buffer

        Curso curso = entityManager.find(Curso.class, idCurso);

        if (curso != null) {
            // El curso ya existe, modificar sus datos
            System.out.println("Curso encontrado: " + curso.getNombre());
            System.out.println("Introduce el nuevo nombre del curso:");
            String nuevoNombre = scanner.nextLine();
            curso.setNombre(nuevoNombre);
            entityManager.getTransaction().commit();


            try {
                entityManager.merge(curso);  // Usamos merge para actualizar
                entityManager.getTransaction().commit();
                System.out.println("Curso modificado correctamente.");
            } catch (Exception e) {
                System.out.println("Error al modificar el curso: " + e.getMessage());
                entityManager.getTransaction().rollback();
            }
        } else {
            // Si el curso no existe, añadirlo
            System.out.println("Curso no encontrado. Se añadirá un nuevo curso.");
            Curso nuevoCurso = new Curso();
            nuevoCurso.setId(idCurso);

            System.out.println("Introduce el nombre del nuevo curso:");
            String nombre = scanner.nextLine();
            nuevoCurso.setNombre(nombre);

            try {
                entityManager.persist(nuevoCurso);
                entityManager.getTransaction().commit();
                System.out.println("Nuevo curso añadido correctamente.");
            } catch (Exception e) {
                System.out.println("Error al añadir el curso: " + e.getMessage());
                entityManager.getTransaction().rollback();
            }
        }
    }


    private static void añadircurso(EntityManager entityManager,  Scanner scanner) {
        String id;
        do{
            System.out.println("introduce un id del curso");
             id = new Scanner(System.in).nextLine();
        }while (id.length() != 4|| id.length() == 0);
        Curso curso = entityManager.find(Curso.class, id);

        if (curso == null) {
            curso = new Curso();
            curso.setId(id);

            System.out.println("introduce el nombre del curso");
            String nombre = new Scanner(System.in).nextLine();
            curso.setNombre(nombre);
            entityManager.persist(curso);
            entityManager.getTransaction().commit();
            System.out.println("Curso añadido correctamente");
        } else {
            System.out.println("El Curso ya existe.");
        }

    }

    private static void modificarnombrecurso(EntityManager entityManager, Scanner scanner) {
        System.out.println("introduce el id del curso");
        String idcurso = new Scanner(System.in).nextLine();
        Curso curso = entityManager.find(Curso.class, idcurso);
        if (curso != null) {
            System.out.println("Nombre del curso: " + curso.getNombre());
            System.out.println("Introduce el nuevo nombre del curso");
            String nuevonombre = new Scanner(System.in).nextLine();

            curso.setNombre(nuevonombre);
            entityManager.getTransaction().commit();
            System.out.println("Nombre del curso actualizado a: " + curso.getNombre());
        } else {
            System.out.println("Curso no encontrado.");
        }
    }

    private static void mostrarDatosCurso(EntityManager entityManager, Scanner scanner) {
        System.out.println("introduce el id del curso");
        String idcurso = new Scanner(System.in).nextLine();

        Curso curso = entityManager.find(Curso.class, idcurso);
        if (curso != null) {
            System.out.println("Nombre del curso: " + curso.getNombre());
        } else {
            System.out.println("Curso no encontrado.");
        }
    }


    private static void mostrarDatosAlumno(EntityManager entityManager, Scanner scanner) {
        System.out.println("introduce el id del alumno");
        String id = new Scanner(System.in).nextLine();


        Alumno alumno = entityManager.find(Alumno.class, id);
        if (alumno != null) {
            System.out.println("Nombre: " + alumno.getNombre());
            System.out.println("Curso: " + alumno.getCurso());
            System.out.println("Nota media: " + alumno.getNotaMedia());
        } else {
            System.out.println("Alumno no encontrado.");
        }
    }



    private static void mostrarAlumnosDeCurso(EntityManager entityManager, Scanner scanner) {
        System.out.println("introduce el id del curso");
        String idcurso = new Scanner(System.in).nextLine();

        Curso curso = entityManager.find(Curso.class, idcurso);

        List<Alumno> alumnos = entityManager.createNamedQuery("Alumno.findByCurso", Alumno.class).setParameter("id", curso).getResultList();

        if(!alumnos.isEmpty()) {
            System.out.println("Lista de alumno: ");
            for (Alumno alumno : alumnos) {
                System.out.println("Nombre: " + alumno.getNombre());
                System.out.println("Curso: " + alumno.getCurso().getNombre());
            }
        }
    }

    private static void mostrarListadoCursos(EntityManager entityManager) {

        List<Curso> cursos = entityManager.createNamedQuery("Curso.findAll",Curso.class).getResultList();

        if(!cursos.isEmpty()) {
            System.out.println("Lista de cursos: ");
            for (Curso curso : cursos) {
                System.out.println("ID: " + curso.getId() + ", Nombre tutor: " + curso.getProfesor()); //FALTA NOMBRE PROFESOR
            }

        }else{
            System.out.println("no existe el curso");

        }

    }

    private static void mostrarMenu() {
        System.out.println("Menú:");
        System.out.println("1.- Datos de alumno");
        System.out.println("2.- Datos de curso");
        System.out.println("3.- Listado de cursos");
        System.out.println("4.- Listado de alumnos de curso");
        System.out.println("5.- Modificar nombre de curso:");
        System.out.println("6.- Añadir curso");
        System.out.println("7.- Añadir o modificar curso");
        System.out.println("8.- Modificar tutor de curso");
        System.out.println("9.- - Eliminar alumno");

        System.out.println("0.- Salir");

        System.out.print("Elija una opción: ");
    }

 }

