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

import java.util.Optional;

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
    @Test
    public void updateCategoryTest(){
        String categoryId="dhekljdjl";
        CategoryDto categoryDto=CategoryDto.builder()
                .title("developer")
                .description("This test case for update category")
                .coverImage("xyz.png")
                .build();
        Mockito.when(categoryRepository.findById(Mockito.anyString())).thenReturn(Optional.of(category));
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);
        CategoryDto updateCategory = categoryService.update(categoryDto, categoryId);

        System.out.println(updateCategory.getTitle());
        System.out.println(updateCategory.getCoverImage());
        Assertions.assertNotNull(categoryDto);
        Assertions.assertEquals(categoryDto.getTitle(),updateCategory.getTitle(),"Name is not validated");


    }
    @Test
    public void deleteCategoryTest(){
        String categoryid="userIdabc" ;
        Mockito.when(categoryRepository.findById("userIdabc")).thenReturn(Optional.of(category));
        categoryService.delete(categoryid);
        Mockito.verify(categoryRepository,Mockito.times(1)).delete(category);
    }
    
    }

