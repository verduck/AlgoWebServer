package com.algo.algoweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
  @RequestMapping(value = "/{path:[^\\.]*}")
  public String redirect() {
    return "forward:/";
  }
}
