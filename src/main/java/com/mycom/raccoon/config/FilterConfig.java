package com.mycom.raccoon.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycom.raccoon.common.HTMLCharacterEscape;
import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
public class FilterConfig extends WebMvcConfigurerAdapter {

  /**
   * lucy-xss-filter
   *
   * @return
   */
  @Bean
  public FilterRegistrationBean getFilterRegistrationBean() {
    FilterRegistrationBean registrationBean = new FilterRegistrationBean();
    registrationBean.setFilter(new XssEscapeServletFilter());
    registrationBean.setOrder(1);
    registrationBean.addUrlPatterns("/*");    //filter를 거칠 url patterns
    return registrationBean;
  }

  /**
   * xss 방어 처리 messageConverters
   */
  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    super.configureMessageConverters(converters);
    converters.add(htmlEscapingConveter());
  }

  private HttpMessageConverter<?> htmlEscapingConveter() {
    ObjectMapper objectMapper = new ObjectMapper();
    // 3. ObjectMapper에 특수 문자 처리 기능 적용
    objectMapper.getFactory().setCharacterEscapes(new HTMLCharacterEscape());

    // 4. MessageConverter에 ObjectMapper 설정
    MappingJackson2HttpMessageConverter htmlEscapingConverter = new MappingJackson2HttpMessageConverter();
    htmlEscapingConverter.setObjectMapper(objectMapper);

    return htmlEscapingConverter;
  }
}
