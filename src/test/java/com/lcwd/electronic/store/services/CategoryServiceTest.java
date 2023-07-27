package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dto.CategoryDto;
import com.lcwd.electronic.store.entity.Category;
import com.lcwd.electronic.store.repository.CategoryRepository;
import com.lcwd.electronic.store.service.CategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class CategoryServiceTest {

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    Category category;

    @Autowired
    private ModelMapper mapper;
    @BeforeEach
    public void init() {
        category= Category.builder()
                .title("Krishna ")
                .description("This is testing for Category Method")
                .coverImage("krishna.png")
                .build();

    }

//testing for the create category
@Test
public void createCategoryTest(){

    Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);
    CategoryDto category1 = categoryService.create(mapper.map(category, CategoryDto.class));
    System.out.println(category1.getCoverImage());
    Assertions.assertNotNull(category1);
    Assertions.assertEquals("krishna.png",category1.getCoverImage());

}

    }

