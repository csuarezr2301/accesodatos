package basededatos;


import jakarta.persistence.*;
import model.Cancion;
import model.Grupo;
import model.Usuario;


import java.util.List;

public class GestorBaseDatos {
    private EntityManager em;

    public GestorBaseDatos(EntityManager entityManager) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadDePersistencia");
        this.em = emf.createEntityManager();
    }

    public List<Usuario> usuariosNacidosAnoMayor(int fechanac) {
        TypedQuery<Usuario> query = em.createQuery(
                "SELECT u FROM Usuario u WHERE year(u.fechanac) >= :fechanac", Usuario.class) // consulta es específica
                .setParameter("fechanac", fechanac);
        return query.getResultList();
    }
    public List<Grupo> gruposConNumeroCancionesMayor(int numero) {
        TypedQuery<Grupo> query = em.createNamedQuery("Grupo.findbyNumber", Grupo.class) //se usa named query porque viene de otro metodo
                .setParameter("numero", numero);

        return query.getResultList();
    }
    public List<Object[]> getCancionesGrupos(List<String> nombres) {
        TypedQuery<Object[]> query = em.createQuery(
                        "SELECT c.titulo, g.nombre FROM Cancion c JOIN c.grupo g WHERE g.nombre IN :nombres",
                        Object[].class)
                .setParameter("nombres", nombres);
        return query.getResultList();
    }

    /*
    SELECT c FROM Cancion c JOIN c.grupo g: Seleccionamos todas las canciones que están asociadas a un grupo.
    WHERE g.nombre IN :nombres: Filtramos las canciones de los grupos cuyos nombres están en la lista nombres.
    .setParameter("nombres", nombres): Establecemos los nombres de los grupos como parámetro en la consulta.
     */
    public List<Grupo> gruposLocalidadAno (String localidad, int ano) {
        TypedQuery<Grupo> query = em.createNamedQuery("Grupo.findbyLocYear", Grupo.class) //se usa named query porque viene de otro metodo
                .setParameter("localidad", localidad)
                .setParameter("ano", ano);

        return query.getResultList();
    }
}









