package com.catalogo.repository;

import com.catalogo.entities.Categoria;
import com.catalogo.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
