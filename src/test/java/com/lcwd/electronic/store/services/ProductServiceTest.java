package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dto.ProductDto;
import com.lcwd.electronic.store.entity.Product;
import com.lcwd.electronic.store.repository.ProductRepository;
import com.lcwd.electronic.store.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.Column;
import java.util.Date;
import java.util.Optional;

@SpringBootTest
public class ProductServiceTest {

 @MockBean
private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    Product product;

    @Autowired
    private ModelMapper mapper;

    @BeforeEach
    public void init() {
        product = Product.builder()
                .title("Car")
                .description("test for the prodtuct services")
                .price(406)
                .discountedPrice(345)
                .quantity(7)
                .productImageName("kr.png")
                .build();
    }
    //create user Test
    @Test
    public void createUserTest(){

        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);
        ProductDto product1 = productService.create(mapper.map(product, ProductDto.class));
        System.out.println(product1.getTitle());
        Assertions.assertNotNull(product1);

        Assertions.assertEquals("Car",product1.getTitle());

    }
    //update poduct

    @Test
    public void updateUserTest() {
        String userId = "dhekljdjl";
        ProductDto productDto = ProductDto.builder()
                .title("Aeroplane")
                .description("test for the updated prodtuct services")
                .price(678)
                .discountedPrice(234)
                .quantity(8)
                .productImageName("indigo.png")
                .build();
        Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);
        ProductDto updateProduct = productService.update(productDto, userId);

        System.out.println(updateProduct.getTitle());
        System.out.println(updateProduct.getProductImageName());
        Assertions.assertNotNull(productDto);
        Assertions.assertEquals(productDto.getTitle(), updateProduct.getTitle(), "Name is not validated");

    }
}
