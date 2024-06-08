package com.example.Repository;

import com.example.Entity.Pedido;
import jakarta.persistence.TemporalType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findAllByFechaPedidoBetween(@Temporal(TemporalType.DATE) Date fechaDesde, @Temporal(TemporalType.DATE) Date fechaHasta);

    @Query("SELECT " +
            "YEAR(p.fechaPedido) as year, " +
            "MONTH(p.fechaPedido) as month, " +
            "COUNT(p) as count " +
            "FROM Pedido p " +
            "GROUP BY YEAR(p.fechaPedido), MONTH(p.fechaPedido) " +
            "ORDER BY YEAR(p.fechaPedido), MONTH(p.fechaPedido)")
    List<Object[]> findPedidosGroupedByMonthAndYear();

    @Query("SELECT pd.instrumento.instrumento, SUM(pd.cantidad) " +
            "FROM PedidoDetalle pd " +
            "GROUP BY pd.instrumento.instrumento")
    List<Object[]> findPedidosDetalleGroupedByInstrumento();



}
