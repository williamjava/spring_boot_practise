package com.gui.star.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 我的测试控制器
 * 
 * @author wuhoujian
 *
 */
@RestController
@RequestMapping("/hello")
public class HelloWorldController {
	
	@RequestMapping("/sayHi")
	public String sayHi() {
		return "Hello, SpringBoot!";
	}
}
