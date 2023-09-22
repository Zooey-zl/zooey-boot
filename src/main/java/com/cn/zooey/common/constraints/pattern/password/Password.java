package com.cn.zooey.common.constraints.pattern.password;


import org.apache.commons.lang3.StringUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
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

    /**
     * 密码匹配正则
     * @return
     */
    String pattern() default "";

    /**
     * <p>密码级别</p>
     * (优先级低于pattern, 如果pattern存在,则使用pattern中的正则校验)
     * <p>1 - 弱密码 (6-18位必须包含字母、数字、特殊字符中任意2种)</p>
     * <p>2 - 强密码 (6-18位必须包含字母、数字、特殊字符中的3种)</p>
     * @return
     */
    int level() default 1;

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

        private int level = 1;

        /**
         * 允许的特殊字符
         * 注意: '-' 减号要放到最后,否则会导致 Fengzl666 匹配到强密码
         */
        private static final String ALLOW_SPECIAL_CHARACTER = "!@#$%^&*()<>,.?_+=`~-";


        /**
         * 强密码正则
         * 6-18位字母,数字,符号组合的密码(必须包含数字、字母、特殊字符中3种)
         */
        private static final String STRONG_PWD_PATTERN = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[" + ALLOW_SPECIAL_CHARACTER + "])[0-9a-zA-Z" + ALLOW_SPECIAL_CHARACTER + "]{6,18}";
        /**
         * 弱密码正则
         * 6-18位必须包含字母、数字、特殊字符中任意2种
         */
        private static final String WEAK_PWD_PATTERN = "(?!^(\\d+|[a-zA-Z]+|[" + ALLOW_SPECIAL_CHARACTER + "]+)$)^[\\w" + ALLOW_SPECIAL_CHARACTER + "]{6,18}$";

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {

            if (StringUtils.isBlank(value)) {
                return true;
            }
            //
            // 6-18位必须包含字母、数字、特殊字符中任意2种
            // (?!^(\d+|[a-zA-Z]+|[!@#$%^&*()<>,.?_+=`~-]+)$)^[\w!@#$%^&*()<>,.?_+=`~-]{6,18}$
            if (StringUtils.isBlank(pattern)) {
                if (level == 2) {
                    pattern = STRONG_PWD_PATTERN;
                } else {
                    pattern = WEAK_PWD_PATTERN;
                }
            }
            // 6-18位字母,数字,符号组合的密码
            // (?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*()<>,.?_+=`~-])[0-9a-zA-Z!@#$%^&*()<>,.?_+=`~-]{6,18}
            return Pattern.matches(pattern, value);
        }

        @Override
        public void initialize(Password parameters) {
            pattern = parameters.pattern();
            level = parameters.level();
        }
    }
}
