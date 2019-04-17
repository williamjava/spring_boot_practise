package com.gui.star.web.controller;

import com.gui.star.biz.event.events.TestEvent;
import com.gui.star.common.util.Result;
import com.gui.star.common.util.SpringContextUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 我的测试控制器
 *
 * @author wuhoujian
 */
@Api(value = "测试管理Api", tags = "测试相关操作")
@RestController
@RequestMapping("/test")
public class TestController {
    @ApiOperation(value = "打招呼sayHi", notes = "打招呼sayHi", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/sayHi", method = RequestMethod.GET)
    public Result<String> sayHi() {
        return Result.generateSuccess("Hello, SpringBoot!");
    }

    @ApiOperation(value = "测试事件发布", notes = "测试事件发布", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/testEvent", method = RequestMethod.POST)
    public Result<String> testEvent() {
        // 发布事件
        SpringContextUtil.getApplicationContext().publishEvent(new TestEvent(this, "我的测试事件"));

        return Result.generateSuccess("事件发布成功，事件执行结果见控制台输出。");
    }
}
