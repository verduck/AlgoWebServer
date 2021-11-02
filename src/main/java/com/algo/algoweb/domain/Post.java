package com.algo.algoweb.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String title;
  
  private String content;

  @ManyToOne
  @JoinColumn(name = "author")
  private User user;

  private Date createdAt;
  private Date updatedAt;

  @OneToMany(mappedBy = "post")
  private List<Like> likes;
}
