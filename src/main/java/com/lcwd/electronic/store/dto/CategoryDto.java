package com.lcwd.electronic.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private String categoryId;

   @NotBlank
   @Min(value=4,message="title must be of minimum 4 character !!! ")
    private String title;

   @NotBlank(message="Description required !!!")
    private String description;


    private String coverImage;

}
