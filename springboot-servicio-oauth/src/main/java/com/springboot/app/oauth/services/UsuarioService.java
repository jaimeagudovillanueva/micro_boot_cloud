package com.springboot.app.oauth.services;

import brave.Tracer;
import com.springboot.app.oauth.clients.IUsuarioFeignClient;
import com.springboot.app.usuarioscommons.models.entity.Usuario;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements IUsuarioService, UserDetailsService {

    private Logger log = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    private IUsuarioFeignClient usuarioFeignClient;

    @Autowired
    private Tracer tracer;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
            Usuario usuario = usuarioFeignClient.findByUsername(username);

            List<GrantedAuthority> roles = usuario.getRoles()
                    .stream()
                    .map(rol -> new SimpleGrantedAuthority(rol.getNombre()))
                    .peek(authority -> log.info("Rol: " + authority.getAuthority()))
                    .collect(Collectors.toList());
            log.info("Usuario autenticado: " + username);

            return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(),
                    true, true, true, roles);

        } catch (final FeignException e) {
            final String error = "Error en el login, no existe el usuario '" + username + "' en el sistema";
            log.error(error);
            tracer.currentSpan().tag("error.mensaje", error + ": " + e.getMessage());
            throw new UsernameNotFoundException(error);
        }
    }

    @Override
    public Usuario findByUsername(String username) {
        return usuarioFeignClient.findByUsername(username);
    }

    @Override
    public Usuario update(Usuario usuario, Long id) {
        return usuarioFeignClient.update(usuario, id);
    }
}