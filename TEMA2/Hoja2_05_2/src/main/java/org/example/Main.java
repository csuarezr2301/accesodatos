package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.RollbackException;
import modelo.Estudio;
import modelo.EstudioNoOficial;
import modelo.EstudioOficial;


public class Main {
            public static void main(String[] args) {
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadDePersistencia");
                EntityManager em = emf.createEntityManager();

            em.getTransaction().begin();
                try {
                    Estudio est1=new Estudio();
                    est1.setCodEstudio("RRHH");
                    est1.setNombre("Recursos Humanos");

                    Estudio est2=new Estudio();
                    est2.setCodEstudio("PD");
                    est2.setNombre("Pesca Deportiva");

                    EstudioOficial eo1=new EstudioOficial();
                    eo1.setCodEstudio("DAM");
                    eo1.setNombre("Desarrollo de APlicaciones Multiplataforma");
                    eo1.setCentro("IES Miguel Herrero");
                    eo1.setRealDecreto("RD 117/2011");

                    EstudioOficial eo2=new EstudioOficial();
                    eo2.setCodEstudio("DAW");
                    eo2.setNombre("Desarrollo de APlicaciones Web");
                    eo2.setCentro("IES Miguel Herrero");
                    eo2.setRealDecreto("RD 193/2011");

                    EstudioNoOficial eno1=new EstudioNoOficial();
                    eno1.setCodEstudio("001-NO");
                    eno1.setNombre("API Rest con Spring");
                    eno1.setNumeroHoras(60);
                    eno1.setAcademia("Acedemia Spring");

                    EstudioNoOficial eno2=new EstudioNoOficial();
                    eno2.setCodEstudio("002-NO");
                    eno2.setNombre("API Rest con Spring");
                    eno2.setNumeroHoras(70);
                    eno2.setAcademia("Acedemia Spring");

                    em.persist(est1);
                    em.persist(est2);
                    em.persist(eo1);
                    em.persist(eo2);
                    em.persist(eno2);
                    em.persist(eno1);

                    em.getTransaction().commit();

                }
                catch(RollbackException rbe) {
                    em.getTransaction().rollback();
                }

                em.close();
                emf.close();
            }
}


//BORRAR DE LA TABLA ESTUDIOS EN MYWORK BENCH
//ALTER TABLE estudios DROP COLUMN horas;
