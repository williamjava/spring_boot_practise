package com.gui.star.common.enums;

/**
 * 通用的枚举类，在只有是或者否这两种状态的情景中使用
 * 
 * @author wuhoujian
 *
 */
public enum YesNoEnum {

	NO((byte) 0), YES((byte) 1);

	private Byte code;

	private YesNoEnum(Byte code) {
		this.code = code;
	}

	public Byte getCode() {
		return code;
	}

	public void setCode(Byte code) {
		this.code = code;
	}
}
