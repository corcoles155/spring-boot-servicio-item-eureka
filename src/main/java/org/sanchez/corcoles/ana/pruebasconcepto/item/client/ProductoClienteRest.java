package org.sanchez.corcoles.ana.pruebasconcepto.item.client;

import org.sanchez.corcoles.ana.pruebasconcepto.item.model.Producto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "servicio-productos")
public interface ProductoClienteRest {

    @GetMapping("/productos")
    List<Producto> findAll();

    @GetMapping("/productos/{id}")
    Producto findBy(@PathVariable Long id);
}
