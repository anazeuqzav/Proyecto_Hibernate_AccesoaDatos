package org.services;

import org.model.Usuario;

import java.util.List;

public interface UsuarioDAO {
    void save (Usuario usuario);
    Usuario findById(int id);
    List<Usuario> findAll();
    void update(Usuario usuario);
    void delete(int id);
}
