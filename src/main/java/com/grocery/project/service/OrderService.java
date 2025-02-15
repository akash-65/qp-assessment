package com.grocery.project.service;

import com.grocery.project.model.Order;
import com.grocery.project.payload.GroceryDTO;
import com.grocery.project.payload.OrderDTO;

public interface OrderService {
    Order placeOrder(OrderDTO orderDTO);
}
