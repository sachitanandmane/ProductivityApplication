package com.productivity.controller;

import com.productivity.exceptions.CustomeException;
import com.productivity.model.Tasks;
import com.productivity.model.Users;
import com.productivity.request.TaskRequest;
import com.productivity.request.UserRequest;
import com.productivity.response.CustomeResponse;
import com.productivity.service.TaskService;
import com.productivity.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productivity")
public class ProdController {
        @Autowired
        private UserService userService;

        @Autowired
        private TaskService taskService;

        @Autowired
        private CustomeResponse<Users> customeResponse;

        @PostMapping("/createUser")
        public ResponseEntity<CustomeResponse<Users>> createUser(@RequestBody @Valid  UserRequest userRequest){
                Users userDetails =null;
                try{
                     userDetails = userService.createUser(userRequest);

               }catch (Exception e) {
                       System.out.println(e.getStackTrace());
                        return ResponseEntity.badRequest().body(new CustomeResponse<>(userDetails,"Exception : "+e.getMessage()));
               }
               if(userDetails==null)
                       return ResponseEntity.badRequest().body(new CustomeResponse<>(userDetails,"User Not Created."));

               return ResponseEntity.ok().body(new CustomeResponse<>(userDetails,"New User Created."));
        }

        @PostMapping("/submitTask")
        public ResponseEntity<CustomeResponse<Tasks>> submitTask(@RequestBody @Valid TaskRequest taskRequest){
                Tasks  tasks =null;
                try{
                         tasks = taskService.createTask(taskRequest);
                }catch(CustomeException e){
                   System.out.println(e.getMessage());
                   return ResponseEntity.badRequest().body(new CustomeResponse<>(tasks,"Exception : "+e.getMessage() +"while submitting the task."));
                }catch(Exception e){
                        System.out.println(e.getStackTrace());
                }
                if(tasks==null)
                  return ResponseEntity.badRequest().body(new CustomeResponse<>(tasks,"Task was not submitted."));

                return ResponseEntity.ok().body(new CustomeResponse<>(tasks,"Task submitted Successfully."));
        }

        @GetMapping("/getAllUser")
        public ResponseEntity<CustomeResponse<List<Users>>> getUsers(){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Users users = (Users) authentication.getPrincipal();
            if(users.getUserAuthorities().contains("ADMIN")) {
                List<Users> list = userService.getUser();
                return ResponseEntity.ok().body(new CustomeResponse<>(list, "returned list of users."));
            }
            return ResponseEntity.ok().body(new CustomeResponse<>(null, "Does not have ADMIN access."));
        }

        @GetMapping("/getAllTasks")
        public ResponseEntity<CustomeResponse<List<Tasks>>> getTasks(){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Users users = (Users) authentication.getPrincipal();
            if(users.getUserAuthorities().contains("ADMIN")) {
                List<Tasks> list =  taskService.getTasks();
                return ResponseEntity.ok().body(new CustomeResponse<>(list,"returned list of "+list.size()+" task"));
            }
            return ResponseEntity.ok().body(new CustomeResponse<>(null, "Does not have ADMIN access to get ALL tasks."));
        }

        @GetMapping("/getUserDetails")
        public ResponseEntity<CustomeResponse<Users>> getUserByName(){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Users users = (Users) authentication.getPrincipal();
            if(users == null)
                return ResponseEntity.badRequest().body(new CustomeResponse<>(null,"User does not exists"));

            Users user = userService.getUserByName(users.getUsername());
            return ResponseEntity.ok().body(new CustomeResponse<>(user,"User details for "+users.getUsername() +" returned."));
        }

        @GetMapping("getUserTask")
    public ResponseEntity<CustomeResponse<List<Tasks>>> getUserTask(){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Users users = (Users) authentication.getPrincipal();
            List<Tasks> listOfTasks =  taskService.getUserTask(users.getUserId());
            return ResponseEntity.ok().body(new CustomeResponse<>(listOfTasks,"returned list of task for "+ users.getUsername() + "."));
        }

        @DeleteMapping("/deleteUser")
    public ResponseEntity<CustomeResponse<Users>> deleteUser(@RequestParam String name){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Users users = (Users) authentication.getPrincipal();
            Users user=null;

            if(users.getUserAuthorities().contains("ADMIN")) {
               user = userService.getUserByName(name);
               if(user!=null) {
                   Integer id = user.getUserId();
                   userService.deleteUser(id);
                   customeResponse.setData(user);
                   customeResponse.setMsg("user deleted successfully");
               }else{
                   customeResponse.setData(user);
                   customeResponse.setMsg("user does not exits in the system.");
               }
            }
            else{
               customeResponse.setData(user);
               customeResponse.setMsg("User not deleted, either user or authority is not valid");
            }
            return ResponseEntity.ok().body(customeResponse);
        }
}
