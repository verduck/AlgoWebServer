package com.algo.algoweb.dto;

import com.algo.algoweb.domain.Authority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class UserDTO {
  private String username;
  private String name;
  private Date birth;
  private Authority authority;
}
