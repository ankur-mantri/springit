package com.vega.demo.config;

import com.vega.demo.domain.UserSpringIt;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        //I do not like the following "if" clause which had to be introduced to insert data in test/dev environment
        if(SecurityContextHolder.getContext().getAuthentication() == null) {
            return Optional.of("master@gmail.com");
        }
        return Optional.of(((UserSpringIt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail());
    }
}
