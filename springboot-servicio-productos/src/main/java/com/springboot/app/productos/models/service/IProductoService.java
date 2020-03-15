package com.springboot.app.productos.models.service;

import com.springboot.app.commons.models.entity.Producto;

import java.util.List;

public interface IProductoService {

    List<Producto> findAll();
    Producto findById(Long id);
    Producto save(Producto producto);
    void delete(Long id);
}
