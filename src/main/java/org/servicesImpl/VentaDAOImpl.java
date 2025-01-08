package org.servicesImpl;

import org.model.Venta;
import org.services.VentaDAO;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class VentaDAOImpl implements VentaDAO {
    private EntityManager em;

    public VentaDAOImpl (EntityManager em){
        this.em = em;
    }

    @Override
    public void save(Venta venta) {
        try {
            em.getTransaction().begin();
            em.persist(venta);
            em.getTransaction().commit();
            System.out.println("Venta creada con éxito");

        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error al persistir la entidad: " + e.getMessage());
        }
    }

    @Override
    public Venta findById(int id) {
        return em.find(Venta.class, id);
    }

    @Override
    public List<Venta> findAll() {
        List<Venta> ventas = em.createQuery("SELECT v FROM Venta v", Venta.class).getResultList();
        return ventas;
    }

    @Override
    public void update(Venta venta) {
        try{
            em.getTransaction().begin();
            em.merge(venta);
            em.getTransaction().commit();
            System.out.println("Venta actualizada con éxito");
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error al persistir la entidad: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        try {
            em.getTransaction().begin();
            Venta venta = findById(id);
            if (venta != null) {
                em.remove(venta);
                em.getTransaction().commit();
                System.out.println("\nVenta eliminada con éxito");
            } else {
                System.err.println("\nVenta no encontrada.");
                em.getTransaction().rollback();
            }
        }catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error al eliminar la entidad: " + e.getMessage());
        }
    }

    public Double obtenerIngresosPorMesYAnio(int mes, int anio) {
        Double ingresos = 0.0;
        try {
            Query query = em.createQuery(
                    "SELECT SUM(v.monto) FROM Venta v " +
                            "WHERE FUNCTION('MONTH', v.fecha) = :mes " +
                            "AND FUNCTION('YEAR', v.fecha) = :anio"
            );
            query.setParameter("mes", mes);
            query.setParameter("anio", anio);

            ingresos = (Double) query.getSingleResult();
        } catch (Exception e) {
            System.err.println("Error al obtener ingresos: " + e.getMessage());
        }
        return ingresos;
    }

}
