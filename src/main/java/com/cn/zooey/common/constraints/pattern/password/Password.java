package com.cn.zooey.common.constraints.pattern.password;


import org.apache.commons.lang3.StringUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @Author Fengzl
 * @Date 2023/9/22 11:23
 * @Desc 自定义密码校验
 **/
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Password.List.class)
@Documented
@Constraint(validatedBy = {Password.Validator.class})
public @interface Password {

    String message() default "The password format error";

    String pattern() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        Password[] value();
    }


    class Validator implements ConstraintValidator<Password, String> {
        private String pattern;

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {

            if (Objects.isNull(value)) {
                return true;
            }
            if (StringUtils.isBlank(pattern)) {
                pattern = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*()<>,.?-_+=`~])[0-9a-zA-Z!@#$%^&*()<>,.?-_+=`~]{6,18}$";
            }
            // 6-18位字母,数字,符号组合的密码
            // ^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*()<>,.?-_+=`~])[0-9a-zA-Z!@#$%^&*()<>,.?-_+=`~]{6,18}$
            return Pattern.matches(pattern, value);
        }

        @Override
        public void initialize(Password parameters) {
            pattern = parameters.pattern();
        }
    }
}
