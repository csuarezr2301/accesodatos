package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import modelos.Grupo;
import modelos.Usuario;

import java.util.ArrayList;
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
                    listadogrupos(entityManager);
                    break;
                case 2:
                    // Datos de curso
                    listadousuariosnovotos(entityManager);
                    break;
                case 3:
                    // Listado de cursos
                    listusuarios1990(entityManager);
                    break;
                case 4:
                    // Listado de alumnos de un curso
                    grupossincomponentes(entityManager);
                    break;
                case 5:
                    grupossincomapñia(entityManager);
                    break;
                case 6:
                    gruposbarcelona(entityManager, "barcelona", 2010);
                    break;
                case 7:
                    ngruposmadrid(entityManager, "madrid");
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

    private static void ngruposmadrid(EntityManager entityManager,String loc) {
        List<Grupo> grupos = entityManager.createQuery("select g FROM Grupo g WHERE g.localidad=:loc", Grupo.class)
                .setParameter("loc",loc)
                .getResultList();
        if(grupos.isEmpty()){
            System.out.println("no yhay grupos de madrid");
        }else {
            for (Grupo grupo : grupos) {
                System.out.println("grupos de madrid: " + grupo.getNombre());
            }
        }
    }

    private static void gruposbarcelona(EntityManager entityManager, String loc, int a) {
        //declaramos las variables arriba y las añadimos en los ()
        List<Grupo> grupos = entityManager.createQuery("select g from Grupo g where g.localidad=:loc and annoGrab<:a",Grupo.class)
                .setParameter("loc", loc)
                .setParameter("a", a)
                .getResultList();
        if ( grupos.isEmpty() ) {
            System.out.println("no hay grupos con esas caracteristicas");
        }else{
            for(Grupo grupo : grupos) {
                System.out.println("grupos de barcelona y disco antes del 2010: " + grupo.getNombre());
            }
        }
    }

    private static void grupossincomapñia(EntityManager entityManager) {
        List<Grupo> grupos = entityManager.createQuery("SELECT g FROM Grupo g WHERE g.compania IS NULL ",Grupo.class)
                .getResultList();
        if ( grupos.isEmpty() ) {
            System.out.println("no hay grupos sin compañia");
        }else{
            for(Grupo grupo : grupos) {
                System.out.println("grupos sin compañia: " + grupo.getNombre());
            }
        }
    }

    private static void grupossincomponentes(EntityManager entityManager) {
        List<Grupo> grupos = entityManager.createQuery("select g from Grupo g left join g.componentes c group by g having count(c)=0",Grupo.class)
                .getResultList();
        if (grupos.isEmpty()) {
            System.out.println("no hay grupos sin componente");
        }else{
            for (Grupo grupo : grupos){
                System.out.println("grupos sin componente: " + grupo.getNombre());
            }
        }
    }
/*
LEFT JOIN g.componentes c: Realiza un LEFT JOIN con la colección de componentes de cada grupo.
Esto garantiza que se incluirán todos los grupos, incluso si no tienen componentes.
GROUP BY g: Agrupa los resultados por grupo, lo que significa que solo habrá un grupo por cada resultado.
HAVING COUNT(c) = 0: Filtra los resultados para devolver solo aquellos grupos que no tienen componentes .
Esto se logra al verificar que la cantidad de componentes asociados a un grupo sea 0.
*/
    private static void listusuarios1990(EntityManager entityManager) {
//    var query=em.createQuery("select u from Usuario u  "
//              + "where year(u.fechanac)>=:a",Usuario.class);
//        query.setParameter("a", a);
//		var query=em.createQuery("SELECT u FROM Usuario u WHERE u.fechanac >= :fecha",Usuario.class);
//		query.setParameter("fecha",LocalDate.of(a,1,1));
        List<Usuario>usuarios = entityManager.createQuery("SELECT u from Usuario u WHERE year(u.fechanac) >= 1990 ORDER BY u.fechanac ",Usuario.class)
                .getResultList();
        if(usuarios.isEmpty()) {
            System.out.println("No hay usuarios");
        }else{
            for (Usuario usuario : usuarios) {
                System.out.println("Usuarios nacidos despues de 1990: "+usuario.getNombre()+usuario.getApellidos());
            }
        }
    }

    private static void listadousuariosnovotos(EntityManager entityManager) {
        //		var query=em.createQuery("select u from Usuario u left join u.votos v "
//				+ "group by u having count(v)=0", Usuario.class);
//		var query=em.createQuery("select u from Usuario u left join u.votos v "
//				+ "where v is null", Usuario.class);
//        var query=em.createQuery("select u from Usuario u where size(u.votos)=0",Usuario.class);

        List<Usuario> usuarios = entityManager.createQuery("SELECT u FROM Usuario u WHERE u.numvotos = 0", Usuario.class)
                .getResultList();
        if(usuarios.isEmpty()){
            System.out.println("No hay usuarios");
        }else{
            for (Usuario usuario : usuarios) {
                System.out.println("usuarios con 0 votos: "+usuario.getNombre());
            }
        }
    }

    private static void listadogrupos(EntityManager entityManager) {
        List<Grupo> grupos = entityManager.createQuery("SELECT g FROM Grupo g ", Grupo.class)
                .getResultList();
        if (grupos.isEmpty()) {
            System.out.println("No hay grupos");
        }else {
            for (Grupo grupo : grupos) {
                System.out.println("Nombre:" + grupo.getNombre());
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("Menú:");
        System.out.println("1.- Listado de grupos");
        System.out.println("2.- Listado de usuarios que no han votado ");
        System.out.println("3.- Listado de usuarios nacidos a partir de 1990");
        System.out.println("4.- Grupos sin componentes cargados");
        System.out.println("5.- Grupos sin compañía cargada");
        System.out.println("6.- Grupos de Barcelona con primer disco en año antes de 2010");
        System.out.println("7.- Número de grupos de Madrid");
 
        System.out.println("0.- Salir");

        System.out.print("Elija una opción: ");
    }

}