package com.springboot.app.items.clientes;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.springboot.app.commons.models.entity.Producto;

import java.util.List;

@FeignClient(name = "servicio-productos")
public interface IProductoClienteRest {

    @GetMapping("/listar")
    public List<Producto> list();

    @GetMapping("/ver/{id}")
    public Producto detail(@PathVariable Long id);

    @PostMapping("/crear")
    public Producto save(@RequestBody Producto producto);

    @PutMapping("/editar/{id}")
    public Producto update(@RequestBody Producto producto, @PathVariable Long id);

    @DeleteMapping("/eliminar/{id}")
    public void delete(@PathVariable Long id);
}
