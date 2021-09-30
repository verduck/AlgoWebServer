package com.algo.algoweb.config;

import com.algo.algoweb.security.JwtAuthenticationEntryPoint;
import com.algo.algoweb.security.JwtRequestFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

  @Autowired
  private JwtRequestFilter jwtRequestFilter;

  @Override
  public void configure(WebSecurity webSecurity) {
    webSecurity.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.httpBasic().disable()
      .csrf().disable()
      .authorizeRequests()
      .antMatchers("/api/v1/auth/**").permitAll()
      .antMatchers("/api/**").authenticated()
      .anyRequest().permitAll().and()
      .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
  }
}
