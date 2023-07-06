package com.lcwd.electronic.store.entity;

import lombok.*;

import javax.persistence.*;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="users")
public class User {

    /**
     * author's name:krishna
     *ApiNotes: User Entity
     */


    @Id
   //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;

    @Column(name = "user_name")
    private String name;

    @Column(name="user_email")
    private String email;

    @Column(name="user_password")
    private String password;

    private String gender;

    @Column(length=1000)
    private String about;

    @Column(name="user_image_name")
    private String imageName;

}
