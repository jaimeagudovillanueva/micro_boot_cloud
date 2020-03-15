package com.springboot.app.items.models.service;

import com.springboot.app.items.models.Item;
import com.springboot.app.commons.models.entity.Producto;

import java.util.List;

public interface IItemService {

    List<Item> findAll();
    Item findById(Long id, Integer cantidad);
    Producto save(Producto producto);
    Producto update(Producto producto, Long id);
    void delete(Long id);
}
