package org.sanchez.corcoles.ana.pruebasconcepto.item.service;

import org.sanchez.corcoles.ana.pruebasconcepto.item.model.Item;
import org.sanchez.corcoles.ana.pruebasconcepto.item.model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("itemServiceWithRestTemplate")
public class ItemServiceWithRestTemplateImpl implements ItemService {

    private static final String SERVICE_NAME_PRODUCTO = "servicio-productos";

    @Autowired
    private RestTemplate clienteRest;

    @Override
    public List<Item> findAll() {
        final List<Producto> productos = Arrays.asList(clienteRest.getForObject("http://" + SERVICE_NAME_PRODUCTO + "/productos", Producto[].class));
        return productos.stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
    }

    @Override
    public Item findBy(Long id, Integer cantidad) {
        final Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put("id", id.toString());
        final Producto producto = clienteRest.getForObject("http://" + SERVICE_NAME_PRODUCTO + "/productos/{id}", Producto.class, pathVariables);
        return new Item(producto, cantidad);
    }
}
