package com.itheima.ige.monitor.controller;

import com.itheima.ige.framework.controller.Controller;
import com.itheima.ige.framework.util.Result;
import com.itheima.ige.monitor.pojo.NamespaceConfig;
import com.itheima.ige.monitor.service.NamespaceConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 命名空间配置 控制器</p>
 *
 * @author 作者
 * @since 2022-03-14
 */
@Api(tags = "命名空间管理")
@RestController
@RequestMapping("/api/namespaceConfig")
public class NamespaceConfigController extends Controller<NamespaceConfig> {

    @Autowired
    private NamespaceConfigService namespaceConfigService;

    public NamespaceConfigController(NamespaceConfigService namespaceConfigService) {
        super(namespaceConfigService);
    }

    /**
     * 添加记录
     * @param record
     * @return
     */
    @ApiOperation(value = "添加功能")
    @PostMapping
    public Result<NamespaceConfig> insert(@RequestBody NamespaceConfig record) {
        return namespaceConfigService.addNamespace(record);
    }
}

