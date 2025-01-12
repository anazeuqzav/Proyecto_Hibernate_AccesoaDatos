package org.servicesImpl;

import org.model.Cliente;
import org.model.Usuario;
import org.services.UsuarioDAO;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class UsuarioDAOImpl implements UsuarioDAO {
    // Conexión
    private EntityManager em;

    // Constructor
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

    /**
     * Método para encontrar un usuario en la bd
     * @param id identificador del usuario que se quiere encontrar
     * @return objeto usuario
     */
    @Override
    public Usuario findById(int id) {
        return em.find(Usuario.class, id);
    }

    /**
     * Método para listar todos los usuarios contenidos en la bd
     * @return una lista de usuarios
     */
    @Override
    public List<Usuario> findAll() {
        List<Usuario> usuarios = em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
        return usuarios;
    }

    /**
     * Método para actualizar los datos de un usuario
     * @param usuario objeto usuario que se desea actualizar
     */
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

    /**
     * Método para eliminar un usuario de la bd
     * @param id identificador del usuario que se desea eliminar
     */
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
