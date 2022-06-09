package com.itheima.ige.monitor.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.itheima.ige.framework.controller.Controller;
import com.itheima.ige.framework.util.Result;
import com.itheima.ige.monitor.pojo.ServerConfig;
import com.itheima.ige.monitor.service.ServerConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 服务配置 控制器</p>
 *
 * @author 作者
 * @since 2022-03-14
 */
@Api(tags = "服务管理")
@RestController
@RequestMapping("/api/serverConfig")
public class ServerConfigController extends Controller<ServerConfig> {

    @Autowired
    private ServerConfigService serverConfigService;

    public ServerConfigController(ServerConfigService serverConfigService) {
        super(serverConfigService);
    }

    /***
     * 启用/停用
     */
    @ApiOperation(value = "启用/停用")
    @PutMapping(value = "/enable/{id}/{status}")
    public Result enable(@ApiParam(name = "id", value = "服务配置ID", required = true) @PathVariable(value = "id")String id,@ApiParam(name = "status", value = "1=启用，2=停用", required = true) @PathVariable(value = "status")Integer status){
        ServerConfig serverConfig = serverConfigService.getById(id);
        if(status==1||status==2){
            serverConfig.setEnable(status);
            serverConfigService.updateById(serverConfig);
            return Result.ok();
        }
        return Result.error("启用或停用取值不正确！");
    }

    /**
     * 添加记录
     * @param record
     * @return
     */
    @Override
    @ApiOperation(value = "添加功能")
    @PostMapping
    public Result<ServerConfig> insert(@RequestBody ServerConfig record) throws SchedulerException {
        boolean flag = serverConfigService.save(record);
        if (!flag) {
            return Result.error();
        }
        return Result.ok(record);
    }

    /**
     * 更新数据
     * @param record
     * @return
     */
    @Override
    @ApiOperation(value = "根据ID修改")
    @PutMapping
    public Result updateByPrimaryKey(@RequestBody ServerConfig record) throws SchedulerException {
        boolean flag = serverConfigService.updateById(record);
        if (!flag) {
            return Result.error();
        }
        return Result.ok();
    }

    /**
     * 查询所有可用的存储服务
     * @return
     */
    @ApiOperation(value = "查询所有可用的存储服务")
    @GetMapping(value = "/list")
    public Result<List<ServerConfig>> findAll() {
        List<ServerConfig> list = serverConfigService
                .list(Wrappers
                        .<ServerConfig>lambdaQuery()
                        .eq(ServerConfig::getEnable,1));
        return Result.ok(list);
    }
}

