package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.RollbackException;
import modelo.*;

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
                    //em.merge(departamento); //este hace que si existe un id igual lo ignora y lo añade igual

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
                            empleado.addEstudio(estudio, LocalDate.parse("2000-12-31"));
                        }
                    }
            em.getTransaction().commit();
            System.out.println("Datos insertados correctamente");

                    System.out.println("Id del estudio ?"); // Solicitar al usuario que ingrese el ID de un estudio
                    int idTit = scanner.nextInt(); // Leer el ID del estudio proporcionado por el usuario
                    Estudio est=em.find(Estudio.class, idTit); // Buscar el estudio en la base de datos utilizando el ID proporcionado
                    if(est==null) { // Si no se encuentra un estudio con el ID proporcionado, mostrar un mensaje de error
                        System.out.println("No existe estudio con ese id");
                    }
                    else {
                        System.out.println("El estudio a asignar es "+est.getNombre());
                        System.out.println("Escribe id de empleado que tiene ese estudio (0 para cancelar)");
                        long n=scanner.nextLong();
                        if (n!=0) { // Si el ID del empleado es diferente de 0, proceder con la asignación
                            Empleado empleado=em.find(Empleado.class, n); // Buscar al empleado en la base de datos utilizando el ID proporcionado
                            if(empleado==null) {
                                System.out.println("NO existe el empleado");
                            }
                            else {
                                System.out.println("Empleado "+empleado.getNombre());
                                em.getTransaction().begin(); // Iniciar una transacción para realizar las operaciones de persistencia
                                empleado.addEstudio(est,LocalDate.of(2013, 6, 30));  // Agregar el estudio al empleado con una fecha de finalización
                                try {
                                    em.getTransaction().commit();
                                    System.out.println("asignado el estudio a "+empleado.getNombre());
                                }
                                catch(RollbackException rbe) {
                                    System.out.println("Error, ya se habia registrado el estudio de ese empleado");
                                    em.getTransaction().rollback();
                                }
                            }
                        }
                    }

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

