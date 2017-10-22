package com.gui.star.biz.event.events;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 测试事件
 * 
 * @author wuhoujian
 *
 */

@Setter
@Getter
@ToString
public class TestEvent extends ApplicationEvent {
	private static final long serialVersionUID = -5824265251729348221L;

	/** 事件的内容 */
	private String eventContent;

	public TestEvent(Object source) {
		super(source);
	}

	public TestEvent(Object source, String eventContent) {
		super(source);
		this.eventContent = eventContent;
	}
}
