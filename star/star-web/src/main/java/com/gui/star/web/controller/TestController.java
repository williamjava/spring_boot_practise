package com.gui.star.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gui.star.biz.event.events.TestEvent;
import com.gui.star.common.util.SpringContextUtil;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 我的测试控制器
 * 
 * @author wuhoujian
 *
 */
@ApiIgnore
@RestController
@RequestMapping("/test")
public class TestController {

	@ApiOperation(value = "打招呼sayHi", notes = "打招呼sayHi")
	@RequestMapping("/sayHi")
	public String sayHi() {
		return "Hello, SpringBoot!";
	}

	@ApiOperation(value = "测试事件发布", notes = "测试事件发布")
	@RequestMapping("/testEvent")
	public String testEvent() {
		// 发布插入登录日志事件
		SpringContextUtil.getApplicationContext().publishEvent(new TestEvent(this, "我的测试事件"));
		return "事件发布成功，事件执行结果见控制台输出。";
	}
}
