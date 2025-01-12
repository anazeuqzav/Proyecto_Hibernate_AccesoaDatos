package org.servicesImpl;

import org.model.Empleado;
import org.services.EmpleadoDAO;

import javax.persistence.EntityManager;
import java.util.List;

public class EmpleadoDAOImpl implements EmpleadoDAO {
    // Conexión
    private EntityManager em;

    // Constructor
    public EmpleadoDAOImpl(EntityManager em) {
        this.em = em;
    }

    /**
     * Método para guardar un Empleado en la bd
     */

    @Override
    public void save(Empleado empleado) {
        try {
            em.getTransaction().begin();
            em.persist(empleado);
            em.getTransaction().commit();
            System.out.println("Empleado creado con éxito");

        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error al persistir la entidad: " + e.getMessage());
        }
    }

    /**
     * Método para encontrar un empleado en la bd
     * @param id identificador del empleado a encontrar
     * @return objeto empleado
     */
    @Override
    public Empleado findById(int id) {
        return em.find(Empleado.class, id);
    }

    /**
     * Método que lista todos los empleados que se encuentran en la bd
     * @return una lista de empleados
     */
    @Override
    public List<Empleado> findAll() {
        List<Empleado> empleados = em.createQuery("SELECT e FROM Empleado e", Empleado.class).getResultList();
        return empleados;
    }

    /**
     * Método para actualizar los datos de un empleado en la bd
     * @param empleado el empleado para actualizar
     */
    @Override
    public void update(Empleado empleado) {
        try{
            em.getTransaction().begin();
            em.merge(empleado);
            em.getTransaction().commit();
            System.out.println("Empleado actualizado con éxito");
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error al persistir la entidad: " + e.getMessage());
        }
    }

    /**
     * Método para borrar un empleado de la bd
     * @param id identificador del empleado a eliminar
     */
    @Override
    public void delete(int id) {
        try {
            em.getTransaction().begin();
            Empleado empleado = findById(id);
            if (empleado != null) {
                em.remove(empleado);
                em.getTransaction().commit();
                System.out.println("\nEmpleado eliminado con éxito");
            } else {
                System.err.println("\nEmpleado no encontrado.");
                em.getTransaction().rollback();
            }
        }catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error al eliminar la entidad: " + e.getMessage());
        }
    }
}
