package org.services;

import org.model.Venta;

import java.util.List;

public interface VentaDAO {
    void save (Venta venta);
    Venta findById(int id);
    List<Venta> findAll();
    void update(Venta venta);
    void delete(int id);
    Double obtenerIngresosPorMesYAnio(int mes, int anio);
}
