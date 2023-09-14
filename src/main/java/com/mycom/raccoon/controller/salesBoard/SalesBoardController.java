package com.mycom.raccoon.controller.salesBoard;


import com.mycom.raccoon.entity.SalesBoard;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "salesBoard")
@RequiredArgsConstructor
@PropertySource("classpath:/properties/key.properties")
public class SalesBoardController {

  @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
  public String list(){

    return "/salesBoard/list";
  }

  @GetMapping("form")
  public String form(@ModelAttribute("salesBoardForm")SalesBoard to, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
    model.addAttribute("salesBoard", to);
    return "/salesBoard/form";
  }


}
