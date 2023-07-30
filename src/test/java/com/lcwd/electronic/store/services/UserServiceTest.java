package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dto.PageableResponse;
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
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

//Services  ko test krne ka Best Tarika yahi used kare//

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
@Test
public void getAllUsersTest(){
   User user1= User.builder()
            .name("Karan")
            .email("karan34@gmail.com")
            .about("This is testing for GetAllUser Method")
            .gender("Male")
            .imageName("kks.png")
            .password("lcwd")
            // roles(Set.of(role))
            .build();
   User user2= User.builder()
            .name("dilip")
            .email("dk58@gmail.com")
            .about("This is testing for getAllUser Method")
            .gender("Male")
            .imageName("dk.png")
            .password("lcwd")
            // roles(Set.of(role))
            .build();

        List<User> userList= Arrays.asList(user,user1,user2);

        Page<User> page=new PageImpl<>(userList);
        Mockito.when(userRepository.findAll((Pageable)Mockito.any())).thenReturn(page);

//       Sort sort=Sort.by("name").ascending();
//        Pageable pageable= PageRequest.of( 1, 2, sort);
//
        PageableResponse<UserDto> allUser=userService.getAllUser(1,2,"name","asc");
        Assertions.assertEquals(3,allUser.getContent().size());

}
@Test
public void getUserByIdTest(){
        String userId="userIdTest";
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        //actual call of service method
    UserDto userDto = userService.getUserById(userId);
    Assertions.assertNotNull(userDto);
    Assertions.assertEquals(user.getName(),userDto.getName(),"Name not matched !!!");

}
@Test
public void getUserByEmailTest(){
      String emailId="kr123@gmail.com";
      Mockito.when(userRepository.findByEmail(emailId)).thenReturn(Optional.of(user));
      UserDto userDto = userService.getUserByEmail(emailId);
      Assertions.assertNotNull(userDto);
      Assertions.assertEquals(user.getEmail(),userDto.getEmail(),"Email not matched");

}
@Test
public void searchUserTest(){
    User user1= User.builder()
            .name("Prafulla")
            .email("pk67@gmail.com")
            .about("This is testing for searchUserTest Method")
            .gender("Male")
            .imageName("pkf.png")
            .password("lcwd")
            // roles(Set.of(role))
            .build();
    User user2= User.builder()
            .name("sindhe")
            .email("Sinde34@gmail.com")
            .about("This is testing for searchUser Method")
            .gender("Male")
            .imageName("Suiu.png")
            .password("lcwd")
            // roles(Set.of(role))
            .build();
    User user3= User.builder()
            .name("Pankaj")
            .email("pkj34@gmail.com")
            .about("This is testing for CreateUser Method")
            .gender("Male")
            .imageName("kks.png")
            .password("lcwd")
            // roles(Set.of(role))
            .build();
    String keywords="kumar";
    Mockito.when(userRepository.findByNameContaining(keywords)).thenReturn(Arrays.asList(user,user1,user2,user3));
    List<UserDto>userDtos=userService.searchUser(keywords);
    Assertions.assertEquals(4,userDtos.size(),"size not matched !!");
}
@Test
public void findUserByEmailOptionalTest(){
      String email="krkri234@gmail.com";
    Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
   Optional<User>userByEmailOptional=userService.findUserByEmailOptional(email);
      Assertions.assertTrue(userByEmailOptional.isPresent());
}
}
