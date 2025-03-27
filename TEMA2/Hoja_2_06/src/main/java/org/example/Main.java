package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import org.example.modelo.Alumno;
import org.example.modelo.Curso;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("miUnidadDePersistencia");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        try {

            Query consulta = entityManager.createQuery("SELECT a FROM Alumno a");
            List<Alumno> listaAlumnos = consulta.getResultList();


            for (Alumno alumno : listaAlumnos) {
                System.out.println("Nombre: " + alumno.getNombre() + ", Curso: " + alumno.getCurso().getNombre() + ", Nota media: " + alumno.getNotaMedia());

            }


            entityManager.getTransaction().commit();
        } catch (Exception e) {

            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {

            entityManager.close();
            entityManagerFactory.close();
        }
    }
}
