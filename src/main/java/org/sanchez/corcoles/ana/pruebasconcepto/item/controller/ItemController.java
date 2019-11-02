package org.sanchez.corcoles.ana.pruebasconcepto.item.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.sanchez.corcoles.ana.pruebasconcepto.item.model.Item;
import org.sanchez.corcoles.ana.pruebasconcepto.item.model.Producto;
import org.sanchez.corcoles.ana.pruebasconcepto.item.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ItemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private Environment environment;

    @Autowired
    @Qualifier("itemServiceWithFeign")
    //@Qualifier("itemServiceWithRestTemplate")
    private ItemService itemService;

    @Value("${configuracion.texto}")
    private String texto;

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

    @GetMapping("/obtener-config")
    public ResponseEntity<?> getConfig(@Value("${server.port}") String port) {

        LOGGER.info(texto);

        final Map<String, String> json = new HashMap<>();
        json.put("texto", texto);
        json.put("port", port);

        if (environment.getActiveProfiles().length > 0 && environment.getActiveProfiles()[0].equalsIgnoreCase("dev")) {
            json.put("autor.nombre", environment.getProperty("configuracion.autor.nombre"));
            json.put("autor.email", environment.getProperty("configuracion.autor.email"));
        }

        return new ResponseEntity<>(json, HttpStatus.OK);
    }
}
