package org.servicesImpl;

import net.bytebuddy.asm.Advice;
import org.model.Coche;
import org.model.Empleado;
import org.services.CocheDAO;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;

public class CocheDAOImpl implements CocheDAO {
    private EntityManager em;

    public CocheDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void save(Coche coche) {
        try {
            em.getTransaction().begin();
            em.persist(coche);
            em.getTransaction().commit();
            System.out.println("Coche creado con éxito");

        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error al persistir la entidad: " + e.getMessage());
        }
    }

    @Override
    public Coche findById(int id) {
        return em.find(Coche.class, id);
    }

    @Override
    public List<Coche> findAll() {
        List<Coche> coches = em.createQuery("SELECT c FROM Coche c", Coche.class).getResultList();
        return coches;
    }

    @Override
    public void update(Coche coche) {
        try{
            em.getTransaction().begin();
            em.merge(coche);
            em.getTransaction().commit();
            System.out.println("Coche actualizado con éxito");
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error al persistir la entidad: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        try {
            em.getTransaction().begin();
            Coche coche = findById(id);
            if (coche != null) {
                em.remove(coche);
                em.getTransaction().commit();
                System.out.println("\nCoche eliminado con éxito");
            } else {
                System.err.println("\nCoche no encontrado.");
                em.getTransaction().rollback();
            }
        }catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error al eliminar la entidad: " + e.getMessage());
        }
    }

    public List<Coche> cochesVendidosFecha(LocalDate fechaInicio, LocalDate fechaFin){
        Query query = em.createQuery(
                "SELECT c FROM Coche c " +
                        "JOIN c.ventas v " +
                        "WHERE v.fecha BETWEEN :fechaInicio AND :fechaFin",
                Coche.class);
        query.setParameter("fechaInicio", fechaInicio);
        query.setParameter("fechaFin", fechaFin);

        List<Coche> coches = query.getResultList();
        return coches;
    }

    public List<Coche> cochesByIdCliente(int id){
        Query query = em.createQuery(
                "SELECT DISTINCT c FROM Coche c " +
                        "JOIN c.ventas v " +
                        "WHERE v.cliente.id = :idCliente",
                Coche.class);
        query.setParameter("idCliente", id);

        List<Coche> coches = query.getResultList();
        return coches;
    }
}
