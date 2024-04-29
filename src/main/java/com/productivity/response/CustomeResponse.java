package com.productivity.response;

import lombok.*;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Setter
@Getter
@Builder
@Component
@NoArgsConstructor
public class CustomeResponse<T>  {
    private T data;
    private String msg;
}
