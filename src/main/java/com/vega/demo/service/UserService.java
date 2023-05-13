package com.vega.demo.service;
import com.vega.demo.domain.UserSpringIt;
import com.vega.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {
    private final Logger  logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserSpringIt register(UserSpringIt user) {
        return user;
    }

    public UserSpringIt save(UserSpringIt user) {
        userRepository.save(user);
        return user;
    }

    @Transactional
    public void saveUsers(UserSpringIt... users) {
        for(UserSpringIt user : users) {
            logger.info("Adding user -> " + user.getEmail());
            userRepository.save(user);
        }
    }
}
