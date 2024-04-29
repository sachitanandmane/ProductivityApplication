package com.productivity.service;

import com.productivity.exceptions.CustomeException;
import com.productivity.model.Users;
import com.productivity.repository.UserRepository;
import com.productivity.request.UserRequest;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private PasswordEncoder encoder;
    public Users createUser(UserRequest userRequest) throws Exception {
        Users newUser=null;
        Users dbUser=null;

            dbUser = userRepository.findByUserName(userRequest.getUserName());
            if(dbUser!=null)
                throw new CustomeException("user already exists in the system.");

            newUser = Users.builder()
                    .userName(userRequest.getUserName())
                 //   .userPassword(encoder.encode(userRequest.getUserPassword()))
                    .userPassword(userRequest.getUserPassword())
                    .userAge(userRequest.getUserAge())
                    .userEmail(userRequest.getUserEmail())
                    .build();
            if(newUser!=null){
                newUser.setUserAuthorities("USER");
                userRepository.save(newUser);
            }else
                throw  new Exception("Exception occurred, can not create new user..");

        return newUser;
    }

    public List<Users> getUser() {
        List<Users> list =  userRepository.findAll();
        return list;
    }

    public Users getUserByName(String name) {
        return userRepository.findByUserName(name);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       return userRepository.findByUserName(username);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}
