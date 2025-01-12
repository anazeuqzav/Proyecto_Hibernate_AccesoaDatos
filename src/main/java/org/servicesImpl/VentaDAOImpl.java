package org.servicesImpl;

import org.model.Venta;
import org.services.VentaDAO;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class VentaDAOImpl implements VentaDAO {
    // Conexión
    private EntityManager em;

    // Constructor
    public VentaDAOImpl (EntityManager em){
        this.em = em;
    }

    /**
     * Método para guardar una venta en la bd
     * @param venta objeto venta a guardar
     */
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

    /**
     * Método para encontrar una venta en la bd
     * @param id identificador de la venta que se desea encontrar
     * @return objeto venta
     */
    @Override
    public Venta findById(int id) {
        return em.find(Venta.class, id);
    }

    /**
     * Método para listar todas las ventas de una bd
     * @return una lista de ventas
     */
    @Override
    public List<Venta> findAll() {
        List<Venta> ventas = em.createQuery("SELECT v FROM Venta v", Venta.class).getResultList();
        return ventas;
    }

    /**
     * Método para actualizar los datos de una venta en la bd
     * @param venta objeto venta que se quiere actualizar
     */
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

    /**
     * Método para eliminar una venta de la bd
     * @param id identificador de la venta que se quiere eliminar
     */
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

    /**
     * Método para obtener los ingresos de un mes
     * @param mes mes que se quiere consultar
     * @param anio año que se quiere consultar
     * @return total de ingresos de ese mes
     */
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
