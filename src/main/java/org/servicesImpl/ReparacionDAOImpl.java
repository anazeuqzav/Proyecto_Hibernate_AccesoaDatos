package org.servicesImpl;

import org.model.Reparacion;
import org.services.ReparacionDAO;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class ReparacionDAOImpl implements ReparacionDAO {
    private EntityManager em;

    public ReparacionDAOImpl(EntityManager em) {
        this.em = em;
    }

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

    @Override
    public Reparacion findById(int id) {
        return em.find(Reparacion.class, id);
    }

    @Override
    public List<Reparacion> findAll() {
        List<Reparacion> reparaciones = em.createQuery("SELECT r FROM Reparacion r", Reparacion.class).getResultList();
        return reparaciones;
    }

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


    // Mostrar las reparaciones realizadas por un mecánico específico
    public List<Reparacion> reparacionesById(int id){
        Query query = em.createQuery("FROM Reparacion r WHERE  r.empleado.id = :idEmpleado");
        query.setParameter("idEmpleado", id);
        List<Reparacion> resultados = query.getResultList();
        return resultados;
    }
}
