package com.example.Service.Imp;

import com.example.Entity.Dto.PedidoConverter;
import com.example.Entity.Dto.PedidoDto;
import com.example.Entity.Pedido;
import com.example.Repository.PedidoRepository;
import com.example.Service.PedidoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Date;

@Service
public class PedidoServiceImp implements PedidoService {

    private static final Logger logger = LoggerFactory.getLogger(PedidoServiceImp.class);

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoConverter pedidoConverter;

    @Override
    public Pedido save(PedidoDto pedido) {
        return pedidoRepository.save(pedidoConverter.toEntity(pedido));
    }

    @Override
    public Pedido update(Pedido pedido, Long id) {
        var pedidoDB = pedidoRepository.findById(id);
        if (pedidoDB.isEmpty()){
            logger.error("No se encontro una entidad con el id " + pedido.getId());
            throw new RuntimeException("No se encontro una entidad con el id " + pedido.getId());
        }
        pedido.setId(id);
        var newEntity = pedidoRepository.save(pedido);
        logger.info("Actualizada entidad {}",newEntity);
        return newEntity;
    }

    @Override
    public Pedido findById(Long id) {
        return pedidoRepository.findById(id).orElseThrow(()-> new NullPointerException("No se encontro pedido con el id "+ id));
    }

    @Override
    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    @Override
    public Boolean delete(Long id) {
        try {
            pedidoRepository.deleteById(id);
            return true;
        }catch (Exception e ){
            return false;
        }
    }

    @Override
    public List<Pedido> findByFecha(Date fechaDesde, Date fechaHasta) {
            return pedidoRepository.findAllByFechaPedidoBetween(fechaDesde,fechaHasta);
    }

    @Override
    public List<Object[]> findByMesYaño() {
        return pedidoRepository.findPedidosGroupedByMonthAndYear();
    }

    @Override
    public List<Object[]> findByInstrumentoAgrupado() {
        return pedidoRepository.findPedidosDetalleGroupedByInstrumento();
    }


}
