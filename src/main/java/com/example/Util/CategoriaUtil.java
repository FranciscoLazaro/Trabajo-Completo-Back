package com.example.Util;

import com.example.Entity.Categoria;
import com.example.Repository.CategoriaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CategoriaUtil implements CommandLineRunner {

    @Autowired
    private CategoriaRepository categoriaRepository;

    private static final Logger logger = LoggerFactory.getLogger(CategoriaUtil.class);

    @Override
    public void run(String... args) throws Exception {
        if (categoriaRepository.count() != 5){
            Categoria categoria = Categoria.builder().id(1L).denominacion("Cuerda").build();
            categoriaRepository.save(categoria);
            categoria.setId(2L);
            categoria.setDenominacion("Viento");
            categoriaRepository.save(categoria);
            categoria.setId(3L);
            categoria.setDenominacion("Percusión");
            categoriaRepository.save(categoria);
            categoria.setId(4L);
            categoria.setDenominacion("Teclado");
            categoriaRepository.save(categoria);
            categoria.setId(5L);
            categoria.setDenominacion("Electrónico");
            categoriaRepository.save(categoria);
        }
        logger.info("{}", categoriaRepository.findAll());
        logger.info("Categorias cargadas");

    }
}
