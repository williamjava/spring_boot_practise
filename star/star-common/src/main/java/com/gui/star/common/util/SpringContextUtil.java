package com.gui.star.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * spring容器工具类，可以方便的获取容器对象和其它的bean
 *
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {
	// Spring应用上下文环境
	private static ApplicationContext applicationContext;

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 获取容器里面的某个bean对象
	 * 
	 * @param name
	 *            bean的名字
	 * @return
	 * @throws BeansException
	 */

	public static <T> T getBean(String name, Class<T> requiredType) throws BeansException {
		return applicationContext.getBean(name, requiredType);
	}

	/**
	 * 实现了ApplicationContextAware接口必须实现该方法,通过传递applicationContext参数初始化成员变量applicationContext
	 */

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextUtil.applicationContext = applicationContext;
	}
}
