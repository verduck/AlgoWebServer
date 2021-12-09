package com.algo.algoweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class LikesDTO {
    private Integer id;
    private PostDTO post;
    private UserDTO user;
}
