package com.example.Service;

import com.example.Entity.Dto.PedidoDto;
import com.example.Entity.Pedido;
import com.example.Repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public interface PedidoService {

    Pedido save(PedidoDto pedido);

    Pedido update(Pedido pedido, Long id);

    Pedido findById(Long id);

    List<Pedido> findAll();

    Boolean delete(Long id);

    public List<Pedido> findByFecha(Date fechaDesde, Date fechaHasta);

    public List<Object[]> findByMesYaño();
    public List<Object[]> findByInstrumentoAgrupado();
}
