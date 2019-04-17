package com.gui.star.common.util;

import com.gui.star.common.enums.CodeEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * 
 * @author wuhoujian
 * 
 * @date 2018/9/4
 *
 * @param <T>
 *            数据 以json格式返回客户端请求的数据
 */
@Data
@Slf4j
public class Result<T> implements Serializable {
	private T data;
	private int status;
	private String msg;

	public static <T> Result<T> generateSuccess(T t) {
		Result<T> result = new Result<T>();
		result.setData(t);
		result.setStatus(CodeEnum.SUCCESS.getCode());
		result.setMsg(CodeEnum.SUCCESS.getMsg());

		return result;
	}
	
	public static <T> Result<T> generateSuccessWithMsg(T t, String msg) {
		Result<T> result = new Result<T>();
		result.setData(t);
		result.setStatus(CodeEnum.SUCCESS.getCode());
		result.setMsg(msg);

		return result;
	}

	public static <T> Result<T> generateFail(T t) {
		Result<T> result = new Result<T>();
		result.setData(t);
		result.setStatus(CodeEnum.FAIL.getCode());
		result.setMsg(CodeEnum.FAIL.getMsg());
		log.error("返回结果异常处理：" + CodeEnum.FAIL.getMsg());
		return result;
	}

	/**
	 * 失败的时候返回的对象，同时返回详细的异常信息
	 * 
	 * @param t
	 * @param e
	 * @return
	 */
	public static <T> Result<T> generateFailWithExceptionMsg(T t, Throwable e) {
		Result<T> result = new Result<T>();
		result.setData(t);
		result.setStatus(CodeEnum.FAIL.getCode());
		result.setMsg(e.getMessage());
		log.error("返回结果异常处理：" + e.getMessage());
		return result;
	}
	
	/**
	 * 失败的时候返回的对象，同时返回自定义的异常信息
	 * 
	 * @param t
	 * @param msg
	 * @return
	 */
	public static <T> Result<T> generateFailWithCustomerDefinitonMsg(T t, String msg) {
		Result<T> result = new Result<T>();
		result.setData(t);
		result.setStatus(CodeEnum.FAIL.getCode());
		result.setMsg(msg);
		log.error("返回结果异常处理：" + msg);
		return result;
	}
}
