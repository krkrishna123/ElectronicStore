package com.lcwd.electronic.store.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private String categoryId;

   @NotBlank(message="title is required !!")
   @Size(min=4,message="title must be of minimum 4 characters !!! ")
    private String title;

  @NotBlank(message="Description required !!!")
    private String description;


    private String coverImage;

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    @ToString
    public static class ProductDto {


        private String productId;
        private String title;
        private String description;
        private int price;
        private int discountedPrice;
        private int quantity;
        private Date addedDate;
        private  boolean live;
        private  boolean stock;

    }
}
