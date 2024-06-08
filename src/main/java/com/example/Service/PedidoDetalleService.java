package com.example.Service;

import com.example.Entity.Dto.PedidoDetalleDto;
import com.example.Entity.PedidoDetalle;

import java.util.List;

public interface PedidoDetalleService {
    PedidoDetalle save(PedidoDetalleDto pedidoDetalle);
    PedidoDetalle update(PedidoDetalle pedidoDetalle, Long id);
    PedidoDetalle findById(Long id);
    List<PedidoDetalle> findAll();
    Boolean delete(Long id);
}
