# 项目说明

### 技术栈

*  SpringBoot 2.6.13   [Index of /spring-boot/docs/2.6.13](https://docs.spring.io/spring-boot/docs/2.6.13/)
*  

>  提示
>
>  -  Knife4j提供的starter已经引用springdoc-openapi的jar，开发者需注意避免jar包冲突



### 参数校验

#### 1、什么是validator

Bean Validation是Java定义的一套基于注解的数据校验规范，目前已经从JSR 303的1.0版本升级到JSR 349的1.1版本，再到JSR 380的2.0版本（2.0完成于2017.08），已经经历了三个版本 。需要注意的是，JSR只是一项标准，它规定了一些校验注解的规范，但没有实现，比如@Null、@NotNull、@Pattern等，它们位于 jakarta.validation.constraints这个包下。而hibernate validator是对这个规范的实现，并增加了一些其他校验注解，如 @URL、@Length等，它们位于org.hibernate.validator.constraints这个包下。

```xml
<dependency>
    <groupId>org.hibernate.validator</groupId>
    <artifactId>hibernate-validator</artifactId>
    <version>8.0.0.Final</version>
</dependency>
```

#### 2、注解介绍

##### validator内置注解



##### hibernate validator中扩展注解



##### 自定义注解

#### 3、使用

##### 单参数校验

单参数校验只需要在参数前添加注解即可，如下所示：

```
public Result deleteUser(@NotNull(message = "id不能为空") Long id) {
  // do something
}
```

但有一点需要注意，如果使用单参数校验，controller类上必须添加@Validated注解，如下所示：

```
@RestController
@RequestMapping("/user")
@Validated // 单参数校验需要加的注解
public class UserController {
  // do something
}
```

##### 对象参数校验

对象参数校验使用时，需要先在对象的校验属性上添加注解，然后在Controller方法的对象参数前添加@Validated 注解，如下所示：

```
public Result addUser(@Validated UserAO userAo) {
    // do something
}

public class UserAO {
  @NotBlank
  private String name;

  @NotNull
  private Integer age;
  
  ……
}
```

##### 注解分组

在对象参数校验场景下，有一种特殊场景，同一个参数对象在不同的场景下有不同的校验规则。比如，在创建对象时不需要传入id字段（id字段是主键，由系统生成，不由用户指定），但是在修改对象时就必须要传入id字段。在这样的场景下就需要对注解进行分组。

1）组件有个默认分组Default.class, 所以我们可以再创建一个分组UpdateAction.class，如下所示：

```
public interface UpdateAction {
}
```

2）在参数类中需要校验的属性上，在注解中添加groups属性：

```
public class UserAO {

    @NotNull(groups = UpdateAction.class, message = "id不能为空")
    private Long id;
    
    @NotBlank
    private String name;

    @NotNull
    private Integer age;
    
    ……
}
```

如上所示，就表示只在UpdateAction分组下校验id字段，在默认情况下就会校验name字段和age字段。

然后在controller的方法中，在@Validated注解里指定哪种场景即可，没有指定就代表采用Default.class，采用其他分组就需要显示指定。如下代码便表示在addUser()接口中按照默认情况进行参数校验，在updateUser()接口中按照默认情况和UpdateAction分组对参数进行共同校验。

```
public Result addUser(@Validated UserAO userAo) {
  // do something
}
public Result updateUser(@Validated({Default.class, UpdateAction.class}) UserAO userAo) {
  // do something
}
```

##### **对象嵌套**

如果需要校验的参数对象中还嵌套有一个对象属性，而该嵌套的对象属性也需要校验，那么就需要在该对象属性上增加@Valid注解。

```
public class UserAO {

    @NotNull(groups = UpdateAction.class, message = "id不能为空")
    private Long id;
    
    @NotBlank
    private String name;

    @NotNull
    private Integer age;
    
    @Valid
    private Phone phone;
    
    ……
}

public class Phone {
    @NotBlank
    private String operatorType;
    
    @NotBlank
    private String phoneNum;
}
```

#### 4、参数非法统一异常处理

参数校验失败后会抛出异常，我们只需要在全局异常处理类中捕获参数校验的失败异常，然后将错误消息添加到返回值中即可。捕获异常的方法如下所示，返回值Result是我们系统自定义的返回值类。

```
@RestControllerAdvice(basePackages= {"com.alibaba.dc.controller","com.alibaba.dc.service"})
public class GlobalExceptionHandler {

  @ExceptionHandler(value = {Throwable.class})
  Result handleException(Throwable e, HttpServletRequest request){
    // 异常处理
        }
}
```

需要注意的是，如果缺少参数抛出的异常是MissingServletRequestParameterException，单参数校验失败后抛出的异常是ConstraintViolationException，get请求的对象参数校验失败后抛出的异常是BindException，post请求的对象参数校验失败后抛出的异常是MethodArgumentNotValidException，不同异常对象的结构不同，对异常消息的提取方式也就不同。如下所示：

```java
@ExceptionHandler(value = {Throwable.class})
ResResult<?> handleException(Throwable e, HttpServletRequest request) {
    int code = ResCode.PARAM_ILLEGAL.getCode();
    String msg = ResCode.PARAM_ILLEGAL.getMsg();

    // 缺少参数异常处理
    if (e instanceof MissingServletRequestParameterException) {
        msg = MessageFormat.format("缺少参数{0}", ((MissingServletRequestParameterException) e).getParameterName());
    } else if (e instanceof ConstraintViolationException) {
        // 单个参数校验异常
        Set<ConstraintViolation<?>> sets = ((ConstraintViolationException) e).getConstraintViolations();
        if (CollectionUtils.isNotEmpty(sets)) {
            StringBuilder sb = new StringBuilder();
            sets.forEach(error -> {
                if (error instanceof FieldError) {
                    sb.append(((FieldError) error).getField()).append(":");
                }
                sb.append(error.getMessage()).append(";");
            });
            msg = StringUtils.substring(sb.toString(), 0, msg.length() - 1);
        }
    } else if (e instanceof MethodArgumentNotValidException) {
        // post请求的对象参数校验异常
        List<ObjectError> errors = ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors();
        String validMsg = getValidExceptionMsg(errors);
        if (StringUtils.isNotBlank(validMsg)) {
            msg = validMsg;
        }
    } else if (e instanceof BindException) {
        // get请求的对象参数校验异常
        List<ObjectError> errors = ((BindException) e).getBindingResult().getAllErrors();
        String bindMsg = getValidExceptionMsg(errors);
        if (StringUtils.isNotBlank(bindMsg)) {
            msg = bindMsg;
        }
    } else {
        log.error("[系统异常] --- message -> {}", e.getMessage(), e);
        return ResResult.of(ResCode.INTERNAL_SERVER_ERROR.getCode(), e.getMessage());
    }
    log.warn("[参数校验异常] --- message -> {}", msg);
    //  ILLEGAL
    return ResResult.of(code, msg);
}


private String getValidExceptionMsg(List<ObjectError> errors) {
    if (CollectionUtils.isNotEmpty(errors)) {
        StringBuilder sb = new StringBuilder();
        errors.forEach(error -> {
            if (error instanceof FieldError) {
                sb.append(((FieldError) error).getField()).append(":");
            }
            sb.append(error.getDefaultMessage()).append(";");
        });
        String msg = sb.toString();
        msg = StringUtils.substring(msg, 0, msg.length() - 1);
        return msg;
    }
    return null;
}
```



