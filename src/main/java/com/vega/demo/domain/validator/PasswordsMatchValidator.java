package com.vega.demo.domain.validator;

import com.vega.demo.domain.UserSpringIt;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, UserSpringIt> {

    @Override
    public void initialize(PasswordsMatch constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserSpringIt user, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println("Password MATcHER AT WORK");
        return user.getPassword().equals(user.getConfirmPassword());
    }
}
