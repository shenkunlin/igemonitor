/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : localhost:3306
 Source Schema         : ige_config

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : 65001

 Date: 16/05/2022 06:03:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for full_statically
-- ----------------------------
DROP TABLE IF EXISTS `full_statically`;
CREATE TABLE `full_statically`  (
  `id` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键ID',
  `job_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务名称',
  `unique_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '远程服务接口地址',
  `template` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '模板内容',
  `out_type` int(11) NOT NULL COMMENT '输出类型   1:输出本地  2:返回文件内容',
  `storage_type` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '存储服务类型',
  `local_path` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '输出本地的路径，只有out_type=1时，需要该值',
  `remote_url_receive` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接收模板数据的远程地址，只有out_type=2时，需要该值',
  `naming_key` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '命名规则属性,[输出类型=1时，才需要]',
  `namespace_id` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '命名空间ID',
  `namespace_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cron` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行时间,Cron表达式，例如，每5秒执行一次：0/5 * * * * ? ，参考地址：https://cron.qqe2.com/',
  `status` int(11) NULL DEFAULT 2 COMMENT '是否启用   1：开启  2：关闭',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '全量静态化配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of full_statically
-- ----------------------------
INSERT INTO `full_statically` VALUES ('1510025188160876546', '全量测试', 'demo', '<!doctype html>\n<html>\n <head>\n  <meta charset=\"UTF-8\">\n  <meta name=\"Generator\" content=\"EditPlus®\">\n  <meta name=\"Author\" content=\"\">\n  <meta name=\"Keywords\" content=\"\">\n  <meta name=\"Description\" content=\"\">\n  <title>Document</title>\n </head>\n <body>\n  ${username}\n </body>\n</html>', 1, '10001', 'D:/items', NULL, '*', '1510015721578999810', 'shop', '0/50 * * * * ? ', 1);
INSERT INTO `full_statically` VALUES ('1510090463208022017', 'Http_Gz_Shop_Full', 'gshop001', '<!DOCTYPE html>\n<html lang=\"en\">\n<head>\n    <meta charset=\"utf-8\">\n    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n    <meta name=\"screen-orientation\" content=\"portrait\"> <!--    //Android 禁止屏幕旋转-->\n    <meta name=\"full-screen\" content=\"yes\">             <!--    //全屏显示-->\n    <meta name=\"browsermode\" content=\"application\">     <!--    //UC应用模式，使用了application这种应用模式后，页面讲默认全屏，禁止长按菜单，禁止收拾，标准排版，以及强制图片显示。-->\n    <meta name=\"x5-orientation\" content=\"portrait\">     <!--    //QQ强制竖屏-->\n    <meta name=\"x5-fullscreen\" content=\"true\">          <!--    //QQ强制全屏-->\n    <meta name=\"x5-page-mode\" content=\"app\">            <!--    //QQ应用模式-->\n    <meta name=\"viewport\" content=\"width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no,minimal-ui\">    <!--    //WebApp全屏模式-->\n    <meta name=\"format-detection\" content=\"telphone=no, email=no\"/>  <!--    //忽略数字识别为电话号码和邮箱-->\n    <style>\n        .swiper-container{\n            overflow: hidden;\n        }\n    </style>\n    <link rel=\"icon\" href=\"./images/favicon.ico\">\n    <title>新诗“精魂”的追寻：穆旦研究新探 散文 中文 老年</title>\n</head>\n<body>\n<div id=\"app\">\n    <header>\n        <div class=\"back\">\n            <img onclick=\"history.go(-1)\" src=\"./images/back.png\" width=\"44\" alt=\"\">\n        </div>\n        <div class=\"swiper-container\">\n            <div class=\"swiper-wrapper\">\n                <#if images??>\n                    <#list images as image>\n                        <div class=\"swiper-slide\">\n                            <img src=\"${image}\" :onerror=\"defaultImage\" width=\"100%\"></img>\n                        </div>\n                    </#list>\n                </#if>\n            </div>\n            <!-- 如果需要分页器 -->\n            <div class=\"swiper-pagination\"></div>\n        </div>\n        <div class=\"detailsInfo\">\n            <div style=\"padding:8px 14px;position: relative;\">\n                <div class=\"cards\"><span class=\"left\"></span><span class=\"rit\"></span></div>\n                <img src=\"./images/detailLog.png\" class=\"ico\" width=\"45\" alt=\"\">\n            </div>\n            <div style=\"flex: 1; padding-top: 15px;padding-left: 30px;\">\n                <div><span style=\"font-size: 12px;\">￥</span><span style=\"font-size: 20px;font-weight: bold\">{{seckillPrice}}</span></div>\n                <div style=\"text-decoration: line-through;font-size: 12px;\"><span>￥</span><span>{{price}}</span></div>\n            </div>\n            <div style=\"padding-top: 10px;padding-right: 15px;font-size: 12px;\">\n                <div v-if=\"isbegin==1\" id=\"time\" class=\"time\">\n                    <div style=\"margin-bottom: 6px\">距离结束：</div>\n                    <div class=\"tim\" ><span>{{hours}}</span> ：<span>{{minutes}}</span> ：<span>{{seconds}}</span></div>\n                </div>\n                <div v-if=\"isbegin==0\" id=\"time\" class=\"time\">\n                    <div style=\"margin-bottom: 6px\">距离开始：</div>\n                    <div class=\"tim\"><span>{{hours}}</span> ：<span>{{minutes}}</span> ：<span>{{seconds}}</span> </div></div>\n                <div v-if=\"isbegin==-1\" id=\"time\" class=\"time\" style=\"line-height: 50px;\">活动已结束</div>\n            </div>\n        </div>\n    </header>\n    <div class=\"skileDetail\">\n        <div class=\"itemCont infoCont\">\n            <div class=\"title\">${sku.name}</div>\n            <div class=\"desc\">${sku.brandName} ${sku.category3Name}</div>\n            <div class=\"item\"><div class=\"info\"><span>已选</span><span>${spec} 1件</span></div><img src=\"./images/right.png\" width=\"44\" alt=\"\"></div>\n            <div class=\"item\"><div class=\"info\"><span>送至</span><span>北京市昌平区建材城西路9号, 传智播客前台</span></div><img src=\"./images/right.png\" width=\"44\" alt=\"\"></div>\n        </div>\n        <div class=\"itemCont comment\">\n            <div class=\"title\">\n                <div class=\"tit\">评论</div>\n                <div class=\"rit\">\n                    <span>好评度 100%</span>\n                    <img src=\"./images/right.png\" width=\"44\" alt=\"\">\n                </div>\n            </div>\n            <div class=\"cont\">\n                <div class=\"item\">\n                    <div class=\"top\">\n                        <div class=\"info\">\n                            <img src=\"./images/icon.png\" width=\"25\" height=\"25\" alt=\"\" />\n                            <div style=\"color:#666;margin-left: 10px; font-size: 14px;\">张庆</div>\n                        </div>\n                        <div>\n                            <img src=\"./images/shoucang.png\" width=\"11\" alt=\"\">\n                            <img src=\"./images/shoucang.png\" width=\"11\" alt=\"\">\n                            <img src=\"./images/shoucang.png\" width=\"11\" alt=\"\">\n                            <img src=\"./images/shoucang.png\" width=\"11\" alt=\"\">\n                            <img src=\"./images/shoucang.png\" width=\"11\" alt=\"\">\n                        </div>\n                    </div>\n                    <div class=\"des\">\n                        <div class=\"tit\">质量不错，灵敏度高，结构巧妙，款式也好看</div>\n                        <div style=\"margin: 10px 0\">\n                            <img src=\"./images/detail1.png\" width=\"79\" height=\"79\" style=\"border-radius: 3px;\"/>\n                            <img src=\"./images/detail2.png\" width=\"79\" height=\"79\" style=\"border-radius: 3px;\"/>\n                        </div>\n                        <div class=\"font\">\n                            购买时间：2016-12-02      黑色，公开版，128GB,1件\n                        </div>\n                    </div>\n                </div>\n            </div>\n        </div>\n        <div class=\"content\">\n            <img src=\"./images/demo.png\" width=\"100%\" alt=\"\">\n        </div>\n    </div>\n    <footer>\n        <div class=\"collection\">\n            <span><img src=\"./images/shoucang.png\" width=\"21\" alt=\"\"></span>\n            <span>收藏</span>\n        </div>\n        <button v-if=\"isbegin==1\" value=\"立即购买\" @click=\"addOrder()\">立即购买</button>\n        <button v-if=\"isbegin!=1\" value=\"立即购买\" style=\"background: #92949C;\" disabled=\"disabled\">立即购买</button>\n    </footer>\n</div>\n<!-- 引入组件库 -->\n<script src=\"vue.js\"></script>\n<script type=\"text/javascript\" src=\"axios.js\"></script><!-- axios交互-->\n<script>\n    var app = new Vue({\n        el:\'#app\',\n        data:{\n            message:\'\',\n            hours:\'\',\n            minutes:\'\',\n            seconds:\'\',\n            socket:null,\n            isbegin:-1,\n            showTime:\'\',\n            defaultImage:\'this.src=\"https://img11.360buyimg.com/n7/jfs/t1/101646/6/13621/393111/5e5d1390E7ea13607/0117bb13ca414b11.jpg\"\',\n            price:0,\n            seckillPrice:0\n        },\n        methods:{\n            /***\n             * 时间运算\n             * @param starttimes:秒杀开始时间\n             * @param endtimes：秒杀结束时间\n             */\n            timeCalculate:function (starttimes,endtimes) {\n                //获取当前时间\n                let nowtimes = new Date().getTime();\n\n                console.log(\'starttimes:\'+starttimes,\'endtimes:\'+endtimes,\'nowtimes:\'+nowtimes)\n                if(nowtimes>endtimes){\n                    this.message=\'活动已结束！\';\n                    this.isbegin=-1;\n                    return;\n                }\n                //时间差\n                let nums = 0;\n\n                //前缀，记录倒计时描述\n                let prefix =\'距离结束：\';\n\n                //判断是距离开始/距离结束\n                if(starttimes<=nowtimes){\n                    //距离结束,运算求出nums\n                    nums = endtimes-nowtimes;\n                    this.isbegin=1;\n                }else{\n                    //距离开始\n                    prefix=\'距离开始：\';\n\n                    //运算求出nums\n                    nums = starttimes-nowtimes;\n                    this.isbegin=0;\n                }\n\n                //nums定时递减\n                let clock = window.setInterval(function () {\n                    //时间递减\n                    nums=nums-1000;\n\n                    //消息拼接\n                    prefix+app.timedown(nums);\n\n                    //nums<0\n                    if(nums<=0){\n                        //结束定时任务\n                        window.clearInterval(clock);\n                        //刷新时间，重新调用该方法\n                        app.timeCalculate(starttimes,endtimes);\n                    }\n                },1000);\n            },\n\n            //将毫秒转换成天时分秒\n            timedown:function(num) {\n                var oneSecond = 1000;\n                var oneMinute=oneSecond*60;\n                var oneHour=oneMinute*60\n                var oneDay=oneHour*24;\n                //天数\n                //var days =Math.floor(num/oneDay);\n                //小时\n                //this.hours =Math.floor((num%oneDay)/oneHour);\n                this.hours =Math.floor(num/oneHour);\n                //分钟\n                this.minutes=Math.floor((num%oneHour)/oneMinute);\n                //秒\n                this.seconds=Math.floor((num%oneMinute)/oneSecond);\n                //拼接时间格式\n                //var str = days+\'天\'+hours+\'时\'+minutes+\'分\'+seconds+\'秒\';\n                //return str;\n                this.showTime=this.hours+\'小时\'+this.minutes+\'分钟\'+this.seconds+\'秒\'\n            },\n\n            //计算时间\n            loadTime:function () {\n                //查询商品信息\n                axios.get(\'http://data-seckill-java.itheima.net/api/sku/${sku.id}\').then(function (resp) {\n                    let skuinfo = resp.data.data;\n                    app.price=skuinfo.price;\n                    app.seckillPrice=skuinfo.seckillPrice;\n\n                    //秒杀倒计时时间\n                    console.log(skuinfo.seckillBegin)\n                    console.log(skuinfo.seckillEnd)\n                    let tm1 = new Date(skuinfo.seckillBegin.replace(/-/g,\"/\")).getTime();\n                    let tm2 = new Date(skuinfo.seckillEnd.replace(/-/g,\"/\")).getTime();\n                    app.timeCalculate(tm1,tm2);\n                })\n            },\n            //下单\n            addOrder:function () {\n                //获取令牌\n                var token = localStorage.getItem(\"token\");\n                if(token!=null && token!=\'\'){\n                    //将令牌传给后台  /lua/order/add\n                    var instance = axios.create({\n                    });\n                    instance.defaults.headers.common[\'Authorization\'] = \'Bearer \'+token;\n                    //发送请求\n                    instance.post(`http://data-seckill-java.itheima.net/api/order/add/${sku.id}`).then(function (response) {\n                        if(response.data.flag){\n                            //跳转到个人中心\n                            location.href=\'http://data-seckill-java.itheima.net/#/orderinfo?id=\'+response.data.message;\n                        }else if(response.data.code===401){\n                            alert(\'您的登录信息已过期，请重新登录！\')\n                            window.localStorage.clear();\n                            //跳转登录\n                            location.href=\'http://data-seckill-java.itheima.net/#/login\';\n                        }else{\n                            alert(response.data.message)\n                        }\n                    })\n                }else{\n                    //跳转登录\n                    location.href=\'http://data-seckill-java.itheima.net/#/login\';\n                }\n            }\n        },\n        created:function () {\n            this.loadTime();\n        }\n    });\n</script>\n\n<script>\n    // var mySwiper = new Swiper (\'.swiper-container\', {\n    //     loop: true, // 循环模式选项\n    //     // 如果需要分页器\n    //     pagination: {\n    //         el: \'.swiper-pagination\',\n    //     },\n    // })\n</script>\n<style>\n    html,body{\n        padding: 0;\n        margin: 0;\n        background: #F7F7F8;\n    }\n    header{\n        background: #fff;\n    }\n    header .back{\n        position: absolute;\n        top:20px;\n        left: 20px;\n        z-index: 999;\n    }\n    header .detailsInfo{\n        position: relative;\n        z-index: 99;\n        color: #fff;\n        display: flex;\n        height: 66px;\n        top: -4px;\n        background: linear-gradient(-90deg, #F87755 0%, #D82F41 64%);\n    }\n    header .detailsInfo .ico{\n        z-index: 9;\n        position: relative;\n        left: 0px;\n        top: 4px;\n    }\n    header .detailsInfo .cards .left{\n        background: #C11E30;\n        width: 65px;\n        height: 70px;\n        position: absolute;\n        top:-4px;\n        left: 0px;\n        z-index: 1;\n    }\n    header .detailsInfo .cards .rit{\n        background: transparent;\n        width: 30px;\n        height: 70px;\n        position: absolute;\n        overflow: hidden;\n        top:-4px;\n        left: 65px;\n        z-index: 1;\n    }\n    header .detailsInfo .cards .rit::before{\n        content: \'\';\n        display: block;\n        position: relative;\n        width: 20px;\n        height: 130px;\n        transform: rotateZ(10deg) translate(-14px, -10px);\n        box-shadow: 0px -4px 10px rgba(0,0,0,0.1);\n        background: #C11E30;\n    }\n    header .detailsInfo .time span{\n        background: #000;\n        display: inline-block;\n        padding: 2px 4px;\n        text-align: center;\n        min-width: 18px;\n        font-size: 14px;\n        border-radius: 3px;\n    }\n    .swiper-container {\n        width: 100%;\n        height: 300px;\n    }\n    .skileDetail{\n        padding-bottom: 88px;\n    }\n    .skileDetail .infoCont .title{\n        color:#333;\n        padding: 10px;\n    }\n    .skileDetail .infoCont .desc{\n        padding: 10px;\n        font-size: 12px;\n        color: #D82F41;\n        position: relative;\n        top: -8px;\n        line-height: 6px;\n    }\n    .skileDetail .infoCont .item{\n        border-top:solid 1px rgba(247,247,248,1);\n        padding-left:15px;\n        line-height: 44px;\n        display: flex;\n        justify-content: space-between;\n    }\n    .skileDetail .infoCont .item .info{\n        font-size: 13px;\n        display: flex;\n        width: calc(100% - 44px);\n    }\n    .skileDetail .infoCont .item span:first-child{\n        width: 44px;\n        color:#92949C;\n    }\n    .skileDetail .infoCont .item span:last-child{\n        display: block;\n        line-height: 44px;\n        overflow: hidden;\n\n        flex: 1;\n        white-space:nowrap;\n        color:#333;\n    }\n    .itemCont{\n        background: #fff;\n        margin-bottom: 10px;\n    }\n    .comment .title{\n        padding-right: 0;\n        line-height: 44px;\n        color:#666666;\n        display: flex;\n        justify-content: space-between;\n        border-bottom: solid 1px #EAEAEA;\n        align-items: center;\n        font-size: 14px;\n    }\n    .comment .title .tit{\n        position: relative;\n        font-weight: bold;\n        padding-left: 10px;\n    }\n    .comment .title .tit:before{\n        position: relative;\n        display: inline-block;\n        width:2px;\n        height:15px;\n        background:rgba(216,47,65,1);\n        content: \'\';\n        left: -5px;\n        top:2px;\n        font-weight: bold;\n    }\n    .comment .title .rit{\n        display: flex;\n        font-size: 13px;\n    }\n    .comment .cont{\n        padding: 15px 0;\n        width: 100%;\n    }\n    .comment .cont .top{\n        width: calc(100% - 20px);\n        display: flex;\n        justify-content: space-between;\n        align-items: center;\n        margin-bottom: 10px;\n    }\n    .comment .cont .top .info{\n        display: flex;\n        flex: 1;\n        align-items: center;\n        padding-left: 20px;\n    }\n    .comment .cont .des{\n        padding-left: 57px;\n    }\n    .comment .cont .des .tit{\n        font-size: 13px;\n        color: #333333;\n    }\n    .comment .cont .des .font{\n        font-size: 12px;\n        color: #999;\n    }\n    footer{\n        position: fixed;\n        background: #fff;\n        padding: 20px 20px 0px;\n        display: flex;\n        height: 58px;\n        width: calc(100% - 40px);\n        left:0px;\n        bottom:0px;\n        border-top:solid 1px #EDEDED;\n    }\n    footer button{\n        flex: 1;\n        text-align: center;\n        font-size: 12px;\n        height:35px;\n        background:linear-gradient(90deg,rgba(208,40,58,1) 43%,rgba(248,118,84,1) 100%);\n        border-radius:18px;\n        color:#fff;\n        outline:none;\n        border:none;\n    }\n    footer .collection{\n        width: 30px;\n        margin-right: 10px;\n        display: flex;\n        flex-direction: column;\n        font-size: 12px;\n    }\n</style>\n</body>\n</html>', 2, '10002', 'D:/items', NULL, '*', '1510086791916953601', 'gzshop', '0/50 * * * * ? ', 1);

-- ----------------------------
-- Table structure for ige_log
-- ----------------------------
DROP TABLE IF EXISTS `ige_log`;
CREATE TABLE `ige_log`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键ID',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '日志内容',
  `log_type` int(11) NOT NULL COMMENT '类型  1：普通操作日志   2：异常日志   3：全量操作日志  4：增量操作日志',
  `namespace_id` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '所属命名空间ID',
  `namespace_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属命名空间名字',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '执行日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ige_user
-- ----------------------------
DROP TABLE IF EXISTS `ige_user`;
CREATE TABLE `ige_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账号',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `i_username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ige_user
-- ----------------------------
INSERT INTO `ige_user` VALUES (2, 'admin', '$2a$10$p.PvgUCet4ayohKIR5XgEuyNRGvAZPjn3nEHu5AlbgmYTgMSGjaEi');

-- ----------------------------
-- Table structure for increment_statically
-- ----------------------------
DROP TABLE IF EXISTS `increment_statically`;
CREATE TABLE `increment_statically`  (
  `id` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键ID',
  `job_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务名称',
  `unique_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '业务唯一识别码',
  `template` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '模板内容',
  `out_type` int(11) NOT NULL COMMENT '输出类型   1:输出本地  2:原路返回文件内容',
  `storage_type` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '存储类型',
  `local_path` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '输出本地的路径，只有out_type=1时，需要该值',
  `naming_key` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '命名规则属性,[输出类型=1时，才需要]',
  `namespace_id` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '命名空间ID',
  `namespace_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '命名空间名字',
  `trigger_method` int(11) NULL DEFAULT NULL COMMENT '触发方式：1：主动触发，2：MQ',
  `trigger_mq_addr` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'MQ触发方式的MQ地址，只有trigger_method=2时，才有该值',
  `trigger_mq_queue` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'MQ触发方式的MQ队列名字，只有trigger_method=2时，',
  `status` int(1) NULL DEFAULT 2 COMMENT '是否启用   1：开启  2：关闭',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '增量静态化配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of increment_statically
-- ----------------------------
INSERT INTO `increment_statically` VALUES ('1510089514624229378', 'demo', 'll', '<!doctype html>\n<html>\n <head>\n  <meta charset=\"UTF-8\">\n  <meta name=\"Generator\" content=\"EditPlus®\">\n  <meta name=\"Author\" content=\"\">\n  <meta name=\"Keywords\" content=\"\">\n  <meta name=\"Description\" content=\"\">\n  <title>Document</title>\n </head>\n <body>\n  你好，${username},欢迎来到传智教育学习Java课程！Java\n </body>\n</html>', 2, '10002', 'D:/items', '*', '1510015721578999810', 'shop', 1, NULL, NULL, 1);

-- ----------------------------
-- Table structure for namespace_config
-- ----------------------------
DROP TABLE IF EXISTS `namespace_config`;
CREATE TABLE `namespace_config`  (
  `id` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '命名空间ID',
  `namespace_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '命名空间名字',
  `description` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '命名空间描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '命名空间配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of namespace_config
-- ----------------------------
INSERT INTO `namespace_config` VALUES ('1510015721578999810', 'shop', '商城项目qq');
INSERT INTO `namespace_config` VALUES ('1510086791916953601', 'gzshop', 'GZ商城项目tt');

-- ----------------------------
-- Table structure for server_config
-- ----------------------------
DROP TABLE IF EXISTS `server_config`;
CREATE TABLE `server_config`  (
  `id` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `server_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '服务名字',
  `server_type` int(11) NOT NULL COMMENT '服务类型  1：Nacos   2：Http服务配置',
  `description` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '服务描述',
  `config_content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '配置内容',
  `enable` int(11) NOT NULL DEFAULT 2 COMMENT '是否启用   1：开启  2：关闭',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '服务配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of server_config
-- ----------------------------
INSERT INTO `server_config` VALUES ('10001', 'FastDFS-192.168.211.130', 1, NULL, '{\n	\"tracker_server\":\"192.168.211.130:22122\",\n    \"server_http\":\"http://192.168.211.130:8888/\"\n}', 1);
INSERT INTO `server_config` VALUES ('10002', 'AliyunOSS-130', 2, NULL, '{\n   \"endpoint\":\"https://oss-cn-hangzhou.aliyuncs.com\",\n   \"access-key\":\"LTAI5tSdh1fPyrJCXLy4u6BA\",\n   \"access-secret\":\"Ty25AsL7amm1MkDdefGkvynwR85PoW\",\n   \"bucket-name\":\"itheima-ige\",\n   \"content-type\":\"text/html\",\n   \"url\":\"https://itheima-ige.oss-cn-hangzhou.aliyuncs.com/\"\n}', 1);

SET FOREIGN_KEY_CHECKS = 1;
