package com.springboot.app.items.controllers;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.springboot.app.items.models.Item;
import com.springboot.app.commons.models.entity.Producto;
import com.springboot.app.items.models.service.IItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RefreshScope
@RestController
public class ItemController {

    private static Logger log = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private Environment env;

    @Autowired
    // El Qualifier no haría falta con el @Primary en ItemServiceFeign, es una alternativa
    @Qualifier("serviceFeign")
    private IItemService itemService;

    @Value("${configuracion.texto}")
    private String texto;

    @GetMapping("/listar")
    public List<Item> listar() {
        return itemService.findAll();
    }

    @HystrixCommand(fallbackMethod = "metodoAlternativo")
    @GetMapping("/ver/{id}/cantidad/{cantidad}")
    public Item detalle(@PathVariable final Long id, @PathVariable final Integer cantidad) {
        return itemService.findById(id, cantidad);
    }

    public Item metodoAlternativo(final Long id, final Integer cantidad) {
        Item item = new Item();
        Producto producto = new Producto();
        producto.setId(id);
        producto.setNombre("Camara Sony");
        producto.setPrecio(500.00);

        item.setProducto(producto);
        item.setCantidad(cantidad);

        return item;
    }

    @GetMapping("/obtener-config")
    public ResponseEntity<?> obtenerConfig(@Value("${server.port}") String puerto) {

        log.info(texto);

        Map<String, String> json = new HashMap<>();
        json.put("texto", texto);
        json.put("puerto", puerto);

        if (env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev")) {
            json.put("autor.nombre", env.getProperty("configuracion.autor.nombre"));
            json.put("autor.email", env.getProperty("configuracion.autor.email"));
        }

        return new ResponseEntity<Map<String, String>>(json, HttpStatus.OK);
    }

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public Producto crear(@RequestBody Producto producto) {
        return itemService.save(producto);
    }

    @PutMapping("/editar/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Producto crear(@RequestBody Producto producto, @PathVariable Long id) {
        return itemService.update(producto, id);
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        itemService.delete(id);
    }
}
