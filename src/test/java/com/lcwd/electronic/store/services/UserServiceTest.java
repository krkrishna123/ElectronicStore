package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dto.UserDto;
import com.lcwd.electronic.store.entity.User;
import com.lcwd.electronic.store.repository.UserRepository;
import com.lcwd.electronic.store.service.UserService;
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
public class UserServiceTest {

    //test me kahi bhi data base se interact nii ho rahi hai,yaha insertion nii ho rha hai
    //create me data insert hota hai

    @MockBean
    private UserRepository userRepository;

//    @MockBean
//    private RoleRepository roleRepository;

    //  @InjectMocks
    @Autowired
    private UserService userService;
    User user;
    // Role role;
    //String roleId;
    @Autowired
    private ModelMapper mapper;

    @BeforeEach
    public void init() {
        // role=Role.builder().roleId("abc").roleName("NORMAL").build()  ;

        user= User.builder()
                .name("Krishna")
                .email("krishnakumar3454878@gmail.com")
                .about("This is testing for CreateUser Method")
                .gender("Male")
                .imageName("abc.png")
                .password("lcwd")
                // roles(Set.of(role))
                .build();
        //roleId="abc";
    }
    //create user Test
   @Test
   public void createUserTest(){

       Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
     //  Mockito.when(roleRepository.findById(Mockito.anyString()).thenReturn(Optional.of(role));
       UserDto user1 = userService.createdUser(mapper.map(user, UserDto.class));
       System.out.println(user1.getName());
      Assertions.assertNotNull(user1);
       Assertions.assertEquals("Krishna",user1.getName());

   }
   @Test
public void updateUserTest(){
        String userId="dhekljdjl";
        UserDto userDto=UserDto.builder()
                .name("Krishna Kumar")
                .about("This is testing for UpdateUser Method")
                .gender("Male")
                .imageName("xyz.png")
                .build();
        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
       UserDto updateUser = userService.updateUser(userDto, userId);
     //  UserDto updatedUser = mapper.map(user,UserDto.class);

       System.out.println(updateUser.getName());
       System.out.println(updateUser.getImageName());
       Assertions.assertNotNull(userDto);
       Assertions.assertEquals(userDto.getName(),updateUser.getName(),"Name is not validated");
//multiple assertion valid.......

}
@Test
public void deleteUserTest(){
   String userid="userIdabc" ;
   Mockito.when(userRepository.findById("userIdabc")).thenReturn(Optional.of(user));
   userService.deleteUser(userid);
   Mockito.verify(userRepository,Mockito.times(1)).delete(user);

}
}
