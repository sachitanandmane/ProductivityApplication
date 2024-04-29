package com.productivity;

import com.productivity.model.Users;
import com.productivity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.userdetails.User;

@SpringBootApplication
public class ProductivityApplication implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(ProductivityApplication.class,args);
        System.out.println( "Hello World!" );
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Inside command line runner.");
        Users adminUser = userRepository.findByUserName("admin");
        if(adminUser==null) {
            Users user = Users.builder()
                    .userName("admin")
                    .userPassword("admin")
                    .userEmail("admin@gmail.com")
                    .userAuthorities("USER,ADMIN")
                    .build();

            userRepository.save(user);
        }
    }
}
