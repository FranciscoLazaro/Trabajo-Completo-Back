package com.example.Service.Imp;

import com.example.Entity.Usuario;
import com.example.Repository.UsuarioRepository;
import com.example.Service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class UsuarioServiceImp implements UsuarioService {
    private static final Logger logger = LoggerFactory.getLogger(UsuarioServiceImp.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario save(Usuario usuario) {
        usuario.setClave(MD5Encriptador(usuario.getClave()));
        return usuarioRepository.save(usuario);

    }
    @Override
    public Usuario existsUsuarioByNombreUsuarioAndClave(String nombreUsuario, String clave) {
        if(usuarioRepository.existsByNombreUsuarioAndClave(nombreUsuario,MD5Encriptador(clave))){
            return usuarioRepository.findByNombreUsuario(nombreUsuario).get();
        }
        return null;
    }

    @Override
    public Usuario update(Usuario usuario, Long id) {
        var usuarioDB = usuarioRepository.findById(id);
        if (usuarioDB.isEmpty()){
            logger.error("No se encontro una entidad con el id " + usuario.getId());
            throw new RuntimeException("No se encontro una entidad con el id " + usuario.getId());
        }
        usuario.setId(id);
        usuario.setClave(MD5Encriptador(usuario.getClave()));
        var newEntity = usuarioRepository.save(usuario);
        logger.info("Actualizada entidad {}",newEntity);
        return newEntity;
    }

    @Override
    public Usuario findByNombreUsuario(String nombre) {
        return usuarioRepository.findByNombreUsuario(nombre).get();
    }

    @Override
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElseThrow(()-> new NullPointerException("No se encontro usuario con el id "+ id));
    }

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Boolean delete(Long id) {
        try {
            usuarioRepository.deleteById(id);
            return true;
        }catch (Exception e ){
            return false;
        }
    }

    private String MD5Encriptador(String clave){
        String encriptado = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(clave.getBytes());
            StringBuilder sb = new StringBuilder();
            for(byte b : digest){
                sb.append(String.format("%02x",b));
            }
            encriptado = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return encriptado;
    }
}
