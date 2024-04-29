package com.productivity.request;


import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRequest {

    @NotBlank
    private String userName;

    @NotBlank(message = "password is not valid.")
    private String userPassword;

    @NotBlank
    @Email(message = "Email is not valid.")
    private String userEmail;

    @Positive
    @Min(value = 14, message = "Age should be above 13.")
    private Integer userAge;

}
