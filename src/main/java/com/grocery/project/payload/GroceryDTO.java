package com.grocery.project.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class GroceryDTO {

    private Long groceryId;
    private String name;
    private double price;
    private String category;
    private int stock;
}
