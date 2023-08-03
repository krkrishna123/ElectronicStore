package com.lcwd.electronic.store.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcwd.electronic.store.dto.CategoryDto;
import com.lcwd.electronic.store.dto.PageableResponse;
import com.lcwd.electronic.store.entity.Category;
import com.lcwd.electronic.store.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {
    @MockBean
    private CategoryService categoryService;

    // private Role role;
    private Category category;
    @Autowired
    private ModelMapper mapper;



    @Autowired
    private MockMvc mockMvc;
    @BeforeEach
    public void init() {
        // role=Role.builder().roleId("abc").roleName("NORMAL").build()  ;

        category = Category.builder()
                .title("This is testing1 for GetAllCategory Method")
                .coverImage("kks.png")
                .description("lcwd")
                .build();
    }

    @Test
    public void createCategoryTest() throws Exception {
        //user+post+user Data as json
        //data as json +status cerated
        CategoryDto dto=mapper.map(category, CategoryDto.class);
        Mockito.when(categoryService.create(Mockito.any())).thenReturn(dto);
        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/categorys")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(category))
                                .accept(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isCreated())//controller me created status code(201) isliye liye hai agar waha OK ho fail hoga test
                .andExpect(jsonPath("$.title").exists());


    }
    @Test
    public void updateCategoryTest() throws Exception {
        // /users/{userId}+PUT request+json
        String categoryId="123";
        CategoryDto dto=this.mapper.map(category,CategoryDto.class);
        Mockito.when(categoryService.update(Mockito.any(),Mockito.anyString())).thenReturn(dto);
        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/categorys/" +categoryId)
                                //  .headers(HttpHeaders.AUTHORIZATION,"bearer..........")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(category))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());
    }
    private String convertObjectToJsonString(Category category){
        try{
            return new ObjectMapper().writeValueAsString(category);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //get All user:testing
    @Test
    public void getAllCategoryTest() throws Exception {
        CategoryDto object1 = CategoryDto.builder().title("krishna").coverImage("krishna.png").description("testing").build();
        CategoryDto object2 = CategoryDto.builder().title("karan").coverImage("karan.png").description("developing").build();
        CategoryDto object3 = CategoryDto.builder().title("Raju").coverImage("Raju.png").description("testing").build();

        PageableResponse<CategoryDto> pageableResponse=new PageableResponse<>();
        pageableResponse.setContent(Arrays.asList(
                object1,object2,object3
        ));

        pageableResponse.setLastPage(false);

        pageableResponse.setPageSize(10);
        pageableResponse.setPageNumber(100);
        pageableResponse.setTotalElements(1000);
//        String userId="123";
//        UserDto dto=this.mapper.map(user,UserDto.class);
        Mockito.when(categoryService.getAll(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponse);


        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/category" )
                                // .headers(HttpHeaders.AUTHORIZATION,"Bearer.......")
                                .contentType(MediaType.APPLICATION_JSON)
                                // .content(convertObjectToJsonString(user))
                                .accept(MediaType.APPLICATION_JSON)

                )
                .andDo(print())
                .andExpect(status().isOk());
    }


}
