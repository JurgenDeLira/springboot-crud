package com.jorge.springboot.app.springboot_crud.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min=3, max = 20)
    private String name;

    @Min(500)
    @NotNull
    private Integer price;

    @NotBlank
    private String description;

}
