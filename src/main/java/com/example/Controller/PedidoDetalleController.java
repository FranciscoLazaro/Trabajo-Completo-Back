package com.example.Controller;

import com.example.Entity.Dto.PedidoDetalleDto;
import com.example.Entity.PedidoDetalle;
import com.example.Service.PedidoDetalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/pedidoDetalle")
public class PedidoDetalleController {

    @Autowired
    private PedidoDetalleService pedidoDetalleService;

    @GetMapping
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(pedidoDetalleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return ResponseEntity.ok(pedidoDetalleService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody PedidoDetalleDto pedidoDetalle){
        return ResponseEntity.ok(pedidoDetalleService.save(pedidoDetalle));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @RequestBody PedidoDetalle pedidoDetalle){
        return ResponseEntity.ok(pedidoDetalleService.update(pedidoDetalle, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        return ResponseEntity.ok(pedidoDetalleService.delete(id));
    }
}