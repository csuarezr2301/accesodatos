package org.example.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import modelo.Empleado;
import modelo.Sueldo; // Asegúrate de importar la clase Sueldo


import java.time.LocalDate;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadDePersistencia");
        EntityManager em = emf.createEntityManager();
        Scanner scanner = new Scanner(System.in);


        em.getTransaction().begin(); // Inicializar la transacción y agregar algunos empleados de ejemplo

        Empleado empleado1 = new Empleado();
        empleado1.setNombre("Juan Pérez");
        empleado1.setOficio("Desarrollador");
        empleado1.setFechaAlta(LocalDate.now());

        Sueldo sueldo1 = new Sueldo();
        sueldo1.setSalario(3000); // Asignar salario
        empleado1.setSueldo(sueldo1); // Asignar el sueldo al empleado

        em.persist(empleado1);

        Empleado empleado2 = new Empleado();
        empleado2.setNombre("Ana Gómez");
        empleado2.setOficio("Diseñadora");
        empleado2.setFechaAlta(LocalDate.now());

        Sueldo sueldo2 = new Sueldo();
        sueldo2.setSalario(2800); // Asignar salario
        empleado2.setSueldo(sueldo2); // Asignar el sueldo al empleado
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

                System.out.print("Salario: ");
                Sueldo nuevoSueldo = new Sueldo();
                nuevoSueldo.setSalario(scanner.nextDouble());

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
