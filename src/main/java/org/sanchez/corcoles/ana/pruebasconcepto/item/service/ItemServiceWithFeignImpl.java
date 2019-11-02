package org.sanchez.corcoles.ana.pruebasconcepto.item.service;

import org.sanchez.corcoles.ana.pruebasconcepto.item.client.ProductoClienteRest;
import org.sanchez.corcoles.ana.pruebasconcepto.item.model.Item;
import org.sanchez.corcoles.ana.pruebasconcepto.item.model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("itemServiceWithFeign")
public class ItemServiceWithFeignImpl implements ItemService {

    @Autowired
    private ProductoClienteRest clienteFeign;

    @Override
    public List<Item> findAll() {
        return clienteFeign.findAll().stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
    }

    @Override
    public Item findBy(final Long id, final Integer cantidad) {
        final Producto producto = clienteFeign.findBy(id);
        return new Item(producto, cantidad);
    }

    @Override
    public Producto save(final Producto producto) {
        return clienteFeign.save(producto);
    }

    @Override
    public Producto update(Long id, Producto producto) {
        return clienteFeign.update(id, producto);
    }

    @Override
    public void deleteById(Long id) {
        clienteFeign.delete(id);
    }
}
