package org.servicesImpl;

import org.model.Cliente;
import org.services.ClienteDAO;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ClienteDAOImpl implements ClienteDAO {
    private EntityManager em;

    public ClienteDAOImpl(EntityManager em){
        this.em = em;
    }

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

    @Override
    public Cliente findById(int id) {
        return em.find(Cliente.class, id);
    }

    @Override
    public List<Cliente> findAll() {
        List<Cliente> clientes = em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
        return clientes;
    }

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
