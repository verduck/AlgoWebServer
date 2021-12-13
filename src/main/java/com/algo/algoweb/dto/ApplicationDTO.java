package com.algo.algoweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ApplicationDTO {
    private Integer id;
    private UserDTO user;
    private String introduction;
}
