package com.cn.zooey.dto;

import com.cn.zooey.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;

/**
 * @author Fengzl
 * @date 2023/10/7 10:55
 * @desc
 **/
@Data
@NoArgsConstructor
public class LoginUser implements UserDetails {
    private static final long serialVersionUID = 3115830304799955531L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "手机号")
    private String mobile;
    @Schema(description = "密码")
    private String password;

    @Schema(description = "性别:0-不详,1-男,2-女")
    private Integer gender;

    @Schema(description = "出生年月")
    private LocalDate birthday;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "头像")
    private String headUrl;

    @Schema(description = "地址")
    private String address;

    @Schema(description = "状态: 1-启用, 2-禁用")
    private Integer state;

    @Schema(description = "token")
    private String token;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    /**
     * 账号是否没有过期
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账号是否没有被锁定
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 账号的凭证是否没过期
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 账号是否可用
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }


    public LoginUser(User user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.mobile = user.getMobile();
        this.password = user.getPassword();
        this.gender = user.getGender();
        this.birthday = user.getBirthday();
        this.email = user.getEmail();
        this.headUrl = user.getHeadUrl();
        this.address = user.getAddress();
        this.state = user.getState();
    }
}
