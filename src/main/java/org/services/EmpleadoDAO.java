package org.services;

import org.model.Empleado;
import org.model.Usuario;

import java.util.List;

public interface EmpleadoDAO {
    void save (Empleado empleado);
    Empleado findById(int id);
    List<Empleado> findAll();
    void update(Empleado empleado);
    void delete(int id);
}
