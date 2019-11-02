package org.sanchez.corcoles.ana.pruebasconcepto.item.service;

import org.sanchez.corcoles.ana.pruebasconcepto.item.model.Item;
import org.sanchez.corcoles.ana.pruebasconcepto.item.model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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

    @Override
    public Producto save(final Producto producto) {
        final HttpEntity httpEntity = new HttpEntity<>(producto);
        final ResponseEntity<Producto> responseEntity = clienteRest.exchange("http://" + SERVICE_NAME_PRODUCTO + "/productos", HttpMethod.POST, httpEntity, Producto.class);
        return responseEntity.getBody();
    }

    @Override
    public Producto update(final Long id, final Producto producto) {
        final HttpEntity httpEntity = new HttpEntity<>(producto);
        final Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put("id", id.toString());
        final ResponseEntity<Producto> responseEntity = clienteRest.exchange("http://" + SERVICE_NAME_PRODUCTO + "/productos/{id}", HttpMethod.PUT, httpEntity, Producto.class, pathVariables);
        return responseEntity.getBody();
    }

    @Override
    public void deleteById(final Long id) {
        final Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put("id", id.toString());
        clienteRest.delete("http://" + SERVICE_NAME_PRODUCTO + "/productos/{id}", pathVariables);
    }
}
