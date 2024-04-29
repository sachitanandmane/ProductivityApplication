package com.productivity.service;

import com.productivity.exceptions.CustomeException;
import com.productivity.model.Tasks;
import com.productivity.model.Users;
import com.productivity.repository.TasksRepository;
import com.productivity.repository.UserRepository;
import com.productivity.request.TaskRequest;
import com.productivity.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TasksRepository tasksRepository;
    @Autowired
    private UserService userService;
    public Tasks createTask(TaskRequest taskRequest) throws  CustomeException{
        Tasks  tasks =null;
        Tasks savedTask=null;
        //check of given user name present in the system. then create user from that and pass to task.

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Users users= (Users) authentication.getPrincipal();
            String name =users.getUsername();

            Users userDetails = userService.getUserByName(name);
            if(userDetails == null)
                throw new CustomeException("User does not exists in the system.");

            tasks = Tasks.builder()
                    .taskName(taskRequest.getTaskName())
                    .user(userDetails)
                    .taskCategory(taskRequest.getTaskCategory())
                    .startDateTime(taskRequest.getStartDateTime())
                    .endDateTime(taskRequest.getEndDateTime())
                    .build();
            savedTask = tasksRepository.save(tasks);

        if(savedTask==null)
            return tasks;

        return savedTask;
    }

    public List<Tasks> getTasks() {
        return tasksRepository.findAll();
    }

    public List<Tasks> getUserTask(Integer id) {
        return tasksRepository.findAllByUser_id_fk(id);
    }

    @Service
    public static class UserService implements UserDetailsService {

        @Autowired
        private UserRepository userRepository;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
           return userRepository.findByUserName(username);
        }

        public Users createUser(UserRequest userRequest){
            Users newUser = null;
            Users DBUser =null;
            try{
                newUser =  Users.builder()
                        .userName(userRequest.getUserName())
                        .userEmail(userRequest.getUserEmail())
                        .userAge(userRequest.getUserAge())
                        .build();

                if(newUser !=null){
                    newUser.setUserAuthorities("USER");
                }
                DBUser =  userRepository.save(newUser);


            }catch (Exception e) {
                System.out.println(e.getStackTrace());
            }
            if(DBUser == null)
            return newUser;

            return DBUser;
        }

        public List<Users> getUser() {
           return userRepository.findAll();
        }

        public Users getUserByName(String name) {
           return userRepository.findByUserName(name);
        }
    }
}
