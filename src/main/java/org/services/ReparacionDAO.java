package org.services;

import org.model.Reparacion;

import java.util.List;

public interface ReparacionDAO {
    void save (Reparacion reparacion);
    Reparacion findById(int id);
    List<Reparacion> findAll();
    void update(Reparacion reparacion);
    void delete(int id);
    List<Reparacion> reparacionesById(int id);

}
