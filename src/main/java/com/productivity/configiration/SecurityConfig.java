package com.productivity.configiration;

import com.productivity.service.TaskService;
import com.productivity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private UserService userService;

//  @Bean
//    public UserDetailsManager userDetailsService(){
//       UserDetails userDetails = User.builder().
//                username("user")
//                .password("user")
//                .roles("user")
//                .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("admin")
//                .roles("ADMIN")
//                .build();
//
//        return  new InMemoryUserDetailsManager(userDetails,admin);
//    }

//    @Bean
//    public UserDetailsManager jdbcUserDetailsManager(DataSource dataSource){
//        JdbcUserDetailsManager manager = new  JdbcUserDetailsManager(dataSource);
//        return  manager;
//    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider =  new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(getEncoder());
        return  provider;
    }

    @Bean
    public PasswordEncoder getEncoder(){
        //return NoOpPasswordEncoder.getInstance();
        return NoOpPasswordEncoder.getInstance();
    }


    @Bean
    public SecurityFilterChain getAuth(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(authorize -> authorize
                 .requestMatchers("/createUser/**").permitAll()
                .requestMatchers("/getAllUser/**","/getAllTasks/**","/deleteUser/**").hasAuthority("ADMIN")
                .requestMatchers("/getUserTask/**","/getUserDetails/**").hasAuthority("USER")
                .requestMatchers("/submitTask/**").hasAnyAuthority("USER","ADMIN")
                .anyRequest().permitAll())
                .httpBasic(Customizer.withDefaults())
                .csrf(csrf->csrf.disable());
           return http.build();
    }
}
