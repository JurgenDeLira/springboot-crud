package com.jorge.springboot.app.springboot_crud.repositories;

import com.jorge.springboot.app.springboot_crud.entities.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
}
