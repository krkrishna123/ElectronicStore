package com.lcwd.electronic.store.service.impl;

import com.lcwd.electronic.store.dto.UserDto;
import com.lcwd.electronic.store.entity.User;
import com.lcwd.electronic.store.repository.UserRepository;
import com.lcwd.electronic.store.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    /**
     * Author's name:Krishna
     *Api notes:UserServiceImpl in this all method of UserService are implemented here
     */

    private static Logger logger=  LoggerFactory.getLogger( UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
   private ModelMapper mapper;


    @Override
    public UserDto createdUser(UserDto userDto) {
        logger.info(" Before createdUser:="+ userDto);

        //generate unique id in string format
        String userId= UUID.randomUUID().toString();//ye randomly userId ko generate karega,har bar
        userDto.setUserId(userId);//jitni bar usercreate hoga utni bar userid apne aap dega

      //dto->entity conversion here

      User user=dtoToEntity(userDto) ;
      User savedUser=userRepository.save(user);

      //entity->dto conversion done here
        UserDto newDto=entityToDto(savedUser);


    logger.info(" after createdUser:="+ userDto);

        return newDto;
    }

    private UserDto entityToDto(User savedUser) {//this is only for coversion purposes
      //  logger.info(" before conversion  Entity to Dto"+ savedUser);
//        UserDto userDto= UserDto.builder()
//                .userId(savedUser.getUserId())
//                .name(savedUser.getName())
//                .email(savedUser.getEmail())
//                .password(savedUser.getPassword())
//                .about(savedUser.getPassword())
//                .gender(savedUser.getGender())
//                .imageName(savedUser.getImageName())
//                .build();
       // logger.info(" after conversion  Entity to Dto"+ savedUser);
        return mapper.map(savedUser,UserDto.class);
    }

    private User dtoToEntity(UserDto userDto) {//this is only for coversion purposes

//        logger.info(" before conversion  Dto to entity"+ userDto);
//       User user= User.builder()
//                .userId(userDto.getUserId())
//                .name(userDto.getName())
//                .email(userDto.getEmail())
//                .password(userDto.getPassword())
//                .about(userDto.getAbout())
//                .imageName(userDto.getImageName())
//                .gender(userDto.getGender()).build();
//
//        logger.info(" after conversion  Dto to entity"+ userDto);
        return mapper.map(userDto,User.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
       User user =userRepository.findById(userId).orElseThrow(()->new RuntimeException("User nort found with given id !!"));
        user.setName(userDto.getName());

        //email update

         user.setAbout(userDto.getAbout());
         user.setGender(userDto.getGender());
         user.setPassword(userDto.getPassword());
         user.setImageName(userDto.getImageName());

         //save data
        User updatedUser=userRepository.save(user);
        UserDto updatedDto=entityToDto(updatedUser);

       return updatedDto;
    }

    @Override
    public void deleteUser(String userId) {
        User user =userRepository.findById(userId).orElseThrow(()->new RuntimeException("User nort found with given id !!"));
        //delete user
        userRepository.delete(user);

    }

    @Override
    public List<UserDto> getAllUser() {
       List<User> users=userRepository.findAll();
       List<UserDto>dtoList=users.stream().map(user->entityToDto(user)).collect(Collectors.toList());

        return dtoList;
    }

    @Override
    public UserDto getUserById(String userId) {
        User user=userRepository.findById(userId).orElseThrow(()->new RuntimeException("user not found with given id !!!"));
        return entityToDto(user);
    }

   //Jpa Repository me sare method available hai like findbyid,getbyid,deletall ...etc but
    //kuch nii hai jo hm apne trf se lage hai like:search,getUserByEmail so custom Query dene honge
    //iske liye hame custom method likhna hoga jpa repository me
   @Override
   public UserDto getUserByEmail(String email) {
        User user=userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("User not found with given email id and password"));
        return entityToDto(user);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        List<User>users=userRepository.findByNameContaining(keyword);
        List<UserDto>dtoList=users.stream().map(user->entityToDto(user)).collect(Collectors.toList());

        return dtoList;
    }

}
