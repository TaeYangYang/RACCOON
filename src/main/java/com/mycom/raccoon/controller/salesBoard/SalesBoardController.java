package com.mycom.raccoon.controller.salesBoard;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "user")
@RequiredArgsConstructor
@PropertySource("classpath:/properties/key.properties")
public class SalesBoardController {


}
