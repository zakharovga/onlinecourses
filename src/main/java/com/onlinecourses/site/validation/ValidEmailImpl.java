package com.onlinecourses.site.validation;

import org.apache.commons.validator.routines.EmailValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by zakharov_ga on 11.06.2015.
 */
public class ValidEmailImpl implements ConstraintValidator<ValidEmail, String> {

    public void initialize(ValidEmail constraintAnnotation) {}

    public boolean isValid(String email, ConstraintValidatorContext context) {

        if (!EmailValidator.getInstance(false).isValid(email)) {
            return false;
        }

        return true;
    }
}