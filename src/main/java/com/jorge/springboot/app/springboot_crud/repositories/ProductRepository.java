package com.jorge.springboot.app.springboot_crud.repositories;

import com.jorge.springboot.app.springboot_crud.entities.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
