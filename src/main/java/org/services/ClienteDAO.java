package org.services;

import org.model.Cliente;

import java.util.List;

public interface ClienteDAO {
    void save (Cliente cliente);
    Cliente findById(int id);
    List<Cliente> findAll();
    void update(Cliente cliente);
    void delete(int id);
}
