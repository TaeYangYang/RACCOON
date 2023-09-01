package com.mycom.raccoon.repository;

import com.mycom.raccoon.config.DataSourceConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 실제 DB환경에서 테스트
@ImportAutoConfiguration(DataSourceConfig.class) // hikari 사용때문에 설정클래스 적용
class UserRepositoryTest {

  @Autowired
  UserRepository userRepository;

  @Test
  @DisplayName("유저아이디 찾기")
  void findByUserid() {
    userRepository.findByUserid("user1");
  }

  @Test
  void findByNickname() {
  }

  @Test
  void findByCelnoOrderByUserid() {
  }

  @Test
  void findByUseridAndCelno() {
  }

  @Test
  void findByUseridAndSignupdiv() {
  }
}