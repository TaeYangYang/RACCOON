package com.mycom.raccoon.controller.salesBoard;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "salesBoard")
@RequiredArgsConstructor
@PropertySource("classpath:/properties/key.properties")
public class SalesBoardController {

  @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
  public String list(){

    return "/salesBoard/list";
  }


}
