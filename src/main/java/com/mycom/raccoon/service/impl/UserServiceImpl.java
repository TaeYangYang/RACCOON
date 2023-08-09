package com.mycom.raccoon.service.impl;

import com.mycom.raccoon.entity.Userinfo;
import com.mycom.raccoon.repository.UserRepository;
import com.mycom.raccoon.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  @Transactional
  public void signUp(Userinfo userinfo){
    userinfo.setPassword(passwordEncoder.encode(userinfo.getPassword()));
    userRepository.save(userinfo);
  }
}
