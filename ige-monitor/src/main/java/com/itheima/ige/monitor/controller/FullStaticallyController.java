package com.itheima.ige.monitor.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.ige.framework.controller.Controller;
import com.itheima.ige.framework.util.Request;
import com.itheima.ige.framework.util.Result;
import com.itheima.ige.monitor.pojo.FullStatically;
import com.itheima.ige.monitor.pojo.NamespaceConfig;
import com.itheima.ige.monitor.pojo.ServerConfig;
import com.itheima.ige.monitor.service.FullStaticallyService;
import com.itheima.ige.monitor.service.NamespaceConfigService;
import com.itheima.ige.monitor.service.ServerConfigService;
import com.itheima.ige.util.IgeConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

/**
 * <p>
 * 全量静态化配置 控制器</p>
 *
 * @author 作者
 * @since 2022-03-14
 */
@Api(tags = "全量静态化管理")
@RestController
@RequestMapping("/api/fullStatically")
public class FullStaticallyController extends Controller<FullStatically> {

    @Autowired
    private FullStaticallyService fullStaticallyService;

    @Autowired
    private ServerConfigService serverConfigService;

    @Autowired
    private NamespaceConfigService namespaceConfigService;

    public FullStaticallyController(FullStaticallyService fullStaticallyService) {
        super(fullStaticallyService);
    }

    /***
     * 启用/停用
     */
    @ApiOperation(value = "启用/停用")
    @PutMapping(value = "/enable/{id}/{status}")
    public Result enable(@ApiParam(name = "id", value = "配置ID", required = true) @PathVariable(value = "id")String id, @ApiParam(name = "status", value = "1=启用，2=停用", required = true) @PathVariable(value = "status")Integer status) throws SchedulerException {
        if(status== IgeConstant.STATUS_TE_1 ||status==IgeConstant.STATUS_UTE_2){
            return fullStaticallyService.enable(id,status);
        }
        return Result.error("启用或停用取值不正确！");
    }

    /**
     * 删除记录
     * @param id
     * @return
     */
    @Override
    @ApiOperation(value = "根据ID删除")
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable(name = "id") Serializable id) throws SchedulerException {
        return fullStaticallyService.removeJob(id);
    }

    /**
     * 添加记录
     * @param record
     * @return
     */
    @Override
    @ApiOperation(value = "添加功能")
    @PostMapping
    public Result<FullStatically> insert(@RequestBody FullStatically record) throws SchedulerException {
        //查询服务数据
        if(record.getStorageType().equals(IgeConstant.ZERO)){
            record.setOutType(IgeConstant.OUTTYPE_RETURN);
        }else{
            ServerConfig serverConfig = serverConfigService.getById(record.getStorageType());
            record.setOutType(serverConfig.getServerType());
        }
        NamespaceConfig namespace = namespaceConfigService.getById(record.getNamespaceId());
        record.setNamespaceName(namespace.getNamespaceName());
        boolean flag = fullStaticallyService.saveJob(record);
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
    public Result updateByPrimaryKey(@RequestBody FullStatically record) throws SchedulerException {
        //查询服务数据
        if(record.getStorageType().equals(IgeConstant.ZERO)){
            record.setOutType(IgeConstant.OUTTYPE_RETURN);
        }else{
            ServerConfig serverConfig = serverConfigService.getById(record.getStorageType());
            record.setOutType(serverConfig.getServerType());
        }
        NamespaceConfig namespace = namespaceConfigService.getById(record.getNamespaceId());
        record.setNamespaceName(namespace.getNamespaceName());
        return fullStaticallyService.modifyJob(record);
    }

    /**
     * 通用条件分页查询
     * @param req
     * @return
     */
    @Override
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "/search")
    public Result<Page<FullStatically>> findByPage(@RequestBody Request<FullStatically> req) {
        Page page = new Page(req.getPage(), req.getSize());
        Page pageInfo = fullStaticallyService.page(page,
                Wrappers.<FullStatically>lambdaQuery()
                        .eq(FullStatically::getNamespaceId,req.getBody().getNamespaceId()));
        return Result.ok(pageInfo);
    }
}

