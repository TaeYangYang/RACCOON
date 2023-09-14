package com.mycom.raccoon.controller.salesBoard;


import com.mycom.raccoon.entity.Code;
import com.mycom.raccoon.entity.SalesBoard;
import com.mycom.raccoon.service.code.CodeService;
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
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "salesBoard")
@RequiredArgsConstructor
@PropertySource("classpath:/properties/key.properties")
public class SalesBoardController {

  private final CodeService codeService;

  /**
   * 구매/판매 게시판 리스트
   * @return String view
   */
  @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
  public String list(){

    return "/salesBoard/list";
  }

  /**
   * 구매/판매 등록 접근
   * @param SalesBoard to
   * @param HttpServletRequest request
   * @param HttpServletResponse response
   * @param ModelMap model
   * @return String view
   * @throws Exception e
   */
  @GetMapping("form")
  public String form(@ModelAttribute("salesBoardForm")SalesBoard to, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
    model.addAttribute("salesBoard", to);

    List<Code> codelist = new ArrayList<>();
    model.addAttribute("codelist", codeService.getLowLevelCode("AA"));

    return "/salesBoard/form";
  }


}
