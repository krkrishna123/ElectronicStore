package com.lcwd.electronic.store.dto;

import com.lcwd.electronic.store.entity.CartItem;
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
public class CartDto {

    private String cartId;
    private Date createdAt;
    private UserDto  user;
    private List<CartItemDto> items=new ArrayList<>();

}
