package com.example.Service;



import com.example.Entity.Usuario;

import java.util.List;

public interface UsuarioService {
    Usuario save(Usuario pedido);
    Usuario update(Usuario pedido, Long id);
    Usuario findById(Long id);
    List<Usuario> findAll();
    Boolean delete(Long id);
    Usuario findByNombreUsuario(String nombre);
    public Usuario existsUsuarioByNombreUsuarioAndClave(String nombreUsuario, String clave);
}
