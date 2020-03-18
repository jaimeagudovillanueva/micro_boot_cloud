package com.springboot.app.oauth.security.event;

import brave.Tracer;
import com.springboot.app.oauth.services.IUsuarioService;
import com.springboot.app.usuarioscommons.models.entity.Usuario;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {

    private Logger log = LoggerFactory.getLogger(AuthenticationSuccessErrorHandler.class);

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private Tracer tracer;

    @Override
    public void publishAuthenticationSuccess(Authentication authentication) {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        log.info("Success Login: " + user.getUsername());


        final Usuario usuario = usuarioService.findByUsername(authentication.getName());

        if (usuario.getIntentos() != null && usuario.getIntentos() > 0) {
            usuario.setIntentos(0);
            usuarioService.update(usuario, usuario.getId());
        }
    }

    @Override
    public void publishAuthenticationFailure(AuthenticationException e, Authentication authentication) {

        final String errorLogin = "Error en el Login: " + e.getMessage();
        log.error(errorLogin);
        try {

            StringBuilder errors = new StringBuilder();
            errors.append(errorLogin);
            final Usuario usuario = usuarioService.findByUsername(authentication.getName());

            if (usuario.getIntentos() == null) {
                usuario.setIntentos(0);
            }

            usuario.setIntentos(usuario.getIntentos() + 1);

            errors.append(" - ").append("Intentos de login: " + usuario.getIntentos());

            if (usuario.getIntentos() >= 3) {
                final String errorMaxIntentos = String.format("El usuario %s deshabilitado por máximo de intentos", authentication.getName());
                log.error(errorMaxIntentos);
                errors.append(" - ").append(errorMaxIntentos);
                usuario.setEnabled(false);
            }
            usuarioService.update(usuario, usuario.getId());

            tracer.currentSpan().tag("error.mensaje", errors.toString());

        } catch (final FeignException fe) {
            log.error(String.format("El usuario %s no existe en el sistema", authentication.getName()));
        }
    }
}
