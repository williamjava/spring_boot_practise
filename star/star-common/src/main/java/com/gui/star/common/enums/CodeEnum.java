package com.gui.star.common.enums;

/**
 * 通用的枚举类，各种场景对应各种code
 * 
 * @author wuhoujian 2018/9/4
 *
 */
public enum CodeEnum {
	SUCCESS((byte) 0, "success"), FAIL((byte) 1, "fail"), UNLOGIN((byte) 2, "unlogin"), exception((byte) 3,
			"exception"), DENIED((byte) 4, "denied");

	private Byte code;
	private String msg;

	private CodeEnum(Byte code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Byte getCode() {
		return code;
	}

	public void setCode(Byte code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
