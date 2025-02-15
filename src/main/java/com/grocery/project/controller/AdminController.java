package com.grocery.project.controller;

import com.grocery.project.config.AppConstants;
import com.grocery.project.payload.GroceryDTO;
import com.grocery.project.payload.GroceryResponse;
import com.grocery.project.service.GroceryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    GroceryService groceryService;

    @Autowired
    GroceryDTO groceryDTO;

    @PostMapping("/items")
    public ResponseEntity<GroceryDTO> addItem( @RequestBody GroceryDTO groceryDTO) {
        GroceryDTO savedGroceryDTO = groceryService.addItem(groceryDTO);
        return new ResponseEntity<>(savedGroceryDTO,HttpStatus.CREATED);
    }

    @GetMapping("/items")
    public ResponseEntity<GroceryResponse> getAllItems(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_GROCERY_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
        GroceryResponse groceryResponse = groceryService.getAllItems(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(groceryResponse, HttpStatus.OK);
    }

    @DeleteMapping("/items/{groceryId}")
    public ResponseEntity<String> deleteItem(@PathVariable Long groceryId) {
        String status = groceryService.deleteGrocery(groceryId);
        return new ResponseEntity<String>(status, HttpStatus.OK);
    }


    @PutMapping("/items/{groceryId}")
    public ResponseEntity<GroceryDTO> updateItem(@RequestBody GroceryDTO groceryDTO, @PathVariable Long groceryId) {
        GroceryDTO updatedCategoryDTO = groceryService.updateGrocery(groceryDTO, groceryId);
        return new ResponseEntity<>(updatedCategoryDTO, HttpStatus.OK);
    }

}
