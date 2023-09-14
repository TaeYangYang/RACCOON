package com.mycom.raccoon.controller.common;

import com.mycom.raccoon.common.Util;
import com.mycom.raccoon.entity.Code;
import com.mycom.raccoon.service.code.CodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "common")
@RequiredArgsConstructor
public class CommonController {

  private final Environment environment;

  private final CodeService codeService;

  /**
   * 공통응답페이지
   * @return String responseView
   * @throws Exception
   */
  @GetMapping("response")
  public String response(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
    model.addAttribute("resultVal", Util.nvl(request.getParameter("resultVal")));
    model.addAttribute("resultMsg", Util.nvl(request.getParameter("resultMsg")));
    return "/common/response";
  }

  @GetMapping("lowlevelcode/{code}")
  @ResponseBody
  public List<Code> lowlevelcode(@PathVariable String code, HttpServletRequest request, HttpServletResponse response) throws Exception{
    List<Code> codelist = new ArrayList<>();
    codelist = codeService.getLowLevelCode(code);
    return codelist;
  }

}
