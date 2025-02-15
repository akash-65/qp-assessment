package com.grocery.project.service;

import com.grocery.project.expection.APIException;
import com.grocery.project.expection.ResourceNotFoundException;
import com.grocery.project.model.GroceryItem;
import com.grocery.project.payload.GroceryDTO;
import com.grocery.project.payload.GroceryResponse;
import com.grocery.project.repository.GroceryItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroceryServiceImpl implements GroceryService{

    @Autowired
    private GroceryItemRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public GroceryDTO addItem(GroceryDTO groceryDTO) {
        GroceryItem groceryItem = modelMapper.map(groceryDTO, GroceryItem.class);

        // Validation
        GroceryItem groceryItemFromDb = repository.findByName(groceryItem.getName());
        if (groceryItemFromDb != null) {
            throw new APIException("Grocery with the name " + groceryItem.getName() + " already exists !!!");
        }

        // Save
        GroceryItem savedGroceryItem = repository.save(groceryItem);

        return modelMapper.map(savedGroceryItem, GroceryDTO.class);
    }

    @Override
    public GroceryResponse getAllItems(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        // Sorting
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // Pagination
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        Page<GroceryItem> groceryItemPage = repository.findAll(pageDetails);

        List<GroceryItem> groceries = groceryItemPage.getContent();

        // Validation
        if (groceries.isEmpty()) {
            throw new APIException("No Grocery created till now !!!");
        }

        List<GroceryDTO> groceryDTOS = groceries.stream()
                .map(grocery -> modelMapper.map(grocery, GroceryDTO.class))
                .toList();

        // Save the newly created groceryDTO into the groceryResponse along with MetaData
        GroceryResponse groceryResponse = new GroceryResponse();
        groceryResponse.setContent(groceryDTOS);
        groceryResponse.setPageNumber(groceryItemPage.getNumber());
        groceryResponse.setPageSize(groceryItemPage.getSize());
        groceryResponse.setTotalElements(groceryItemPage.getNumberOfElements());
        groceryResponse.setTotalPages(groceryItemPage.getTotalPages());
        groceryResponse.setLastPage(groceryItemPage.isLast());

        return groceryResponse;
    }

    @Override
    public String deleteGrocery(Long groceryId) {

        GroceryItem groceryItem = repository.findById(groceryId)
                .orElseThrow(() -> new ResourceNotFoundException("Grocery", "GroceryId", groceryId));

        repository.delete(groceryItem);

        return "Grocery " + groceryItem.getName() + " has been deleted !!!";
    }

    @Override
    public GroceryDTO updateGrocery(GroceryDTO groceryDTO, Long groceryId) {
        GroceryItem groceryItem = modelMapper.map(groceryDTO, GroceryItem.class);

        // Validation
        GroceryItem savedGroceryItem = repository.findByName(groceryItem.getName());
        if (savedGroceryItem != null) {
            throw new APIException("Grocery with the name " + groceryItem.getName() + " already exists !!!");
        }

        // Save
        groceryItem.setGroceryId(groceryId);
        savedGroceryItem = repository.save(groceryItem);

        return modelMapper.map(savedGroceryItem, GroceryDTO.class);
    }


}
