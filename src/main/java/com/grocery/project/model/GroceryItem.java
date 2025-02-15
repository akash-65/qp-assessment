package com.grocery.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "GroceryItem")
public class GroceryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groceryId;

    @NotBlank(message = "Grocery name cannot be blank !!!")
    private String name;

    @Min(value = 1, message = "Price must be at least 1")
    private double price;

    @NotBlank(message = "Category cannot be blank !!!")
    private String category;

    @Min(value = 1, message = "Stock must be at least 1")
    private int stock;

}
