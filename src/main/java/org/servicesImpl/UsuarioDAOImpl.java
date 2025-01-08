package org.servicesImpl;

import org.model.Cliente;
import org.model.Usuario;
import org.services.UsuarioDAO;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class UsuarioDAOImpl implements UsuarioDAO {
    private EntityManager em;

    public UsuarioDAOImpl(EntityManager em) {
        this.em = em;
    }

    /**
     * Método para guardar un usuario en la bd
     * @param usuario objeto usuario
     */
    @Override
    public void save(Usuario usuario) {
        try {
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
            System.out.println("Usuario creado con éxito");

        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error al persistir la entidad: " + e.getMessage());
        }
    }

    @Override
    public Usuario findById(int id) {
        return em.find(Usuario.class, id);
    }

    @Override
    public List<Usuario> findAll() {
        List<Usuario> usuarios = em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
        return usuarios;
    }

    @Override
    public void update(Usuario usuario) {
        try{
            em.getTransaction().begin();
            em.merge(usuario);
            em.getTransaction().commit();
            System.out.println("Usuario actualizado con éxito");
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error al persistir la entidad: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        try {
            em.getTransaction().begin();
            Usuario usuario = findById(id);
            if (usuario != null) {
                em.remove(usuario);
                em.getTransaction().commit();
                System.out.println("\nUsuario eliminado con éxito");
            } else {
                System.err.println("\nUsuario no encontrado.");
                em.getTransaction().rollback();
            }
        }catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error al eliminar la entidad: " + e.getMessage());
        }
    }
}
