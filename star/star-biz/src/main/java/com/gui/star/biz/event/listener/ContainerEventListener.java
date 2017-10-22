package com.gui.star.biz.event.listener;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.gui.star.biz.event.events.TestEvent;

import lombok.extern.slf4j.Slf4j;

/**
 * spring事件监听器
 * 
 * （1）额外的说明：
 * 注释@EventListener可以注解任意的Spring组件。Spring会为事件创建一个ApplicationListener实例，并从方法参数中获取事件的类型。一个类中被事件注释的方法数量没有限制，所有相关的事件句柄都会分组到一个类中。
 * 
 * （2）如果采用implements
 * ApplicationListener方式实现事件监听器，虽然功能上可以实现，但是它会针对每一个事件都创建一个新类，从而造成代码瓶颈
 * 
 * （3）所有的事件监听和处理逻辑可以统一存放在这个类里面，维护方便
 * 
 * 
 * @author wuhoujian
 *
 */
@Component
public class ContainerEventListener {
	/**
	 * 监听容器启动事件
	 * 
	 * 说明：
	 * 
	 * 1.这个方法特别重要，特殊之处在于它是用来监听容器启动事件的，容器启动完成后会自动发送一个ContextRefreshedEvent事件，我们就可以通过
	 * 监听这个事件，来完成整个系统的一些初始化工作
	 * 
	 * 2.容器的启动完成了表明所有的bean都已经完成了注册，而且bean之间的相互依赖也完成了
	 * 
	 * 3.容器启动完成和我们定义的Application启动类极其包含的main方法完全是两个概念。前者的时间发生在前面，Application.main()的作用在于启动容器里面的应用程序（比如我们的saturn）
	 * 
	 * 
	 * @param ContextRefreshedEvent
	 */
	@Async
	@EventListener
	public void afterContainerStartup(ContextRefreshedEvent event) {
		// 防止重复执行
		if (event.getApplicationContext().getParent() == null) {

		}
	}

	/**
	 * 监听所有的TestEvent类型事件
	 * 
	 */
	@Async
	@EventListener
	public void handleTestEvent(TestEvent event) {
		// 可以调用本地业务处理逻辑，也可以调用远程的发送短信、邮件等服务。

		System.out.println("我的测试事件开始执行了，测试内容：" + event.getEventContent());
	}
}
