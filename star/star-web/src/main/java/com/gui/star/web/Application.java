package com.gui.star.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

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
@EnableScheduling
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
