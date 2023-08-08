package com.mycom.raccoon.config;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class PropertiesConfig {

  @Bean(name="keyPropertiesFactoryBean")
  public PropertiesFactoryBean propertiesFactoryBean() throws Exception{
    PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
    ClassPathResource classPathResource = new ClassPathResource("/properties/key.properties");

    propertiesFactoryBean.setLocation(classPathResource);

    return propertiesFactoryBean;
  }
}
