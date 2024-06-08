package com.example.Repository;

import com.example.Entity.Instrumento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstrumentoRepository extends JpaRepository<Instrumento,Long> {
    List<Instrumento> findByCategoriaId(Long id);
}

