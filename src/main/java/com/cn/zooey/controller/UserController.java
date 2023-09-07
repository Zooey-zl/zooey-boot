package com.cn.zooey.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.zooey.common.base.exception.SaasException;
import com.cn.zooey.common.base.result.ResCode;
import com.cn.zooey.common.base.result.ResResult;
import com.cn.zooey.common.base.util.Region;
import com.cn.zooey.common.constraints.group.AddAction;
import com.cn.zooey.common.constraints.group.UpdateAction;
import com.cn.zooey.common.constraints.pattern.phone.Phone;
import com.cn.zooey.common.util.IPUtils;
import com.cn.zooey.convert.UserConvert;
import com.cn.zooey.entity.User;
import com.cn.zooey.service.UserService;
import com.cn.zooey.vo.UserListVO;
import com.cn.zooey.vo.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.groups.Default;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Fengzl
 * @since 2023-06-25
 */
@Slf4j
@Validated
@Tag(name = "用户模块")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;


    // 增
    @Operation(summary = "新增用户")
    @PostMapping("/addUser")
    public ResResult<?> addUser(@Validated({Default.class, AddAction.class}) @RequestBody UserVO userVO) {

        log.info("接收参数: {}", JSONUtil.toJsonStr(userVO));
        User user = UserConvert.INSTANCE.toUser(userVO);
        log.info("转换后参数: {}", JSONUtil.toJsonStr(user));

        userService.save(user);

        return ResResult.ok();
    }

    // 改
    @Operation(summary = "修改用户")
    @PostMapping("/updateUser")
    public ResResult<?> updateUser(@Validated({Default.class, UpdateAction.class}) @RequestBody UserVO userVO) {

        User user = getUserById(userVO.getId());
        log.info("接收参数: {}", JSONUtil.toJsonStr(userVO));
        log.info("转换前参数: {}", JSONUtil.toJsonStr(user));
        UserConvert.INSTANCE.updateUser(userVO, user);
        log.info("转换后参数: {}", JSONUtil.toJsonStr(user));

        userService.updateById(user);

        return ResResult.ok();
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/removeUser")
    public ResResult<?> removeUser(@Positive Long id) {
        log.info("参数: {}", id);
        getUserById(id);

        userService.removeById(id);
        return ResResult.ok();
    }

    @Operation(summary = "查询用户详情")
    @GetMapping("/getUserInfoById")
    public ResResult<User> getUserInfoById(@Positive Long id) {
        log.info("参数: {}", id);
        User user = getUserById(id);

        return ResResult.ok(user);
    }


    @Operation(summary = "用户列表")
    @PostMapping("/list")
    public ResResult<List<User>> list(@Validated @RequestBody UserListVO userListVO) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(userListVO.getUserName())) {
            queryWrapper.like("user_name", userListVO.getUserName());
        }
        if (StringUtils.isNotBlank(userListVO.getEmail())) {
            queryWrapper.eq("email", userListVO.getEmail());
        }
        Page<User> page = new Page<>(1, 10);
        page = userService.page(page, queryWrapper);

        return ResResult.ok(page.getRecords());
    }

    @Operation(summary = "测试接口")
    @GetMapping("/test")
    public ResResult<?> test(@Phone(message = "手机号格式不正确") String phone) {

        return ResResult.of(ResCode.OK.getCode(), phone, ResCode.OK.getMsg());
    }


    @Operation(summary = "测试接口2")
    @GetMapping("/getSysMenus")
    public ResResult<?> getSysMenus() {
        Map<String, Object> map = new HashMap<>();
        map.put("path", "notfound");
        map.put("component", "NotfoundComponent");
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(map);

        return ResResult.ok(list);
    }


    @Operation(summary = "测试登录ip")
    @PostMapping("/getIp")
    public ResResult<?> getIp(HttpServletRequest request, String p) {
        String ip = IPUtils.getClientIp(request);

        String newIp = StringUtils.isEmpty(p) ? ip : p;
        Region region = IPUtils.domain2region(newIp);
        Assert.notNull(region.getCountry(), "ip不对");

        return ResResult.ok(newIp + " to " + region);
    }


    /**
     * @author Fengzl
     * @desc 获取用户信息<br />存在返回 User,不存在 throw 异常
     * @date 2023/8/31 13:59
     * @param id
     * @return User
     **/
    private User getUserById(Long id) {
        User user = userService.getById(id);

        if (Objects.isNull(user)) {
            throw new SaasException("用户不存在");
        }

        return user;
    }

}
