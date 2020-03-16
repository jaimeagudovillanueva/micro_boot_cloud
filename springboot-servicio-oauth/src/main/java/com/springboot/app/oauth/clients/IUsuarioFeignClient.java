package com.springboot.app.oauth.clients;

import com.springboot.app.usuarioscommons.models.entity.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="servicio-usuarios")
public interface IUsuarioFeignClient {

    @GetMapping("/usuarios/search/buscar-username")
    Usuario findByUsername(@RequestParam String username);

    @PutMapping("/usuarios/usuarios/{id}")
    Usuario update(@RequestBody Usuario usuario, @PathVariable Long id);
}
