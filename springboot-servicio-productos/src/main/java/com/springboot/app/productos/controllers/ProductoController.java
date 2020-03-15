package com.springboot.app.productos.controllers;

import com.springboot.app.commons.models.entity.Producto;
import com.springboot.app.productos.models.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductoController {

    // Esto lo dejo para ver que se pueden obtener atributos de properties usando Environment pero es mejor usar Value
    @Autowired
    private Environment env;

    @Value("${server.port}")
    private Integer port;

    @Autowired
    private IProductoService productoService;

    @GetMapping("/listar")
    public List<Producto> listar() {
        return productoService.findAll().stream().map(p ->
        {
            // p.setPort(Integer.parseInt(env.getProperty("local.server.port")))
            p.setPort(port);
        ;
        return p;
        }).collect(Collectors.toList());
    }

    @GetMapping("/ver/{id}")
    public Producto detalle(@PathVariable final Long id) {
        Producto producto = productoService.findById(id);
        // producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
        producto.setPort(port);
        return producto;
    }

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public Producto crear(@RequestBody Producto producto) {
        return productoService.save(producto);
    }

    @PutMapping("/editar/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Producto modificar(@RequestBody Producto producto, @PathVariable final Long id) {
        final Producto productoExistente = productoService.findById(id);
        productoExistente.setNombre(producto.getNombre());
        productoExistente.setPrecio(producto.getPrecio());

        return productoService.save(productoExistente);
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable final Long id) {
        productoService.delete(id);
    }
}
