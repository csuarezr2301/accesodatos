package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.modelo.Empleado;

import java.time.LocalDate;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadDePersistencia");
        EntityManager em = emf.createEntityManager();
        Scanner scanner = new Scanner(System.in);


        em.getTransaction().begin();

        Empleado empleado1 = new Empleado();
        empleado1.setNombre("Juan Pérez");
        empleado1.setOficio("Desarrollador");
        empleado1.setFechaAlta(LocalDate.now());
        empleado1.setSalario(3000);
        em.persist(empleado1);

        Empleado empleado2 = new Empleado();
        empleado2.setNombre("Ana Gómez");
        empleado2.setOficio("Diseñadora");
        empleado2.setFechaAlta(LocalDate.now());
        empleado2.setSalario(2800);
        em.persist(empleado2);

        em.getTransaction().commit();

        while (true) {
            System.out.println("¿Desea agregar un nuevo empleado? (s/n)");
            String respuesta = scanner.nextLine();

            if (respuesta.equalsIgnoreCase("s")) {
                Empleado nuevoEmpleado = new Empleado();

                System.out.print("Nombre: ");
                nuevoEmpleado.setNombre(scanner.nextLine());

                System.out.print("Puesto: ");
                nuevoEmpleado.setOficio(scanner.nextLine());

                em.getTransaction().begin();
                em.persist(nuevoEmpleado);
                em.getTransaction().commit();

                System.out.println("Empleado agregado: " + nuevoEmpleado.getNombre());
            } else {
                break;
            }
        }

        em.close();
        emf.close();
        scanner.close();
    }
}
