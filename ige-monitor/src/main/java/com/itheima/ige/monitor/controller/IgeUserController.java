package com.itheima.ige.monitor.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.ige.framework.controller.ControllerUtil;
import com.itheima.ige.framework.util.Request;
import com.itheima.ige.framework.util.Result;
import com.itheima.ige.monitor.dto.UserLogin;
import com.itheima.ige.monitor.dto.UserPassword;
import com.itheima.ige.monitor.pojo.IgeUser;
import com.itheima.ige.monitor.service.IgeUserService;
import com.itheima.ige.monitor.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 用户信息 控制器</p>
 *
 * @author 作者
 * @since 2022-03-14
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/api/igeUser")
public class IgeUserController extends ControllerUtil<IgeUser> {

    @Autowired
    private IgeUserService igeUserService;

    /****
     * 登录
     */
    @ApiOperation(value = "用户登录")
    @PostMapping(value = "/login")
    public Result<UserVo> login(@Valid @RequestBody UserLogin userLogin) throws Exception {
        //登录实现
        return igeUserService.login(userLogin);
    }

    /***
     * 删除账号
     */
    @ApiOperation(value = "删除账号")
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable(value = "id")Integer id){
        igeUserService.removeById(id);
        return Result.ok();
    }

    /***
     * 根据ID查询账号
     */
    @ApiOperation(value = "根据ID查询账号")
    @GetMapping(value = "/{id}")
    public Result<IgeUser> one(@PathVariable(value = "id")Integer id){
        return Result.ok(igeUserService.getById(id));
    }

    /***
     * 添加账号
     */
    @ApiOperation(value = "添加用户")
    @PostMapping
    public Result add(@RequestBody IgeUser igeUser){
        return igeUserService.addUser(igeUser);
    }

    /**
     * 修改密码
     */
    @ApiOperation(value = "修改密码")
    @PostMapping(value = "/password")
    public Result password(@Valid @RequestBody UserPassword userPassword){
        return igeUserService.modifyPassword(userPassword);
    }

    /**
     * 通用条件分页查询
     */
    @ApiOperation(value = "用户列表分页查询")
    @PostMapping(value = "/search")
    public Result<Page<IgeUser>> findByPage(@RequestBody Request<IgeUser> req) {
        Page page = new Page(req.getPage(), req.getSize());
        //条件 name 查询 非 lamda表达式查询条件
        QueryWrapper<IgeUser> queryWrapper = getWrapper(req.getBody());
        Page pageInfo = igeUserService.page(page, queryWrapper);
        return Result.ok(pageInfo);
    }
}

