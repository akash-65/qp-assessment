package com.grocery.project.payload;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@Getter
@Setter
public class OrderDTO {
    private List<ItemRequest> items;
}
