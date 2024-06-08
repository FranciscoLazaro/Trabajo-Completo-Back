package com.example.Controller;

import com.example.Entity.Dto.PedidoDto;
import com.example.Entity.Pedido;
import com.example.Service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/pedido")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(pedidoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody PedidoDto pedido) {
        return ResponseEntity.ok(pedidoService.save(pedido));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @RequestBody Pedido pedido) {
        return ResponseEntity.ok(pedidoService.update(pedido, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.delete(id));
    }

    @GetMapping("/agruparPorAño")
    public ResponseEntity<?> getPedidosPorMesyAño() {
        return ResponseEntity.ok(pedidoService.findByMesYaño());
    }

    @GetMapping("/groupedByInstrument")
    public List<List<Object>> getPedidosGroupedByInstrument() {
        return pedidoService.findByInstrumentoAgrupado().stream()
                .map(p -> List.of(p[0], p[1]))
                .collect(Collectors.toList());
    }
}
