package org.sanchez.corcoles.ana.pruebasconcepto.item.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.sanchez.corcoles.ana.pruebasconcepto.item.model.Item;
import org.sanchez.corcoles.ana.pruebasconcepto.item.model.Producto;
import org.sanchez.corcoles.ana.pruebasconcepto.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class ItemController {

    @Autowired
    @Qualifier("itemServiceWithFeign")
    //@Qualifier("itemServiceWithRestTemplate")
    private ItemService itemService;

    @GetMapping("/items")
    public List<Item> findAll() {
        return itemService.findAll();
    }

    @HystrixCommand(fallbackMethod = "metodoAlternativo")
    @GetMapping("/items/{id}/cantidad/{cantidad}")
    public Item findById(@PathVariable Long id, @PathVariable Integer cantidad) {
        return itemService.findBy(id, cantidad);
    }

    public Item metodoAlternativo(Long id, Integer cantidad) {
        return new Item(new Producto(id, "Producto999", 500.00, new Date()), cantidad);
    }
}
