package com.gui.star.web.executor;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时器测试类
 * 
 * @author wuhoujian
 *
 */
@Component
public class MyExecutor {

	// @Scheduled(cron = "0 0 1 ? * MON")，每周一凌晨1点执行
	// 每分钟执行
	@Scheduled(fixedRate = 60000)
	public void sayName() {
		System.out.println("My name is William...");
	}
}
