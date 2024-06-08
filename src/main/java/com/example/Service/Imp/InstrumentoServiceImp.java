package com.example.Service.Imp;

import com.example.Entity.Instrumento;
import com.example.Repository.InstrumentoRepository;
import com.example.Service.InstrumentoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstrumentoServiceImp implements InstrumentoService {

    @Autowired
    private InstrumentoRepository instrumentoRepository;

    private static final Logger logger = LoggerFactory.getLogger(CategoriaServiceImp.class);

    @Override
    public Instrumento save(Instrumento instrumento) {
        return instrumentoRepository.save(instrumento);
    }

    @Override
    public Instrumento update(Instrumento instrumento, Long id) {
        var instrumentoDB = instrumentoRepository.findById(id);
        if (instrumentoDB.isEmpty()){
            logger.error("No se encontro una entidad con el id " + instrumento.getId());
            throw new RuntimeException("No se encontro una entidad con el id " + instrumento.getId());
        }
        instrumento.setId(id);
        var newEntity = instrumentoRepository.save(instrumento);
        logger.info("Actualizada entidad {}",newEntity);
        return newEntity;
    }

    @Override
    public Instrumento findById(Long id) {
        return instrumentoRepository.findById(id).orElseThrow(()-> new NullPointerException("No se encontro instrumento con el id "+ id));
    }

    @Override
    public List<Instrumento> findAll() {
        return instrumentoRepository.findAll();
    }

    @Override
    public List<Instrumento> findByCategoriaId(Long id) {
        return instrumentoRepository.findByCategoriaId(id);
    }

    @Override
    public Boolean delete(Long id) {
        try {
            instrumentoRepository.deleteById(id);
            return true;
        }catch (Exception e ){
            return false;
        }
    }
}