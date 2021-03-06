package com.gui.star.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import tk.mybatis.spring.annotation.MapperScan;

/**
 * SpringBoot启动类
 * 
 * 注解@EnableScheduling的作用：开启定时器，发现注解@Scheduled的任务并后台执行
 * 
 * @author wuhoujian
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.gui")
@MapperScan(basePackages = "com.gui.star.dal.mapper")
@EnableAutoConfiguration
@EnableScheduling
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
