package com.itheima.ige.monitor.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.ige.framework.util.Result;
import com.itheima.ige.framework.util.ResultCode;
import com.itheima.ige.monitor.dto.UserLogin;
import com.itheima.ige.monitor.dto.UserPassword;
import com.itheima.ige.monitor.mapper.IgeUserMapper;
import com.itheima.ige.monitor.pojo.IgeUser;
import com.itheima.ige.monitor.service.IgeUserService;
import com.itheima.ige.monitor.vo.UserVo;
import com.itheima.ige.util.BCrypt;
import com.itheima.ige.util.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author 作者
 * @since 2022-03-14
 */
@Service
public class IgeUserServiceImpl extends ServiceImpl<IgeUserMapper, IgeUser> implements IgeUserService {

    /**
     * 登录实现
     * @param userLogin
     * @return
     */
    @Override
    public Result<UserVo> login(UserLogin userLogin) throws Exception {
        //1)查询数据
        IgeUser igeUser = super.getOne(new LambdaQueryWrapper<IgeUser>()
                .eq(IgeUser::getUsername, userLogin.getUsername()));

        //2)匹配密码
        if(igeUser!=null && BCrypt.checkpw(userLogin.getPassword(),igeUser.getPassword())){
            //3)封装令牌
            String token = JwtUtil.generate(JSON.parseObject(JSON.toJSONString(igeUser), Map.class));
            UserVo userVo = new UserVo(igeUser.getId(), igeUser.getUsername(), token);
            return Result.ok(userVo);
        }
        return Result.error(ResultCode.WEDNESDAY);
    }

    /**
     * 添加账号
     * @param igeUser
     * @return
     */
    @Override
    public Result addUser(IgeUser igeUser) {
        //1)判断账号是否已经存在
        long count = super.count(new LambdaQueryWrapper<IgeUser>()
                .eq(IgeUser::getUsername, igeUser.getUsername()));

        //2)不存在
        if(count==0){
            //3)添加账号
            igeUser.setPassword(BCrypt.hashpw(igeUser.getPassword(),BCrypt.gensalt()));
            super.save(igeUser);
            return Result.ok();
        }
        return Result.error(ResultCode.EXIST);
    }

    /**
     * 修改密码
     * @param userPassword
     * @return
     */
    @Override
    public Result modifyPassword(UserPassword userPassword) {
        if(userPassword.getRePassword().equals(userPassword.getPassword())){
            //修改密码
            IgeUser igeUser = new IgeUser();
            igeUser.setId(userPassword.getId());
            igeUser.setPassword(BCrypt.hashpw(userPassword.getPassword(),BCrypt.gensalt()));
            super.updateById(igeUser);
            return Result.ok();
        }
        return Result.error(ResultCode.UNMATCH);
    }
}
