package org.servicesImpl;

import org.model.Cliente;
import org.services.ClienteDAO;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ClienteDAOImpl implements ClienteDAO {
    // Conexión
    private EntityManager em;

    // Constructor
    public ClienteDAOImpl(EntityManager em){
        this.em = em;
    }

    /**
     * Método para guardar un cliente en la bd
     * @param cliente Objeto cliente a guardar
     */
    @Override
    public void save(Cliente cliente) {
        try {
            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();
            System.out.println("Cliente creado con éxito");

        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error al persistir la entidad: " + e.getMessage());
        }
    }

    /**
     * Método para encontrar un cliente por ID guardado en la bd
     * @param id Identificador del cliente a encontrar
     * @return objeto cliente
     */
    @Override
    public Cliente findById(int id) {
        return em.find(Cliente.class, id);
    }

    /**
     * Método para listar todos los clientes que se encuentran en la bd
     * @return una lista de clientes
     */
    @Override
    public List<Cliente> findAll() {
        List<Cliente> clientes = em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
        return clientes;
    }

    /**
     * Método para actualizar un cliente que se encuenta en la bd
     * @param cliente el objeto Cliente para actualizar
     */
    @Override
    public void update(Cliente cliente) {
        try{
            em.getTransaction().begin();
            em.merge(cliente);
            em.getTransaction().commit();
            System.out.println("Cliente actualizado con éxito");
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error al persistir la entidad: " + e.getMessage());
        }
    }

    /**
     * Método para borrar un cliente en la bd
     * @param id identificador del cliente a eliminar
     */
    @Override
    public void delete(int id) {
        try {
            em.getTransaction().begin();
            Cliente cliente = findById(id);
            if (cliente != null) {
                em.remove(cliente);
                em.getTransaction().commit();
                System.out.println("\nCliente eliminado con éxito");
            } else {
                System.err.println("\nCliente no encontrado.");
                em.getTransaction().rollback();
            }
        }catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error al eliminar la entidad: " + e.getMessage());
        }

    }
}
