package com.jorge.springboot.app.springboot_crud.services;

import com.jorge.springboot.app.springboot_crud.entities.User;

import java.util.List;

public interface UserService {

    List<User> findAll();


    User save(User user);


}
