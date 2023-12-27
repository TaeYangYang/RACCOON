package com.mycom.raccoon.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"com.mycom.raccoon.repository"},    // Repository 경로
        entityManagerFactoryRef = "baseEntityManager",
        transactionManagerRef = "baseTransactionManager"
)
public class DataSourceConfig {

  @Primary
  @Bean(name = "raccoon_dataSource")
  @ConfigurationProperties("spring.datasource")
  public DataSource raccoonDataSource() {
    return DataSourceBuilder.create().type(HikariDataSource.class).build();
  }

  @Primary
  @Bean(name = "baseEntityManager")
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(
          EntityManagerFactoryBuilder builder,
          @Qualifier("raccoon_dataSource") DataSource dataSource) {
    Map<String, String> map = new HashMap<>();
    map.put("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");
    map.put("hibernate.dialect", "org.hibernate.dialect.MariaDBDialect");
    return builder.dataSource(dataSource)
            .packages("com.mycom.raccoon.entity") // TODO Model 패키지 지정
            .properties(map)
            .build();
  }

  @Primary
  @Bean(name = "baseTransactionManager")
  public PlatformTransactionManager transactionManager(
          @Qualifier("baseEntityManager") EntityManagerFactory entityManagerFactory) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory);
    return transactionManager;
  }


}