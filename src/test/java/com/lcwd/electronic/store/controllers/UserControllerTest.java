package com.lcwd.electronic.store.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcwd.electronic.store.dto.UserDto;
import com.lcwd.electronic.store.entity.User;
import com.lcwd.electronic.store.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import javax.print.attribute.standard.Media;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//API(controller) ko test krne ka Best Tarika yahi used kare//

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @MockBean
    private UserService userService;

   // private Role role;
    private User user;
    @Autowired
    private ModelMapper mapper;



    @Autowired
    private MockMvc mockMvc;
    @BeforeEach
    public void init() {
        // role=Role.builder().roleId("abc").roleName("NORMAL").build()  ;

        user = User.builder()
                .name("Krishna")//yaha jo bh niche tk de rahe uskko validation ko  dekhr rahe nii fail hoga test
                .email("krishnakumar3454878@gmail.com")
                .about("This is testing for CreateUser Method")
                .gender("Male")
                .imageName("abc.png")
                .password("lcwd")
                // roles(Set.of(role))
                .build();
        //roleId="abc";
    }
        @Test
    public void createUserTest() throws Exception {
        //user+post+user Data as json
        //data as json +status cerated
        UserDto dto=mapper.map(user, UserDto.class);
        Mockito.when(userService.createdUser(Mockito.any())).thenReturn(dto);
this.mockMvc.perform(
        MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonString(user))
                .accept(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isCreated())//controller me created status code(201) isliye liye hai agar waha OK ho fail hoga test
                .andExpect(jsonPath("$.name").exists());


    }
    @Test
    public void updateUserTest() throws Exception {
        // /users/{userId}+PUT request+json
        String userId="123";
        UserDto dto=this.mapper.map(user,UserDto.class);
        Mockito.when(userService.updateUser(Mockito.any(),Mockito.anyString())).thenReturn(dto);
        this.mockMvc.perform(
                MockMvcRequestBuilders.put("/users/" +userId)
                      //  .headers(HttpHeaders.AUTHORIZATION,"bearer..........")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(user))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());
    }
private String convertObjectToJsonString(User user){
        try{
            return new ObjectMapper().writeValueAsString(user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
}


}
