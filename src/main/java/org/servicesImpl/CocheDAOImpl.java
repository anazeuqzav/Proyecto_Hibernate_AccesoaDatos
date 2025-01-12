package org.servicesImpl;

import net.bytebuddy.asm.Advice;
import org.model.Cliente;
import org.model.Coche;
import org.model.Empleado;
import org.model.Venta;
import org.services.CocheDAO;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;

public class CocheDAOImpl implements CocheDAO {
    // Conexión
    private EntityManager em;

    // Constructor
    public CocheDAOImpl(EntityManager em) {
        this.em = em;
    }

    /**
     * Método para guardar un coche en la bd
     * @param coche objeto Coche para guardar
     */
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

    /**
     * Método para encontrar un coche por id en la base de datos
     * @param id identificador del coche a buscar
     * @return el objeto coche
     */
    @Override
    public Coche findById(int id) {
        return em.find(Coche.class, id);
    }

    /**
     * Método para listar todos los coches que hay en la bd
     * @return una lista de coches
     */
    @Override
    public List<Coche> findAll() {
        List<Coche> coches = em.createQuery("SELECT c FROM Coche c", Coche.class).getResultList();
        return coches;
    }

    /**
     * Método para actualizar los datos de un coche en la bd
     * @param coche Objeto coche a actualizar
     */
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

    /**
     * Método para borrar un coche de la bd
     * @param id identificador del coche a eliminar
     */
    @Override
    public void delete(int id) {
        try {
            em.getTransaction().begin();

            // Buscar el coche por ID
            Coche coche = findById(id);

            if (coche != null) {
                // Eliminar las relaciones de la tabla intermedia
                for (Cliente cliente : coche.getClientes()) {
                    cliente.getCoches().remove(coche);  // Eliminar la relación en el lado del cliente
                    em.merge(cliente);  // Guardar el cambio en la base de datos
                }

                // Ahora eliminar el coche
                em.remove(coche);

                em.getTransaction().commit();
                System.out.println("\nCoche eliminado con éxito");
            } else {
                System.err.println("\nCoche no encontrado.");
                em.getTransaction().rollback();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error al eliminar la entidad: " + e.getMessage());
        }
    }

    /**
     * Método que muestra los coches vendidos entre dos fechas
     * @param fechaInicio Fecha de inicio
     * @param fechaFin Fecha de fin
     * @return una lista de coches vendidos entre esas fechas
     */
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

    /**
     * Método que muestra una lista de coches de un cliente
     * @param id
     * @return
     */
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
