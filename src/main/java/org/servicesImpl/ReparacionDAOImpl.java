package org.servicesImpl;

import org.model.Reparacion;
import org.services.ReparacionDAO;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class ReparacionDAOImpl implements ReparacionDAO {
    // Conexión
    private EntityManager em;

    // Constructor
    public ReparacionDAOImpl(EntityManager em) {
        this.em = em;
    }

    /**
     * Método para guardar una reparación en la bd
     * @param reparacion
     */
    @Override
    public void save(Reparacion reparacion) {
        try {
            em.getTransaction().begin();
            em.persist(reparacion);
            em.getTransaction().commit();
            System.out.println("Reparación creada con éxito");

        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error al persistir la entidad: " + e.getMessage());
        }
    }

    /**
     * Método para encontrar una reparación por id en la bd
     * @param id identificador de la reparación a buscar
     * @return objeto reparación
     */
    @Override
    public Reparacion findById(int id) {
        return em.find(Reparacion.class, id);
    }

    /**
     * Método para listar todas las reparaciones de la bd
     * @return una lista de reparaciones
     */
    @Override
    public List<Reparacion> findAll() {
        List<Reparacion> reparaciones = em.createQuery("SELECT r FROM Reparacion r", Reparacion.class).getResultList();
        return reparaciones;
    }

    /**
     * Método para actualizar los datos de una reparación
     * @param reparacion reparacion a actualizar
     */
    @Override
    public void update(Reparacion reparacion) {
        try{
            em.getTransaction().begin();
            em.merge(reparacion);
            em.getTransaction().commit();
            System.out.println("Reparación actualizada con éxito");
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error al persistir la entidad: " + e.getMessage());
        }
    }

    /**
     * Método para borrar una reparación de la bd
     * @param id Identificador de la reparación que se quiere eliminar
     */
    @Override
    public void delete(int id) {
        try {
            em.getTransaction().begin();
            Reparacion reparacion = findById(id);
            if (reparacion != null) {
                em.remove(reparacion);
                em.getTransaction().commit();
                System.out.println("\nReparación eliminada con éxito");
            } else {
                System.err.println("\nReparación no encontrada.");
                em.getTransaction().rollback();
            }
        }catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error al eliminar la entidad: " + e.getMessage());
        }
    }

    /**
     * Método para mostrar todas las reparaciones realizadas por un mécanico específico
     * @param id identificador de empleado
     * @return lista de reparaciones
     */
    public List<Reparacion> reparacionesById(int id){
        Query query = em.createQuery("FROM Reparacion r WHERE  r.empleado.id = :idEmpleado");
        query.setParameter("idEmpleado", id);
        List<Reparacion> resultados = query.getResultList();
        return resultados;
    }
}
