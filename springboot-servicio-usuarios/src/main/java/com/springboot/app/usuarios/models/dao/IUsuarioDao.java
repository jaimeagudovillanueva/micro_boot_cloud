package com.springboot.app.usuarios.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.springboot.app.usuarioscommons.models.entity.Usuario;

@RepositoryRestResource(path="usuarios")
public interface IUsuarioDao extends PagingAndSortingRepository<Usuario, Long>{

	@RestResource(path="buscar-username")
	Usuario findByUsername(@Param("username") String username);
	
	@Query("from Usuario where username=?1")
	Usuario obtenerPorUsername(String username);
}
