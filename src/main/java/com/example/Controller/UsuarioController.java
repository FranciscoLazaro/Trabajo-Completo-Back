package com.example.Controller;

import com.example.Entity.Usuario;
import com.example.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/Usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/getByName")
    public ResponseEntity<?> findById(@RequestParam String nombre){
        return ResponseEntity.ok(usuarioService.findByNombreUsuario(nombre));
    }
    @GetMapping("/existByNameClave")
    public ResponseEntity<?> existByNameClave(@RequestParam String nombre,@RequestParam String clave){
        return ResponseEntity.ok(usuarioService.existsUsuarioByNombreUsuarioAndClave(nombre,clave));
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Usuario usuario){
        return ResponseEntity.ok(usuarioService.save(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @RequestBody Usuario usuario){
        return ResponseEntity.ok(usuarioService.update(usuario, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        return ResponseEntity.ok(usuarioService.delete(id));
    }
}
