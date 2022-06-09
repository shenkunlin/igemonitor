package com.itheima.ige.framework.controller;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;

/**
 * @Author：
 * @date： 2022/3/14 20:36
 * @Description：
 ***/
@Slf4j
public abstract class ControllerUtil<T> {

    /**
     * 通用包装
     * @param body
     * @return
     */
    public QueryWrapper<T> getWrapper(T body) {
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
}
