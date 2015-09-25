package com.onlinecourses.site.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = ValidEmailImpl.class)
public @interface ValidEmail {

    String message() default "{ValidEmail.user.email}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}