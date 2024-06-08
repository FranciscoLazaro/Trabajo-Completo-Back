package com.example.Entity.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PedidoDetalleDto {
    private int cantidad;

    private Long idInstrumento;
}
