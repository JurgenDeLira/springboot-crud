package com.jorge.springboot.app.springboot_crud.validation;

import com.jorge.springboot.app.springboot_crud.services.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExistsByUserNameValidation implements ConstraintValidator<ExistsByUserName, String> {

    @Autowired
    private UserService service;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context){
        return !service.existsByUsername(username);
    }
}
