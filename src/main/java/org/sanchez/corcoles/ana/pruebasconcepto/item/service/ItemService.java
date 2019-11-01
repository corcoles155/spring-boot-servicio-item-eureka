package org.sanchez.corcoles.ana.pruebasconcepto.item.service;

import org.sanchez.corcoles.ana.pruebasconcepto.item.model.Item;

import java.util.List;

public interface ItemService {

    List<Item> findAll();

    Item findBy(Long id, Integer cantidad);
}
