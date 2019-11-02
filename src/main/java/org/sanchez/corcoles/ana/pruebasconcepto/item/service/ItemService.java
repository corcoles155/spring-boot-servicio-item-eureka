package org.sanchez.corcoles.ana.pruebasconcepto.item.service;

import org.sanchez.corcoles.ana.pruebasconcepto.item.model.Item;
import org.sanchez.corcoles.ana.pruebasconcepto.item.model.Producto;

import java.util.List;

public interface ItemService {

    List<Item> findAll();

    Item findBy(Long id, Integer cantidad);

    Producto save(Producto producto);

    Producto update(Long id, Producto producto);

    void deleteById(Long id);
}
