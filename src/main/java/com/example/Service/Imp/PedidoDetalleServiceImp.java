package com.example.Service.Imp;

import com.example.Entity.Dto.PedidoDetalleConverter;
import com.example.Entity.Dto.PedidoDetalleDto;
import com.example.Entity.PedidoDetalle;
import com.example.Repository.PedidoDetalleRepository;
import com.example.Service.PedidoDetalleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoDetalleServiceImp implements PedidoDetalleService {

    private static final Logger logger = LoggerFactory.getLogger(PedidoDetalleServiceImp.class);

    @Autowired
    private PedidoDetalleRepository pedidoDetalleRepository;

    @Autowired
    private PedidoDetalleConverter pedidoDetalleConverter;

    @Override
    public PedidoDetalle save(PedidoDetalleDto pedidoDetalle) {
        return pedidoDetalleRepository.save(pedidoDetalleConverter.toEntity(pedidoDetalle));
    }

    @Override
    public PedidoDetalle update(PedidoDetalle pedidoDetalle, Long id) {
        var pedidoDetalleDB = pedidoDetalleRepository.findById(id);
        if (pedidoDetalleDB.isEmpty()){
            logger.error("No se encontro una entidad con el id " + pedidoDetalle.getId());
            throw new RuntimeException("No se encontro una entidad con el id " + pedidoDetalle.getId());
        }
        pedidoDetalle.setId(id);
        var newEntity = pedidoDetalleRepository.save(pedidoDetalle);
        logger.info("Actualizada entidad {}",newEntity);
        return newEntity;
    }

    @Override
    public PedidoDetalle findById(Long id) {
        return pedidoDetalleRepository.findById(id).orElseThrow(()-> new NullPointerException("No se encontro pedido detalle con el id "+ id));
    }

    @Override
    public List<PedidoDetalle> findAll() {
        return pedidoDetalleRepository.findAll();
    }

    @Override
    public Boolean delete(Long id) {
        try {
            pedidoDetalleRepository.deleteById(id);
            return true;
        }catch (Exception e ){
            return false;
        }
    }
}
