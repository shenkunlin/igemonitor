package com.itheima.ige.monitor.controller;

import com.itheima.ige.framework.controller.Controller;
import com.itheima.ige.monitor.pojo.IgeLog;
import com.itheima.ige.monitor.service.IgeLogService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 执行日志 控制器</p>
 *
 * @author 作者
 * @since 2022-03-14
 */
@Api(tags = "日志管理")
@RestController
@RequestMapping("/api/igeLog")
public class IgeLogController extends Controller<IgeLog> {

    @Autowired
    private IgeLogService igeLogService;

    public IgeLogController(IgeLogService igeLogService) {
        super(igeLogService);
    }
}

