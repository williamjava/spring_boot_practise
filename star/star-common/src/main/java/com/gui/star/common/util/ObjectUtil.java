package com.gui.star.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 实用对象工具类
 * 
 * @author wuhoujian
 *
 */
@Slf4j
public class ObjectUtil {
	/**
	 * 设置为""的属性值为为null
	 * 
	 * @param source
	 * @param propertyName
	 */
	public static void setEmptyProperty2NullProperty(Object source, String propertyName) {
		Field sourceField;
		try {
			sourceField = source.getClass().getDeclaredField(propertyName);
			// 原始对象不具备指定的属性
			if (sourceField == null) {
				return;
			}

			sourceField.setAccessible(true);

			if (thisEquals("", sourceField.get(source))) {
				sourceField.set(source, null);
			}
		} catch (Exception e) {
			log.info("设置属性值为null出现异常!");
			e.printStackTrace();
		}
	}

	public static boolean thisEquals(Object o1, Object o2) {
		return (o1 == null) ? (o2 == null) : (o2 != null && o1.equals(o2));
	}

	/**
	 * 泛型方法，将一个对象集合转化为另一个对象集合，这个方法没有日期等的处理，最为一般化
	 * 
	 * @param <S>
	 *            原始对象
	 * 
	 * @param <T>
	 *            目标对象
	 * 
	 * @param sourceList
	 *            原始对象集合
	 * 
	 * @param targetClass
	 *            目标对象的类型
	 * 
	 * @param ignoreProperties
	 *            不进行复制的属性
	 * @return
	 */

	public static <S, T> List<T> sourceList2TargetList(List<S> sourceList, Class<T> targetClass,
			String... ignoreProperties) {
		if (CollectionUtils.isEmpty(sourceList)) {
			return Collections.emptyList();
		}

		List<T> targetList = new ArrayList<>();

		try {
			for (S each : sourceList) {
				T target = targetClass.newInstance();

				BeanUtils.copyProperties(each, target, ignoreProperties);

				targetList.add(target);
			}
		}

		catch (Exception e) {
			log.error("将一个对象集合转化为另一个对象集合出现异常", e);
		}

		return targetList;
	}

	/**
	 * 泛型方法，将一个对象集合转化为另一个对象集合，这个业务性较强，一般包含updatedAt和createdAt属性的处理
	 * 
	 * @param <S>
	 *            原始对象
	 * 
	 * @param <T>
	 *            目标对象
	 * 
	 * @param sourceList
	 *            原始对象集合
	 * 
	 * @param targetClass
	 *            目标对象的类型
	 * 
	 * @param dates
	 *            日期相关的属性
	 * 
	 * @param ignoreProperties
	 *            不进行复制的属性
	 * @return
	 */

	public static <S, T> List<T> sourceList2TargetListWithDate(List<S> sourceList, Class<T> targetClass, String[] dates,
			String... ignoreProperties) {
		if (CollectionUtils.isEmpty(sourceList)) {
			return Collections.emptyList();
		}

		List<T> targetList = new ArrayList<>();
		List<String> dateList = (dates != null && dates.length > 0) ? Arrays.asList(dates) : Collections.emptyList();

		try {
			for (S each : sourceList) {
				T target = targetClass.newInstance();

				BeanUtils.copyProperties(each, target, ignoreProperties);

				// 如果传过来updatedAt和createdAt等属性，那么就进行这些属性的设置
				if (!CollectionUtils.isEmpty(dateList)) {
					if (dateList.contains("updatedAt")) {
						setDateProperty(each, target, "updatedAt", "updatedAt", null);
					}

					if (dateList.contains("createdAt")) {
						setDateProperty(each, target, "createdAt", "createdAt", null);
					}
				}

				targetList.add(target);
			}
		}

		catch (Exception e) {
			log.error("将一个包含日期处理的对象集合转化为另一个对象集合出现异常", e);
		}

		return targetList;
	}

	/**
	 * 泛型方法，将一个对象集合转化为另一个对象集合，包含对指定日期属性的处理，并且日期的格式化形式完全由客户指定
	 * 
	 * @param <S>
	 *            原始对象
	 * 
	 * @param <T>
	 *            目标对象
	 * 
	 * @param sourceList
	 *            原始对象集合
	 * 
	 * @param targetClass
	 *            目标对象的类型
	 * 
	 * @param sourceDates
	 *            原始对象需要处理的日期属性名称集合
	 * 
	 * @param targetDates
	 *            目标对象需要处理的日期属性名称集合
	 * 
	 * @param dateFormatPatterns
	 *            每一个特殊的日期需要进行的格式化形式，与sourceDates一一对应，dateFormatPatterns的长度与dates必须相等
	 * 
	 * 
	 * @param ignoreProperties
	 *            不进行复制的属性
	 * @return
	 */

	public static <S, T> List<T> sourceList2TargetListWithSpecialDate(List<S> sourceList, Class<T> targetClass,
			String[] sourceDates, String[] targetDates, String[] dateFormatPatterns, String... ignoreProperties) {
		if (CollectionUtils.isEmpty(sourceList)) {
			return Collections.emptyList();
		}

		// 判断有没有日期需要进行处理
		boolean needHandleDate = (sourceDates != null && sourceDates.length > 0);
		List<T> targetList = new ArrayList<>();

		try {
			for (S each : sourceList) {
				T target = targetClass.newInstance();

				BeanUtils.copyProperties(each, target, ignoreProperties);

				// 如果有多个，那么依次处理
				if (needHandleDate) {
					for (int i = 0; i < sourceDates.length; i++) {
						setDateProperty(each, target, sourceDates[i], targetDates[i], dateFormatPatterns[i]);
					}
				}

				targetList.add(target);
			}
		}

		catch (Exception e) {
			log.error("将一个包含日期处理的对象集合转化为另一个对象集合出现异常", e);
		}

		return targetList;
	}

	/**
	 * 泛型方法，将一个对象集合转化为另一个对象集合，只复制声明的属性
	 * 
	 * 注意:sourceProps和targetProps必须一一对应起来
	 * 
	 * 
	 * @param <S>
	 *            原始对象
	 * 
	 * @param <T>
	 *            目标对象
	 * 
	 * @param sourceList
	 *            原始对象集合
	 * 
	 * @param targetClass
	 *            目标对象的类型
	 * 
	 * @param sourceProps
	 *            需要复制的源对象的属性名称的集合
	 * 
	 * @param isPropsSame
	 *            需要复制的源对象的属性名称的集合与目标对象的属性名称的集合是否完全一样。 如果完全一样，targetProps不必再传
	 * 
	 * @param targetProps
	 *            目标对象的属性名称的集合
	 * 
	 * @return
	 */
	public static <S, T> List<T> sourceList2TargetListWithAnyProperties(List<S> sourceList, Class<T> targetClass,
			String[] sourceProps, boolean isPropsSame, String... targetProps) {
		if (CollectionUtils.isEmpty(sourceList)) {
			return Collections.emptyList();
		}

		// 判断有没有日期需要进行处理
		boolean needHandleProps = (sourceProps != null && sourceProps.length > 0);
		List<T> targetList = new ArrayList<>();

		try {
			for (S each : sourceList) {
				T target = targetClass.newInstance();

				// 如果有多个，那么依次处理
				if (needHandleProps) {
					// 如果属性完全一致，targetProps不必再传，那么targetProps为空
					if (isPropsSame) {
						for (int i = 0; i < sourceProps.length; i++) {
							setCommonProperty(each, target, sourceProps[i], sourceProps[i]);
						}
					}

					else {
						for (int i = 0; i < sourceProps.length; i++) {
							setCommonProperty(each, target, sourceProps[i], targetProps[i]);
						}
					}
				}

				targetList.add(target);
			}
		}

		catch (Exception e) {
			log.error("将一个对象集合转化为另一个对象集合出现异常", e);
		}

		return targetList;
	}

	/**
	 * 泛型方法，将一个对象数组转化为另一个对象集合，只复制声明的属性
	 * 
	 * 注意:sourceProps和targetProps必须一一对应起来
	 * 
	 * 
	 * @param <S>
	 *            原始对象
	 * 
	 * @param <T>
	 *            目标对象
	 * 
	 * @param sourceArray
	 *            原始对象数组
	 * 
	 * @param targetClass
	 *            目标对象的类型
	 * 
	 * @param sourceProps
	 *            需要复制的源对象的属性名称的集合
	 * 
	 * @param isPropsSame
	 *            需要复制的源对象的属性名称的集合与目标对象的属性名称的集合是否完全一样。 如果完全一样，targetProps不必再传
	 * 
	 * @param targetProps
	 *            目标对象的属性名称的集合
	 * 
	 * @return
	 */
	public static <S, T> List<T> sourceArray2TargetListWithAnyProperties(S[] sourceArray, Class<T> targetClass,
			String[] sourceProps, boolean isPropsSame, String... targetProps) {
		if (sourceArray == null || sourceArray.length == 0) {
			return Collections.emptyList();
		}

		return sourceList2TargetListWithAnyProperties(Arrays.asList(sourceArray), targetClass, sourceProps, isPropsSame,
				targetProps);
	}

	/**
	 * 泛型方法，根据查询出来的数据库对象集合和总数直接生成最终的对象
	 * 
	 * @param <R>
	 *            最终的对象
	 * 
	 * @param <S>
	 *            原始对象
	 * 
	 * @param <T>
	 *            目标对象
	 * 
	 * @param resultClass
	 *            最终生成的xxxListVo对象，比如WechatUserListVo对象
	 * 
	 * @param total
	 *            查询出来的记录总数
	 * 
	 * @param sourceList
	 *            原始对象集合
	 * 
	 * @param targetClass
	 *            目标对象
	 * 
	 * @param dates
	 *            日期相关的属性
	 * 
	 * @param ignoreProperties
	 * @return
	 */

	public static <R, S, T> R generateFinalResult(Class<R> resultClass, Integer total, List<S> sourceList,
			Class<T> targetClass, String[] dates, String... ignoreProperties) {
		try {
			if (CollectionUtils.isEmpty(sourceList)) {
				return null;
			}

			List<T> targetList = sourceList2TargetListWithDate(sourceList, targetClass, dates, ignoreProperties);

			// 最终的结果
			R result = resultClass.newInstance();
			Method[] methods = resultClass.getDeclaredMethods();

			for (Method method : methods) {
				if (!method.getName().startsWith("set")) {
					continue;
				}

				if (method.getName().startsWith("setTotal")) {
					method.invoke(result, total);
				}

				else {
					method.invoke(result, targetList);
				}
			}

			return result;
		}

		catch (Exception e) {
			return null;
		}

	}

	/**
	 * 泛型方法，根据查询出来的数据库对象集合和总数直接生成最终的对象
	 * 
	 * @param <R>
	 *            最终的对象
	 * 
	 * @param <S>
	 *            原始对象
	 * 
	 * @param <T>
	 *            目标对象
	 * 
	 * @param resultClass
	 *            最终生成的xxxListVo对象，比如WechatUserListVo对象
	 * 
	 * @param total
	 *            查询出来的记录总数
	 * 
	 * @param sourceList
	 *            原始对象集合
	 * 
	 * @param targetClass
	 *            目标对象
	 * 
	 * @param sourceDates
	 *            原始对象需要处理的日期属性名称集合
	 * 
	 * @param targetDates
	 *            目标对象需要处理的日期属性名称集合
	 * 
	 * @param ignoreProperties
	 * @return
	 */

	public static <R, S, T> R generateFinalResultWithSpecialDate(Class<R> resultClass, Integer total,
			List<S> sourceList, Class<T> targetClass, String[] sourceDates, String[] targetDates,
			String[] dateFormatPatterns, String... ignoreProperties) {
		try {
			List<T> targetList = sourceList2TargetListWithSpecialDate(sourceList, targetClass, sourceDates, targetDates,
					dateFormatPatterns, ignoreProperties);

			// 最终的结果
			R result = resultClass.newInstance();
			Method[] methods = resultClass.getDeclaredMethods();

			for (Method method : methods) {
				if (!method.getName().startsWith("set")) {
					continue;
				}

				if (method.getName().startsWith("setTotal")) {
					method.invoke(result, total);
				}

				else {
					method.invoke(result, targetList);
				}
			}

			return result;
		}

		catch (Exception e) {
			return null;
		}

	}

	/**
	 * 泛型方法，根据对象集合和总数直接生成最终的对象
	 * 
	 * @param <R>
	 *            最终的对象
	 * 
	 * @param resultClass
	 *            最终生成的xxxListVo对象，比如WechatUserListVo对象
	 * 
	 * @param total
	 *            记录总数
	 * 
	 * @param sourceList
	 *            原始对象集合
	 * 
	 * @return
	 */

	public static <R> R generateResultByListAndTotalNum(Class<R> resultClass, Integer total, List<?> sourceList) {
		try {
			// 最终的结果
			R result = resultClass.newInstance();
			Method[] methods = resultClass.getDeclaredMethods();

			for (Object each : sourceList) {
				setProperetiy2Empty(each);
			}

			for (Method method : methods) {
				if (!method.getName().startsWith("set")) {
					continue;
				}

				if (method.getName().startsWith("setTotal")) {
					method.invoke(result, total);
				}

				else {
					method.invoke(result, sourceList);
				}
			}

			return result;
		}

		catch (Exception e) {
			return null;
		}

	}

	/**
	 * 泛型方法，将一个对象转化为另一个对象
	 * 
	 * @param <S>
	 *            原始对象
	 * 
	 * @param <T>
	 *            目标对象
	 * 
	 * @param targetClass
	 *            目标对象的类型
	 * 
	 * @param ignoreProperties
	 *            不进行复制的属性
	 * @return
	 */
	public static <S, T> T source2Target(S source, Class<T> targetClass, String... ignoreProperties) {
		if (source == null) {
			return null;
		}

		try {
			T target = targetClass.newInstance();

			BeanUtils.copyProperties(source, target, ignoreProperties);

			return target;
		}

		catch (Exception e) {
			log.error("将一个对象转化为另一个对象出现异常", e);

			return null;
		}

	}

	/**
	 * 判断当前属性，如果属性值为null，重置为空字符串
	 * 
	 * @param source
	 * @throws Exception
	 */
	public static void setProperetiy2Empty(Object source) {
		try {
			Field[] fields = source.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field currentField = fields[i];

				currentField.setAccessible(true);
				Object fieldVal = currentField.get(source);
				if (fieldVal == null) {
					String fieldClassName = currentField.getType().getSimpleName();
					if (Objects.equals(fieldClassName, "String")) {
						currentField.set(source, "");
					}
				}
			}

			Field[] parentFields = source.getClass().getSuperclass().getDeclaredFields();
			if (parentFields != null && parentFields.length > 0) {
				for (int i = 0; i < parentFields.length; i++) {
					Field currentField = parentFields[i];

					currentField.setAccessible(true);
					if (currentField.get(source) == null) {
						String fieldClassName = currentField.getType().getSimpleName();
						if (Objects.equals(fieldClassName, "String")) {
							currentField.set(source, "");
						}
					}
				}
			}
		} catch (Exception e) {
			log.info("设置属性值为空字符串出现异常：{}", e.getMessage());
		}
	}

	public static <S, T> T source2Target(S source, T target, String... ignoreProperties) {
		if (source == null) {
			return null;
		}

		try {
			BeanUtils.copyProperties(source, target, ignoreProperties);

			return target;
		} catch (Exception e) {
			log.error("将一个对象转化为另一个已有对象出现异常", e);

			return null;
		}
	}

	/**
	 * 泛型方法，将一个对象转化为另一个对象，包含对createdAt和updatedAt属性的处理
	 * 
	 * @param <S>
	 *            原始对象
	 * 
	 * @param <T>
	 *            目标对象
	 * 
	 * @param targetClass
	 *            目标对象的类型
	 * 
	 * @param dates
	 *            日期相关的属性
	 * 
	 * @param ignoreProperties
	 *            不进行复制的属性
	 * @return
	 */

	public static <S, T> T source2TargetWithDate(S source, Class<T> targetClass, String[] dates,
			String... ignoreProperties) {
		if (source == null) {
			return null;
		}

		List<String> dateList = (dates != null && dates.length > 0) ? Arrays.asList(dates) : Collections.emptyList();

		try {
			T target = targetClass.newInstance();
			BeanUtils.copyProperties(source, target, ignoreProperties);

			// 如果传过来updatedAt和createdAt等属性，那么就进行这些属性的设置
			if (!CollectionUtils.isEmpty(dateList)) {
				if (dateList.contains("updatedAt")) {
					setDateProperty(source, target, "updatedAt", "updatedAt", null);
				}

				if (dateList.contains("createdAt")) {
					setDateProperty(source, target, "createdAt", "createdAt", null);
				}
			}

			return target;

		}

		catch (Exception e) {
			log.error("对象转化出现异常", e);

			return null;
		}

	}

	/**
	 * 泛型方法，将一个对象转化为另一个对象，包含对指定日期属性的处理，并且日期的格式化形式完全由客户指定
	 * 
	 * @param <S>
	 *            原始对象
	 * 
	 * @param <T>
	 *            目标对象
	 * 
	 * @param targetClass
	 *            目标对象的类型
	 * 
	 * @param sourceDates
	 *            原始对象需要处理的日期属性名称集合
	 * 
	 * @param targetDates
	 *            目标对象需要处理的日期属性名称集合
	 * 
	 * 
	 * @param dateFormatPatterns
	 *            每一个特殊的日期需要进行的格式化形式，与dates一一对应，dateFormatPatterns的长度与dates必须相等
	 * 
	 * @param ignoreProperties
	 *            不进行复制的属性
	 * @return
	 */

	public static <S, T> T source2TargetWithSpecialDate(S source, Class<T> targetClass, String[] sourceDates,
			String[] targetDates, String[] dateFormatPatterns, String... ignoreProperties) {
		if (source == null) {
			return null;
		}

		try {
			T target = targetClass.newInstance();

			BeanUtils.copyProperties(source, target, ignoreProperties);

			if (sourceDates != null && sourceDates.length > 0) {
				for (int i = 0; i < sourceDates.length; i++) {
					setDateProperty(source, target, sourceDates[i], targetDates[i], dateFormatPatterns[i]);
				}
			}

			return target;
		}

		catch (Exception e) {
			log.error("将一个对象转化为另一个对象，包含对指定日期属性的处理出现异常", e);

			return null;
		}

	}

	/**
	 * 两个对象之间的属性复制，只复制声明的属性
	 * 
	 * 注意:sourceProps和targetProps必须一一对应起来
	 * 
	 * @param source
	 *            源对象
	 * 
	 * @param targetClass
	 *            目标对象
	 * 
	 * @param sourceProps
	 *            需要复制的源对象的属性名称的集合
	 * 
	 * @param isPropsSame
	 *            需要复制的源对象的属性名称的集合与目标对象的属性名称的集合是否完全一样。 如果完全一样，targetProps不必再传
	 * 
	 * @param targetProps
	 *            目标对象的属性名称的集合
	 */

	public static <S, T> T source2TargetWithAnyProperties(S source, Class<T> targetClass, String[] sourceProps,
			boolean isPropsSame, String... targetProps) {
		if (source == null) {
			return null;
		}

		try {
			T target = targetClass.newInstance();

			if (sourceProps != null && sourceProps.length > 0) {
				// 如果属性完全一致，targetProps不必再传，那么targetProps为空
				if (isPropsSame) {
					for (int i = 0; i < sourceProps.length; i++) {
						setCommonProperty(source, target, sourceProps[i], sourceProps[i]);
					}
				}

				else {
					for (int i = 0; i < sourceProps.length; i++) {
						setCommonProperty(source, target, sourceProps[i], targetProps[i]);
					}
				}

			}

			return target;
		}

		catch (Exception e) {
			log.error("两个对象之间的属性复制，只复制声明的属性出现异常", e);

			return null;
		}
	}

	/**
	 * 对象之间某个属性的转化，针对的情况是两个对象的属性名称必须相同，数据类型不相同的处理
	 * 
	 * @param source
	 * @param target
	 * @param sourcePropertyName
	 *            原来对象的属性名称，必须是Date类型
	 * 
	 * @param targetPropertyName
	 *            目标对象的属性名称，必须是String类型
	 * 
	 * @param formatPattern
	 *            日期格式化格式
	 * @throws Exception
	 */

	private static void setDateProperty(Object source, Object target, String sourcePropertyName,
			String targetPropertyName, String formatPattern) throws Exception {
		Field sourceField = source.getClass().getDeclaredField(sourcePropertyName);
		// 原始对象不具备指定的属性
		if (sourceField == null) {
			return;
		}

		sourceField.setAccessible(true);
		// 原始对象虽然具备指定的属性，但是属性值为空
		if (sourceField.get(source) == null) {
			return;
		}

		// 目标对象不具备指定的属性
		Field targetField = target.getClass().getDeclaredField(targetPropertyName);
		if (targetField == null) {
			return;
		}

		targetField.setAccessible(true);
		sourcePropertyName = String.valueOf(sourcePropertyName.charAt(0)).toUpperCase()
				+ sourcePropertyName.substring(1);
		String getMethod = "get" + sourcePropertyName;

		// 如果日期格式化为空，默认采用yyyy-MM-dd HH:mm:ss形式
		if (StringUtils.isEmpty(formatPattern)) {
			formatPattern = DateUtil.DATETIME_FORMAT_YY_MM_DD_HH_MM_SS;
		}
		// 设置属性值
		targetField.set(target,
				DateUtil.format((Date) (source.getClass().getMethod(getMethod).invoke(source)), formatPattern));
	}

	/**
	 * 对象之间某个属性的转化，两个对象的属性名称可以不相同，但是数据类型必须相同
	 * 
	 * @param source
	 * @param target
	 * @param propertyName
	 * 
	 * @throws Exception
	 */

	private static void setCommonProperty(Object source, Object target, String sourcePropertyName,
			String targetPropertyName) throws Exception {
		Field sourceField = source.getClass().getDeclaredField(sourcePropertyName);
		// 原始对象不具备指定的属性
		if (sourceField == null) {
			return;
		}

		sourceField.setAccessible(true);
		// 原始对象虽然具备指定的属性，但是属性值为空
		if (sourceField.get(source) == null) {
			return;
		}

		// 目标对象不具备指定的属性
		Field targetField = target.getClass().getDeclaredField(targetPropertyName);
		if (targetField == null) {
			return;
		}

		targetField.setAccessible(true);
		sourcePropertyName = String.valueOf(sourcePropertyName.charAt(0)).toUpperCase()
				+ sourcePropertyName.substring(1);
		String getMethod = "get" + sourcePropertyName;

		// 设置属性值
		targetField.set(target, source.getClass().getMethod(getMethod).invoke(source));
	}

	/**
	 * 泛型方法，根据源对象生成目标对象，并且设置默认的新建属性
	 * 
	 * @param <S>
	 *            原始对象
	 * 
	 * @param <T>
	 *            目标对象
	 * 
	 * @param targetClass
	 *            目标对象的类型
	 * 
	 * @param ignoreProperties
	 *            不进行复制的属性
	 * @return
	 */

	public static <S, T> T source2TargetAndSetDefaultCreateProperties(S source, Class<T> targetClass,
			String... ignoreProperties) {
		if (source == null) {
			return null;
		}

		T target = source2Target(source, targetClass, ignoreProperties);
		// 说明调用source2Target方法时出现了异常，导致返回null
		if (target == null) {
			return null;
		}

		DomainUtil.setCommonValueForCreate(target);
		return target;
	}

	/**
	 * 泛型方法，根据源对象生成目标对象，并且设置默认的修改属性
	 * 
	 * @param <S>
	 *            原始对象
	 * 
	 * @param <T>
	 *            目标对象
	 * 
	 * @param targetClass
	 *            目标对象的类型
	 * 
	 * @param ignoreProperties
	 *            不进行复制的属性
	 * @return
	 */

	public static <S, T> T source2TargetAndSetDefaultUpdateProperties(S source, Class<T> targetClass,
			String... ignoreProperties) {
		if (source == null) {
			return null;
		}

		T target = source2Target(source, targetClass, ignoreProperties);
		// 说明调用source2Target方法时出现了异常，导致返回null
		if (target == null) {
			return null;
		}

		DomainUtil.setCommonValueForUpdate(target);
		return target;
	}

}