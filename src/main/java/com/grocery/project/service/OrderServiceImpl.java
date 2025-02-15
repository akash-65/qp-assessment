package com.grocery.project.service;

import com.grocery.project.model.GroceryItem;
import com.grocery.project.model.Order;
import com.grocery.project.model.OrderItem;
import com.grocery.project.payload.OrderDTO;
import com.grocery.project.repository.GroceryItemRepository;
import com.grocery.project.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private GroceryItemRepository groceryItemRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Order placeOrder(OrderDTO orderDTO) {

        List<OrderItem> orderItems = orderDTO.getItems().stream().map(itemRequest -> {
            GroceryItem item = groceryItemRepository.findById(itemRequest.getId())
                    .orElseThrow(() -> new RuntimeException("Item not found"));

            if (item.getStock() < itemRequest.getQuantity()) {
                throw new RuntimeException("Insufficient stock for item: " + item.getName());
            }

            item.setStock(item.getStock() - itemRequest.getQuantity()); // Reduce stock
            groceryItemRepository.save(item);

            return new OrderItem(null, null, item, itemRequest.getQuantity());
        }).collect(Collectors.toList());

        double totalAmount = orderItems.stream()
                .mapToDouble(orderItem -> orderItem.getItem().getPrice() * orderItem.getQuantity())
                .sum();

        Order order = new Order();
        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);

        return orderRepository.save(order);

    }
}
