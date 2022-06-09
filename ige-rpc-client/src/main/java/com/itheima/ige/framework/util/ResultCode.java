package com.itheima.ige.framework.util;

/**
 * @Author：
 * @date： 2022/3/7 10:34
 * @Description：
 ***/
public enum ResultCode {

    SUCCESS("操作成功", 200),
    FAIL("操作失败", 201),
    WEDNESDAY("用户名或密码错误", 501),
    UNMATCH ("两次密码不匹配", 502),
    THURSDAY("权限不足", 403),
    FRIDAY("远程调用失败", 404),
    EXIST("已存在要操作的数据", 407),
    GOLOGIN("请登录", 408);

    //文字描述
    private String msg;
    //对应的代码
    private Integer code;

    /**
     * 私有构造,防止被外部调用
     * @param msg
     */
    private ResultCode(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }

    /**
     * 定义方法,返回描述,跟常规类的定义没区别
     * @return
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 定义方法,返回代码,跟常规类的定义没区别
     * @return
     */
    public int getCode() {
        return code;
    }

    /**
     * 状态码描述
     * @return
     */
    public static String description(){
        StringBuilder builder = new StringBuilder("状态码信息：\n");
        for (ResultCode code :ResultCode.values()) {
            builder.append(code.getCode()+":"+code.getMsg()+"\n");
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        System.out.println(description());
    }
}
