package kr.ac.jj.algo.controller.api.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
  @RequestMapping(value = "/{path:[^\\.]*}")
  public String redirect() {
    return "forward:/";
  }
}
