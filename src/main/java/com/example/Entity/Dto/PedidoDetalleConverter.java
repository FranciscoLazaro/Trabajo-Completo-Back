package com.example.Entity.Dto;

import com.example.Entity.PedidoDetalle;
import com.example.Service.InstrumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoDetalleConverter {

    @Autowired
    private InstrumentoService instrumentoService;

    public PedidoDetalle toEntity(PedidoDetalleDto pd){
        PedidoDetalle pedidoDetalle = new PedidoDetalle();
        pedidoDetalle.setCantidad(pd.getCantidad());
        pedidoDetalle.setInstrumento(instrumentoService.findById(pd.getIdInstrumento()));
        return pedidoDetalle;
    }

}
