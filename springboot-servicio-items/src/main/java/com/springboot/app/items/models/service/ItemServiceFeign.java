package com.springboot.app.items.models.service;

import com.springboot.app.items.clientes.IProductoClienteRest;
import com.springboot.app.items.models.Item;
import com.springboot.app.commons.models.entity.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// Si no se especifica nombre el nombre es el de la clase empezando por minúscula
@Service("serviceFeign")
// @Primary se utiliza para discriminar que implementación de la interfaz se va a utilizar al inyectar el servicio
@Primary
public class ItemServiceFeign implements IItemService {

    @Autowired
    private IProductoClienteRest clienteFeign;

    @Override
    public List<Item> findAll() {
        return clienteFeign.list().stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
    }

    @Override
    public Item findById(Long id, Integer cantidad) {
        return new Item(clienteFeign.detail(id), cantidad);
    }

    @Override
    public Producto save(Producto producto) {
        return clienteFeign.save(producto);
    }

    @Override
    public Producto update(Producto producto, Long id) {
        return clienteFeign.update(producto, id);
    }

    @Override
    public void delete(Long id) {
    clienteFeign.delete(id);
    }
}
