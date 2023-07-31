package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dto.PageableResponse;
import com.lcwd.electronic.store.dto.ProductDto;
import com.lcwd.electronic.store.dto.UserDto;
import com.lcwd.electronic.store.entity.Product;
import com.lcwd.electronic.store.entity.User;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.Column;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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
    @Test
    public void deleteProductTest(){
        String productid="productIdabc" ;
        Mockito.when(productRepository.findById("productIdabc")).thenReturn(Optional.of(product));
        productService.delete(productid);
        Mockito.verify(productRepository,Mockito.times(1)).delete(product);
    }
    @Test
    public void getAllProductTest(){
        Product product1= Product.builder()
                .title("product")
                .description("product for testing")
                .productId("pk34")
                .price(567)
                .productImageName("pk.png")
                .discountedPrice(345)
                // roles(Set.of(role))
                .build();
        Product product2= Product.builder()
                .title("product2")
                .description("product2 for testing")
                .productId("kak34")
                .price(907)
                .productImageName("kl.png")
                .discountedPrice(234)
                // roles(Set.of(role))
                .build();

        List<Product> productList= Arrays.asList(product1,product2);

        Page<Product> page=new PageImpl<>(productList);
        Mockito.when(productRepository.findAll((Pageable)Mockito.any())).thenReturn(page);

//       Sort sort=Sort.by("name").ascending();
//        Pageable pageable= PageRequest.of( 1, 2, sort);
//
        PageableResponse<ProductDto> allProduct=productService.getAll(1,2,"title","asc");
        Assertions.assertEquals(2,allProduct.getContent().size());

    }
    @Test
    public void getProductByIdTest(){
        String productId="userIdTest";
        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        //actual call of service method
        ProductDto productDto = productService.get(productId);
        Assertions.assertNotNull(productDto);
        Assertions.assertEquals(product.getPrice(),productDto.getPrice(),"Name not matched !!!");

    }

}
