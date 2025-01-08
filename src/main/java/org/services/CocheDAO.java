package org.services;

import org.model.Coche;

import java.time.LocalDate;
import java.util.List;

public interface CocheDAO {
    void save (Coche coche);
    Coche findById(int id);
    List<Coche> findAll();
    void update(Coche coche);
    void delete(int id);
    List<Coche> cochesVendidosFecha(LocalDate fechaInicio, LocalDate fechaFin);
    List<Coche> cochesByIdCliente(int id);
}
