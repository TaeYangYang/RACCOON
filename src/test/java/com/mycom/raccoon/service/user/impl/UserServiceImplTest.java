package com.mycom.raccoon.service.user.impl;

import com.mycom.raccoon.entity.User;
import com.mycom.raccoon.repository.GenericRepository;
import com.mycom.raccoon.repository.UserRepository;
import com.mycom.raccoon.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class) // Junit5 기능을 사용하고, Test에서 가짜 객체를 사용
class UserServiceImplTest {

  UserService userService;

  @MockBean // 가짜 객체를 만드는 역할을. 가짜 객체이므로 응답을 정의해줘야함. Test의 협력자 역할.
  UserRepository userRepository;

  @MockBean
  GenericRepository genericRepository;

  @MockBean
  PasswordEncoder passwordEncoder;

  // Test를 실행하기 전마다 MemberService에 가짜 객체를 주입시켜준다.
  @BeforeEach
  void setUp(){
    userService = new UserServiceImpl(userRepository, passwordEncoder);
  }

  @Test
  @DisplayName("유저 생성 성공")
  void createUser(){

    //given
    User user = new User();
    user.setUserid("testUser1");
    user.setNickname("testUser1Nickname");
    user.setPassword("");

    //Mockito.when(가짜 객체의 로직 실행). thenReturn(실행되면 이것을 반환한다.)
    Mockito.when(userRepository.save(user)).thenReturn(user); // 가짜 객체 응답 정의

    //when
    userService.insertUser(user);

    //then
    assertThat(userRepository.findByUserid(user.getUserid())).isEqualTo(user);
  }

  /*@BeforeAll
  static void beforeAll() {
    System.out.println("## BeforeAll Annotation 호출 ##");
    System.out.println();
  }

  @AfterAll
  static void afterAll() {
    System.out.println("## afterAll Annotation 호출 ##");
    System.out.println();
  }

  @BeforeEach
  void beforeEach() {
    System.out.println("## beforeEach Annotation 호출 ##");
    System.out.println();
  }

  @AfterEach
  void afterEach() {
    System.out.println("## afterEach Annotation 호출 ##");
    System.out.println();
  }

  @Test
  void test1() {
    System.out.println("## test1 시작 ##");
    System.out.println();
  }

  @Test
  @DisplayName("Test Case 2!!!")
  void test2() {
    System.out.println("## test2 시작 ##");
    System.out.println();
  }

  @Test
  @Disabled
    // Disabled Annotation : 테스트를 실행하지 않게 설정하는 어노테이션
  void test3() {
    System.out.println("## test3 시작 ##");
    System.out.println();
  }*/
}