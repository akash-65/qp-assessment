package com.grocery.project.service;

import com.grocery.project.payload.GroceryDTO;
import com.grocery.project.payload.GroceryResponse;


public interface GroceryService {
    GroceryDTO addItem(GroceryDTO item);

    GroceryResponse getAllItems(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    String deleteGrocery(Long groceryId);

    GroceryDTO updateGrocery(GroceryDTO groceryDTO, Long groceryId);
}
