package com.lcwd.electronic.store.repository;

import com.lcwd.electronic.store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByEmail(String email);           //findby ki query hai to hme "var" ki quer lgani hogi
    Optional<User> findByEmailAndPassword(String email,String password);
    List<User> findByNameContaining(String keywords);


}
