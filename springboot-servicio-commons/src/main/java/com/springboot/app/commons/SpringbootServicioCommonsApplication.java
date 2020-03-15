package com.springboot.app.commons;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
// Al usar JPA Spring Boot obliga a definir una base de datos, para evitar tener la dependencia
// de H2 excluimos la configuracion de base de datos
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class SpringbootServicioCommonsApplication {
}
