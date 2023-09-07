package com.cn.zooey.common.constraints.pattern.phone;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @Author Fengzl
 * @Date 2023/7/7 11:23
 * @Desc 自定义手机号校验注解, 适用中国
 **/
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Phone.List.class)
@Documented
@Constraint(validatedBy = {Phone.Validator.class})
public @interface Phone {
    // String message() default "{javax.validation.constraints.Phone.message}";
    String message() default "The phone number format error";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        Phone[] value();
    }


    class Validator implements ConstraintValidator<Phone, String> {
        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (Objects.isNull(value)) {
                return false;
            }
            return Pattern.matches("^(1[3-9])\\d{9}$", value);
        }
    }
}
