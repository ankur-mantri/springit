package com.vega.demo.service;

import com.vega.demo.domain.UserSpringIt;
import com.vega.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final Logger  logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final MailService mailService;

    public UserService(UserRepository userRepository, RoleService roleService, MailService mailService) {

        this.userRepository = userRepository;
        this.roleService= roleService;
        this.mailService= mailService;
    }

    public UserSpringIt register(UserSpringIt user) {
        BCryptPasswordEncoder encoder = BeanUtil.getBean(BCryptPasswordEncoder.class);
        String secret = "{bcrypt}" + encoder.encode(user.getPassword());
        user.setPassword(secret);
        //confirm password - to be implemented
        user.setConfirmPassword(secret);

        //add role
        user.addRole(roleService.findByName("ROLE_USER"));

        // set activation code
        user.setActivationCode(UUID.randomUUID().toString());

        // disable user
        user.setEnabled(false);

        // save user
        save(user);
        // send activation email
        sendActivationEmail(user);

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
    public void sendActivationEmail(UserSpringIt user) {
        mailService.sendActivationEmail(user);
    }
    public void sendWelcomeEmail(UserSpringIt user) {
        mailService.sendWelcomeEmail(user);
    }
    public Optional<UserSpringIt> findByEmailAndActivationCode(String email, String activationCode) {
        return userRepository.findByEmailAndActivationCode(email,activationCode);
    }
}
