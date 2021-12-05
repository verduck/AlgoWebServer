package com.algo.algoweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class PostDTO {
    private Integer id;
    private String title;
    private String content;
    private UserDTO author;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
