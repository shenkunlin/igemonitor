package com.itheima.ige.monitor.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.ige.framework.util.Result;
import com.itheima.ige.monitor.dto.UserLogin;
import com.itheima.ige.monitor.dto.UserPassword;
import com.itheima.ige.monitor.pojo.IgeUser;
import com.itheima.ige.monitor.vo.UserVo;

/**
 * <p>
 * 用户信息 服务类
 * </p>
 *
 * @author 作者
 * @since 2022-03-14
 */
public interface IgeUserService extends IService<IgeUser> {

    Result<UserVo> login(UserLogin userLogin) throws Exception;

    Result addUser(IgeUser igeUser);

    Result modifyPassword(UserPassword userPassword);
}
