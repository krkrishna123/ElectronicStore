package com.lcwd.electronic.store.dto;


import com.lcwd.electronic.store.validate.ImageNameValid;
import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder //new krk object bnane ki awasqta nii padegi
public class UserDto {
     //userDto ko validate kr rahe hai
    private String userId;

    @Size(min=3,max=15,message="Invalid Name !!") //name min 3 max 15 hone chahye other wise message dega
    private String name;
    //pattern Annotation used
   // @Email(message="Invalid user Email !!") //,below is perfect format of email i.e regex
    @Pattern(regexp = "^[a-z0-9][-a-z0-9._]+@([-a-z0-9]+\\.)+[a-z]{2,5}$",message="Invalid user Email !!")
    @NotBlank(message="Email is required")
    private String email;

    @NotBlank(message="Password is Required!!")
    private String password;

    @Size(min=4,max=6,message="Invalid gender !!")
    private String gender;

    @NotBlank(message="Write something about yourself !!")
    private String about;


    //custom validator
       //ye custom validator k liye ImageNameValid Interface,ImageNameValidator
    @ImageNameValid            // lagana padega tab jakr yaha custom validator laga paenge,jo ki lgae hai
    private String imageName;

}
