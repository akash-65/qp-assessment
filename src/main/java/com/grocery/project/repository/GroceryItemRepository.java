package com.grocery.project.repository;

import com.grocery.project.model.GroceryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroceryItemRepository extends JpaRepository<GroceryItem, Long> {

    GroceryItem findByName(String name);

}
