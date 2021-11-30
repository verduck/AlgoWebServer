package com.algo.algoweb.domain;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String username;
  private String password;
  private String name;
  private LocalDate birth;
  private String token;

  @Enumerated(EnumType.STRING)
  private Authority authority;
  @OneToOne(mappedBy = "user")
  private Application application;
  @OneToMany(mappedBy = "user")
  private List<Post> posts;
  @OneToMany(mappedBy = "user")
  private List<Likes> likes;

  public User(String username, String password, String name) {
    this(username, password, name, null, Authority.ROLE_APPLICANT);
  }

  public User(String username, String password, String name, LocalDate birth, Authority authority) {
    this.username = username;
    this.password = password;
    this.name = name;
    this.birth = birth;
    this.authority = authority;
  }
  
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
