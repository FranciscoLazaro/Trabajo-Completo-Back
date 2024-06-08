package com.example.Service.Imp;

import com.example.Service.CategoriaService;
import com.example.Entity.Categoria;
import com.example.Repository.CategoriaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServiceImp implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    private static final Logger logger = LoggerFactory.getLogger(CategoriaServiceImp.class);

    @Override
    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public Categoria update(Categoria categoria, Long id) {
        var categoriaDB = categoriaRepository.findById(id);
        if (categoriaDB.isEmpty()){
            logger.error("No se encontro una entidad con el id " + categoria.getId());
            throw new RuntimeException("No se encontro una entidad con el id " + categoria.getId());
        }
        categoria.setId(id);
        var newEntity = categoriaRepository.save(categoria);
        logger.info("Actualizada entidad {}",newEntity);
        return newEntity;
    }

    @Override
    public Categoria findById(Long id) {
        return categoriaRepository.findById(id).orElseThrow(()->new NullPointerException("No se encontro categoria con el id " + id));
    }

    @Override
    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    @Override
    public Boolean delete(Long id) {
        try {
            categoriaRepository.deleteById(id);
            return true;
        }catch (Exception e ){
            return false;
        }
    }
}