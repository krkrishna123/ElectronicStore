package com.lcwd.electronic.store.service;

import com.lcwd.electronic.store.dto.PageableResponse;
import com.lcwd.electronic.store.dto.UserDto;
import com.lcwd.electronic.store.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    /**
     * author's name:krishna
     * api notes:UserService interface ,In this all methods created.
     */

    //create
    UserDto createdUser(UserDto userDto);  //here we cant transfer data directly,so used :Data transfer Object(DTO)

    //update
    UserDto updateUser(UserDto userDto,String userId);

    //delete
    void deleteUser(String userId);

    //get all user
    PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir);


    //get single user by id
    UserDto getUserById(String userId);

    //get single user by email
    UserDto getUserByEmail(String email);

    //search user
    List<UserDto>searchUser(String keyword);

    Optional<User> findUserByEmailOptional(String email);

    //other specific features
}
