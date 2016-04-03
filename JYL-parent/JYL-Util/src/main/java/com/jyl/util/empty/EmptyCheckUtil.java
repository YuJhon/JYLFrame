package com.jyl.util.empty;

import java.util.Collection;
import java.util.Map;

/**
 * <p>
 * 功能描述:</br> 为空检查工具类
 * </p>
 * 
 * @className EmptyCheckUtil
 * @author jiangyu
 * @date 2016年4月3日 下午3:27:16
 * @version v1.0
 */
public class EmptyCheckUtil {
	/**
	 * <p>
	 * 功能描述：功能：判断数组是不是空。（null或者length==0）
	 * </p>
	 * 
	 * @author jiangyu
	 * @date 2016年3月23日 下午2:27:13
	 * @param array   数组
	 * @return boolean 空返回true，否则返回false
	 * @since V1.0
	 */
	public static <T> boolean isEmpty(T[] array) {
		return (array == null || array.length == 0);
	}

	/**
	 * <p>
	 * 功能描述：集合是否为空。如果传入的值为null或者集合不包含元素都认为为空
	 * </p>
	 * 
	 * @author jiangyu
	 * @date 2016年3月23日 下午2:28:30
	 * @param collection 校验的目标集合对象
	 * @return boolean 为空返回true，否则返回false
	 * @since V1.0
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return (collection == null || collection.isEmpty());
	}

	/**
	 * <p>
	 * 功能描述：Map是否为空。如果传入的值为null或者集合不包含元素都认为为空
	 * </p>
	 * 
	 * @author jiangyu
	 * @date 2016年3月23日 下午2:29:13
	 * @param map  要校验的map对象
	 * @return boolean 为空返回true，否则返回false
	 * @version v1.0
	 * @since V1.0
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Map map) {
		return (map == null || map.isEmpty());
	}

	/**
	 * <p>
	 * 功能描述：判断个字符是否为空
	 * </p>
	 * 
	 * @author jiangyu
	 * @date 2016年3月23日 下午2:29:53
	 * @param source  要判断字符串
	 * @return 如果为空返回真，反之为假
	 * @since V1.0
	 */
	public static boolean isEmpty(String source) {
		return (source == null || source.trim().equals("") || "null".equals(source));
	}

	/**
	 * <p>
	 * 功能描述：判断个对象是否为空
	 * </p>
	 * 
	 * @author jiangyu
	 * @date 2016年3月23日 下午2:30:26
	 * @param source   要判断对
	 * @return 如果为空返回真，反之为假
	 * @since V1.0
	 */
	public static boolean isEmpty(Object source) {
		if (source instanceof Collection<?> && source != null) {
			Collection<?> collection = (Collection<?>) source;
			return (collection == null || collection.isEmpty());
		}
		return (source == null || source.toString().trim().equals("") || "null".equals(source));
	}
}
