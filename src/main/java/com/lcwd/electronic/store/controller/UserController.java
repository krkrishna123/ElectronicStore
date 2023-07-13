package com.lcwd.electronic.store.controller;

import com.lcwd.electronic.store.dto.ApiResponseMessage;
import com.lcwd.electronic.store.dto.ImageResponse;
import com.lcwd.electronic.store.dto.PageableResponse;
import com.lcwd.electronic.store.dto.UserDto;
import com.lcwd.electronic.store.service.FileService;
import com.lcwd.electronic.store.service.UserService;
import com.lcwd.electronic.store.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    /**
     * Author's name:krishna
     * api notes:Controller layer for User,all methods related controller
     * methods:create,update,delete,getall,get single,get by email,search user
     */
    private static Logger logger=  LoggerFactory.getLogger( UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;
    
    @Value("${user.profile.image.path}")
    private String imageUploadPath;
    
    /**
     * api notes:create User
     * @param :userDto
     * @return:Userdto1
     */
    @PostMapping      //andar me @Valid annotation validation k liye lagae hai,kyoki yahi datacreate hoga
    public ResponseEntity<UserDto>createUser(@Valid @RequestBody UserDto userDto){
        logger.info(" Before createdUser in controller :="+ userDto);

        UserDto userDto1=userService.createdUser(userDto);

        logger.info(" after createdUser in controller:="+ userDto);

        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
    }
    /**
     * Api note:UpdateUser
     * @param :userId
     * @param :userDto
     * @return: updatedUserDto
     */
    @PutMapping("/{userId}")
public ResponseEntity<UserDto>updateUser(
        @PathVariable("userId") String userId,
        @Valid @RequestBody UserDto userDto //request body k aage lga  de validation
) {
        logger.info(" Before updateddUser in controller :="+ userDto);

    UserDto updatedUserDto = userService.updateUser(userDto, userId);

        logger.info(" after updatedUser in controller :="+ userDto);
    return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
}

    /**
     * api notes:deleteUser
     * @param :userId
     * @return :void
     */

    @DeleteMapping("/{userId}")
public ResponseEntity<ApiResponseMessage>deleteUser(@PathVariable String  userId){
        logger.info(" before deleteUser in controller :="+ userId);
  userService.deleteUser(userId);
  ApiResponseMessage message=ApiResponseMessage.builder()
          .message("User is deleted Succesfully !!")
          .success(true)
          .status(HttpStatus.OK)
          .build();
        logger.info(" After deleteUser in controller controller :="+ userId);
  return new ResponseEntity<>(message,HttpStatus.OK);
}

    //get all

    /**
     * api notes:getAllUsers
     * @return
     */
    @GetMapping
 public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
         @RequestParam(value="pageNumber",defaultValue = "0",required=false) int pageNumber,
         @RequestParam(value="pageSize",defaultValue="10",required = false) int pageSize,
         @RequestParam(value="sortBy",defaultValue = "name",required=false) String  sortBy,
         @RequestParam(value="sortDir",defaultValue="10",required = false) String sortDir
    ) {
        logger.info(" After getAllUser in controller  ");
        return new ResponseEntity<>(userService.getAllUser(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
 }

    //get single

    /**
     * notes:get single user
     * @param userId
     * @return
     */

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUsers(@PathVariable String userId) {
        logger.info(" After getUser in controller  "+ userId);
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }
    //get by email

    /**
     * api note:custom method for get userbyEmail
     * @param email
     * @return
     */
        @GetMapping("/email/{email}")
        public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
            logger.info(" After getUser by Email in controller  "+ email);
            return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
        }

    /**
    * api notes:custom methods searchUser
   * @param keywords
   * @return
    */
    //search user
            @GetMapping("/search/{keywords}")
            public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keywords) {

                logger.info(" After searchUser by keywords in controller  " + keywords);
                return new ResponseEntity<>(userService.searchUser(keywords), HttpStatus.OK);
            }
            //upload image user
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse>uploadUserImage(@RequestParam("userImage") MultipartFile image,@PathVariable String userId) throws IOException {
     String imageName=fileService.uploadFile(image,imageUploadPath);
     UserDto user=userService.getUserById(userId);
     user.setImageName(imageName);
        userService.updateUser(user, userId);

        ImageResponse imageResponse= ImageResponse.builder().imageName(imageName).success(true).status(HttpStatus.CREATED).build();
     return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);

    //service user image


            }}
