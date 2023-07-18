package com.lcwd.electronic.store.dto;

import com.lcwd.electronic.store.entity.Cart;
import com.lcwd.electronic.store.entity.CartItem;
import com.lcwd.electronic.store.entity.Product;
import com.lcwd.electronic.store.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDto {

    private int cartItemId;
    private ProductDto product;
    private int quantity;
    private int totalPrice;


}
