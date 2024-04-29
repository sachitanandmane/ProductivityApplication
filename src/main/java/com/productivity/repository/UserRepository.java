package com.productivity.repository;

import com.productivity.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<Users,Integer> {
    Users findByUserName(String userName);

    @Modifying
    @Transactional
    void deleteById(Integer Id);

    // Method to find a user by userName
  //  Optional<UserDetails> findByUserName(String userName);
   // findByUserName(String userName)
    // ;
//    @Query(value = "select * from user where user_name=?1", nativeQuery = true)
//    UserDetails findByUserName(String userName);

}
