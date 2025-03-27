package org.example.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import modelo.Departamento;
import modelo.Empleado;
import modelo.Sueldo;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadDePersistencia");
        EntityManager em = emf.createEntityManager();
        Scanner scanner = new Scanner(System.in);

        try {
            em.getTransaction().begin();

            // Crear un nuevo departamento
            Departamento departamento = new Departamento();

            System.out.print("ID del Departamento: ");
            departamento.setId(scanner.nextLong());
            scanner.nextLine(); // Limpiar el buffer

            System.out.print("Nombre del Departamento: ");
            departamento.setNombre(scanner.nextLine());

            System.out.print("Localidad del Departamento: ");
            departamento.setLocalidad(scanner.nextLine());

            em.persist(departamento);

            // Agregar empleados al departamento
            while (true) {
                System.out.println("Introduce el nombre del empleado (o presiona ENTER para terminar): ");
                String nombreEmpleado = scanner.nextLine();

                if (nombreEmpleado.isEmpty()) {
                    break;
                }

                Empleado empleado = new Empleado();
                empleado.setNombre(nombreEmpleado);

                System.out.print("Puesto: ");
                empleado.setOficio(scanner.nextLine());

                System.out.print("Salario: ");
                Sueldo sueldo = new Sueldo();
                sueldo.setSalario(scanner.nextDouble());

                System.out.print("Comisión: ");
                sueldo.setComision(scanner.nextDouble());
                empleado.setSueldo(sueldo);

                scanner.nextLine(); // Limpiar buffer

                empleado.setFechaAlta(LocalDate.now());
                departamento.addEmpleado(empleado); // Agrega el empleado al departamento
            }

            em.getTransaction().commit();

            // Consulta y muestra todos los empleados
            var query = em.createQuery("SELECT e FROM Empleado e", Empleado.class);
            List<Empleado> empleados = query.getResultList();
            empleados.forEach(emp -> verEmpleado(emp));

        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
            scanner.close();
        }
    }

    public static void verEmpleado(Empleado emp) {
        System.out.println("Empleado: " + emp.getNombre() + ", Puesto: " + emp.getOficio() +
                ", Salario: " + emp.getSueldo().getSalario() +
                ", Departamento: " + emp.getDepartamento().getNombre());
    }
}
