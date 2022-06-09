package com.itheima.ige.framework.controller;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.ige.framework.util.Request;
import com.itheima.ige.framework.util.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

/**
 * @Author：
 * @date： 2022/3/14 20:36
 * @Description：
 ***/
@Slf4j
public abstract class Controller<T> {

    //调用方的service
    protected IService<T> coreService;

    public Controller(IService<T> coreService) {
        this.coreService = coreService;
    }

    /**
     * 删除记录
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID删除")
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable(name = "id") Serializable id) throws SchedulerException {
        boolean flag = coreService.removeById(id);
        if (!flag) {
            return Result.error();
        }
        return Result.ok();
    }

    /**
     * 添加记录
     * @param record
     * @return
     */
    @ApiOperation(value = "添加功能")
    @PostMapping
    public Result<T> insert(@RequestBody T record) throws SchedulerException {
        boolean flag = coreService.save(record);
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
    @ApiOperation(value = "根据ID修改")
    @PutMapping
    public Result updateByPrimaryKey(@RequestBody T record) throws SchedulerException {
        boolean flag = coreService.updateById(record);
        if (!flag) {
            return Result.error();
        }
        return Result.ok();
    }

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID查询")
    @GetMapping("/{id}")
    public Result<T> findById(@PathVariable(name = "id") Serializable id) {
        T t = coreService.getById(id);
        return Result.ok(t);
    }

    /**
     * 查询所有
     * @return
     */
    @ApiOperation(value = "查询所有")
    @GetMapping
    public Result<List<T>> findAll() {
        List<T> list = coreService.list();
        return Result.ok(list);
    }

    /**
     * 通用条件分页查询
     * @param req
     * @return
     */
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "/search")
    public Result<Page<T>> findByPage(@RequestBody Request<T> req) {
        Page page = new Page(req.getPage(), req.getSize());

        //条件 name 查询 非 lamda表达式查询条件
        QueryWrapper<T> queryWrapper = getWrapper(req.getBody());
        Page pageInfo = coreService.page(page, queryWrapper);
        return Result.ok(pageInfo);
    }

    /**
     * 通用包装
     * @param body
     * @return
     */
    private QueryWrapper<T> getWrapper(T body) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if (body == null) {
            return queryWrapper;
        }
        Field[] declaredFields = body.getClass().getDeclaredFields();

        for (Field declaredField : declaredFields) {
            try {
                //遇到 id注解 则直接跳过 不允许实现根据主键查询
                if (declaredField.isAnnotationPresent(TableId.class) || declaredField.getName().equals("serialVersionUID")) {
                    //遇到
                    continue;
                }
                //属性描述器  record.getClass()
                //PropertyDescriptor propDesc = new PropertyDescriptor(declaredField.getName(), body.getClass());
                //获取这个值  先获取读方法的方法对象,并调用获取里面的值
                //Object value = propDesc.getReadMethod().invoke(body);
                declaredField.setAccessible(true);
                Object value = declaredField.get(body);

                //如果是字符串
                TableField annotation = declaredField.getAnnotation(TableField.class);
                //如果传递的值为空则不做处理
                if (!ObjectUtils.isEmpty(value)) {
                    //如是字符串 则用like
                    if (value.getClass().getName().equals("java.lang.String")) {
                        queryWrapper.like(annotation.value(), value);
                    } else {
                        //否则使用=号
                        queryWrapper.eq(annotation.value(), value);
                    }
                }
            } catch (Exception e) {
                log.error(e.getCause().getLocalizedMessage());
                e.printStackTrace();
            }
        }
        return queryWrapper;
    }

    /***
     * 初始化
     */
    public void initDate(T record, String... args) {
        Class<?> clazz = record.getClass();
        for (String arg : args) {
            try {
                Field field = clazz.getDeclaredField(arg);
                String typeName = field.getType().getSimpleName();
                field.setAccessible(true);
                switch (typeName) {
                    case "Date":
                        field.set(record, new Date());
                        break;
                }
            } catch (Exception e) {
                log.error("参数初始化发生异常",e);
            }
        }
    }
}
