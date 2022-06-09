package com.itheima.ige.monitor.controller;

import com.itheima.ige.framework.controller.Controller;
import com.itheima.ige.framework.util.Result;
import com.itheima.ige.monitor.mapper.ServerConfigMapper;
import com.itheima.ige.monitor.pojo.IncrementStatically;
import com.itheima.ige.monitor.pojo.NamespaceConfig;
import com.itheima.ige.monitor.pojo.ServerConfig;
import com.itheima.ige.monitor.service.IncrementStaticallyService;
import com.itheima.ige.monitor.service.NamespaceConfigService;
import com.itheima.ige.monitor.service.ServerConfigService;
import com.itheima.ige.util.IgeConstant;
import com.itheima.ige.wrap.IgeAttribute;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 增量静态化配置 控制器</p>
 *
 * @author 作者
 * @since 2022-03-14
 */
@Api(tags = "增量静态化管理")
@RestController
@RequestMapping("/api/incrementStatically")
public class IncrementStaticallyController extends Controller<IncrementStatically> {

    @Autowired
    private IncrementStaticallyService incrementStaticallyService;

    @Autowired
    private NamespaceConfigService namespaceConfigService;

    @Autowired
    private ServerConfigService serverConfigService;

    public IncrementStaticallyController(IncrementStaticallyService incrementStaticallyService) {
        super(incrementStaticallyService);
    }


    /**
     * 添加记录
     * @param record
     * @return
     */
    @Override
    @ApiOperation(value = "添加功能")
    @PostMapping
    public Result<IncrementStatically> insert(@RequestBody IncrementStatically record) throws SchedulerException {
        //查询服务数据
        if(record.getStorageType().equals(IgeConstant.ZERO)){
            record.setOutType(IgeConstant.OUTTYPE_RETURN);
        }else{
            ServerConfig serverConfig = serverConfigService.getById(record.getStorageType());
            record.setOutType(serverConfig.getServerType());
        }
        NamespaceConfig namespace = namespaceConfigService.getById(record.getNamespaceId());
        record.setNamespaceName(namespace.getNamespaceName());
        boolean flag = incrementStaticallyService.save(record);
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
    public Result updateByPrimaryKey(@RequestBody IncrementStatically record) throws SchedulerException {
        //查询服务数据
        if(record.getStorageType().equals(IgeConstant.ZERO)){
            record.setOutType(IgeConstant.OUTTYPE_RETURN);
        }else{
            ServerConfig serverConfig = serverConfigService.getById(record.getStorageType());
            record.setOutType(serverConfig.getServerType());
        }

        NamespaceConfig namespace = namespaceConfigService.getById(record.getNamespaceId());
        record.setNamespaceName(namespace.getNamespaceName());
        boolean flag = incrementStaticallyService.updateById(record);
        if (!flag) {
            return Result.error();
        }
        return Result.ok();
    }

    /***
     * 主动触发-增量生成
     */
    @ApiOperation(value = "增量触发接口")
    @PostMapping(value = "/initiative/generate/remote")
    public Result generate(@RequestBody IgeAttribute igeAttribute) throws Exception {
        //执行生成
        return incrementStaticallyService.initiativeGenerate(igeAttribute);
    }

    /***
     * 主动触发-增量生成-批量操作
     */
    @ApiOperation(value = "增量触发接口")
    @PostMapping(value = "/initiative/generate/batch/remote")
    public Result generateBatch(@RequestBody List<IgeAttribute> igeAttributes) throws Exception {
        //执行生成
        return incrementStaticallyService.initiativeGenerateBatch(igeAttributes);
    }

    /***
     * 启用/停用
     */
    @ApiOperation(value = "启用/停用")
    @PutMapping(value = "/enable/{id}/{status}")
    public Result enable(@ApiParam(name = "id", value = "配置ID", required = true) @PathVariable(value = "id")String id, @ApiParam(name = "status", value = "1=启用，2=停用", required = true) @PathVariable(value = "status")Integer status){
        if(status==1||status==2){
            IncrementStatically incrementStatically = new IncrementStatically();
            incrementStatically.setId(id);
            incrementStatically.setStatus(status);
            incrementStaticallyService.updateById(incrementStatically);
            return Result.ok();
        }
        return Result.error("启用或停用取值不正确！");
    }
}

