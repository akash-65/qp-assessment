package com.grocery.project.payload;

import lombok.Data;

@Data
public class ItemRequest {
    private Long id;
    private int quantity;
}
