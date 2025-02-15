package com.grocery.project.controller;

import com.grocery.project.config.AppConstants;
import com.grocery.project.model.Order;
import com.grocery.project.payload.GroceryDTO;
import com.grocery.project.payload.GroceryResponse;
import com.grocery.project.payload.OrderDTO;
import com.grocery.project.service.GroceryService;
import com.grocery.project.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    GroceryService groceryService;

    @Autowired
    OrderService orderService;

    @Autowired
    GroceryDTO groceryDTO;

    @Autowired
    OrderDTO orderDTO;

    @GetMapping("/items")
    public ResponseEntity<GroceryResponse> getAllItems(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_GROCERY_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
        GroceryResponse groceryResponse = groceryService.getAllItems(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(groceryResponse, HttpStatus.OK);
    }

    @PostMapping("/orders")
    public ResponseEntity<Order> placeOrder(@RequestBody OrderDTO orderDTO) {
        Order createdOrder = orderService.placeOrder(orderDTO);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

}
