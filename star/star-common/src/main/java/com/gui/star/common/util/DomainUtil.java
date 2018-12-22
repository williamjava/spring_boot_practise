package com.gui.star.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.gui.star.common.constants.Constants;
import com.gui.star.common.enums.YesNoEnum;

import lombok.extern.slf4j.Slf4j;

/**
 * 设置实体默认属性工具类
 * 
 * @author wuhoujian
 * 
 * @date 2018/9/4
 *
 */
@Slf4j
@Component
public class DomainUtil {

	/**
	 * 为新增实体设置默认参数
	 * 
	 * @param pojo
	 */
	public static void setCommonValueForCreate(Object pojo) {
		try {
			List<String> methodNameList = new ArrayList<String>();
			setMethodNameList(pojo, methodNameList);

			Method setCreatedAt = pojo.getClass().getMethod(Constants.DOMAIN_METHOD_SETCREATEDAT, Date.class);
			setCreatedAt.invoke(pojo, new Date());

			Method setUpdatedAt = pojo.getClass().getMethod(Constants.DOMAIN_METHOD_SETUPDATEDAT, Date.class);
			setUpdatedAt.invoke(pojo, new Date());

			Method setDeleted = pojo.getClass().getMethod(Constants.DOMAIN_METHOD_SEDELETED, Byte.class);
			setDeleted.invoke(pojo, YesNoEnum.NO.getCode());

			if (hasField(pojo, "creator")) {
				
				Field userIdField = pojo.getClass().getDeclaredField("creator");
				if (userIdField != null) {
					userIdField.setAccessible(true);
					
					if (methodNameList.contains(Constants.DOMAIN_METHOD_SETCREATOR)) {
						Method setCreator = pojo.getClass().getMethod(Constants.DOMAIN_METHOD_SETCREATOR, Long.class);
						setCreator.invoke(pojo, userIdField.get(pojo));
					}
				}
			}
		} catch (Exception e) {
			log.info(" DomainUtil_setCommonValueForCreate_occurs_exception: ", e);
		}
	}

	/**
	 * 为修改设置默认参数
	 * 
	 * @param pojo
	 */
	public static void setCommonValueForUpdate(Object pojo) {
		try {
			List<String> methodNameList = new ArrayList<String>();
			setMethodNameList(pojo, methodNameList);

			Method setUpdatedAt = pojo.getClass().getMethod(Constants.DOMAIN_METHOD_SETUPDATEDAT, Date.class);
			setUpdatedAt.invoke(pojo, new Date());
		} catch (Exception e) {
			log.info(" DomainUtil_setCommonValueForUpdate_occurs_exception: ", e);
		}
	}

	private static void setMethodNameList(Object pojo, List<String> methodNameList) {
		Method[] methods = pojo.getClass().getMethods();
		if (methods.length > 0) {
			for (int i = 0; i < methods.length; i++) {
				methodNameList.add(methods[i].getName());
			}
		}
	}

	private static boolean hasField(Object pojo, String fieldName) {
		boolean hasField = false;

		Field[] fields = pojo.getClass().getDeclaredFields();

		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getName().equals(fieldName)) {
				hasField = true;
				break;
			}
		}

		return hasField;
	}
}
