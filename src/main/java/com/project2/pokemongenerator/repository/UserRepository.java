package com.project2.pokemongenerator.repository;

import com.project2.pokemongenerator.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // to register
    boolean existsByEmailAddress(String userEmailAddress);

    // to change password
//    boolean existsByUserName(String username);

    // to login
    User findUserByEmailAddress(String userEmailAddress);

    // get username
    User findUserByUserName(String username);
}
