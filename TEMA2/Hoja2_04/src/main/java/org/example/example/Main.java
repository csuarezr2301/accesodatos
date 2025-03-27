package org.example.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import modelo.Departamento;
import modelo.Empleado;
import modelo.Estudio;
import modelo.Sueldo;

import java.time.LocalDate;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadDePersistencia");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // Crear un departamento
            Departamento departamento = new Departamento();
            departamento.setId(45L);
            departamento.setNombre("Desarrollo");
            departamento.setLocalidad("Madrid");
            em.persist(departamento);

            // Crear un empleado con datos fijos
            Empleado empleado = new Empleado();
            empleado.setNombre("Juan Perez");
            empleado.setOficio("Analista");
            empleado.setFechaAlta(LocalDate.now());
            Sueldo sueldo = new Sueldo(2500,300);
            empleado.setSueldo(sueldo);

            departamento.addEmpleado(empleado); // Agrega el empleado al departamento

            em.persist(empleado);

            // Segundo empleado con datos fijos
            Empleado empleado2 = new Empleado();
            empleado2.setNombre("Maria Gomez");
            empleado2.setOficio("Desarrolladora");
            empleado2.setFechaAlta(LocalDate.now());
            Sueldo sueldo2 = new Sueldo(2800,400);
            empleado2.setSueldo(sueldo2);

            departamento.addEmpleado(empleado2); // Agregar el empleado 2 al departamento

            em.persist(empleado2);

            // Agregar un estudio cursado por el empleado
            Estudio estudio = new Estudio();
            estudio.setNombre("Ingeniería en Sistemas");
            estudio.setCodEstudio("A123");
//estudio.addEmpleado(empleado);
            empleado.addEstudio(estudio); // Asociar el estudio al 1º empleado

            em.persist(estudio);

            // Agregar un estudio cursado por el segundo empleado
            Estudio estudio2 = new Estudio();
            estudio2.setNombre("Maestría en Inteligencia Artificial");
            estudio2.setCodEstudio("A456");
//estudio2.addEmpleado(empleado2);
            empleado2.addEstudio(estudio2);  // Asociar el estudio al segundo empleado

            em.persist(estudio2);

            em.flush(); // Fuerza la escritura de los cambios antes del commit
            em.getTransaction().commit();
            System.out.println("Datos insertados correctamente");

            // Consulta y muestra todos los empleados
            var query = em.createQuery("SELECT e FROM Empleado e", Empleado.class);
            List<Empleado> empleados = query.getResultList();
            empleados.forEach(emp -> verEmpleado(emp));

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
    public static void verEmpleado(Empleado emp) {
        System.out.println("Empleado: " + emp.getNombre() + ", Puesto: " + emp.getOficio() +
                ", Salario: " + emp.getSueldo().getSalario() +
                ", Comisión: " + emp.getSueldo().getComision() +
                ", Departamento: " + emp.getDepartamento().getNombre());
    }
}




/* DATOS METIDOS POR LE USUARIO

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.models.Departamento;
import org.example.models.Empleado;

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

                em.persist(empleado);

                //agregar un estudio que nuevos empleados hayan cursado
                System.out.println("Introduce un estudio que el empleado haya cursado (o presiona ENTER para no agregar ninguno): ");
                String nombreEstudio = scanner.nextLine();

                if (!nombreEstudio.isEmpty()) {
                    Estudio estudio = new Estudio();
                    estudio.setNombre(nombreEstudio);
                    em.persist(estudio);  // Persistir el estudio

                    // Asociar el estudio al empleado
                    empleado.addEstudio(estudio);
                }
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
*/