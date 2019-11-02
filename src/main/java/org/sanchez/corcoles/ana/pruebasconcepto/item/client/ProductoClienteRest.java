package org.sanchez.corcoles.ana.pruebasconcepto.item.client;

import org.sanchez.corcoles.ana.pruebasconcepto.commons.model.entity.Producto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "servicio-productos")
public interface ProductoClienteRest {

    @GetMapping("/productos")
    List<Producto> findAll();

    @GetMapping("/productos/{id}")
    Producto findBy(@PathVariable Long id);

    @PostMapping("/productos")
    Producto save(@RequestBody Producto producto);

    @PutMapping("/productos/{id}")
    Producto update(@PathVariable Long id, @RequestBody Producto producto);

    @DeleteMapping("/productos/{id}")
    void delete(@PathVariable Long id);
}
