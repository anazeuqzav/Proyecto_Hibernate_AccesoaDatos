package org.servicesImpl;

import org.model.Empleado;
import org.services.EmpleadoDAO;

import javax.persistence.EntityManager;
import java.util.List;

public class EmpleadoDAOImpl implements EmpleadoDAO {
    private EntityManager em;

    public EmpleadoDAOImpl(EntityManager em) {
        this.em = em;
    }

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

    @Override
    public Empleado findById(int id) {
        return em.find(Empleado.class, id);
    }

    @Override
    public List<Empleado> findAll() {
        List<Empleado> empleados = em.createQuery("SELECT e FROM Empleado e", Empleado.class).getResultList();
        return empleados;
    }

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
