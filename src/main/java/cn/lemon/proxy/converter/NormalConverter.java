package cn.lemon.proxy.converter;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

public class NormalConverter {
	public static Object convert(Class<?> toType, Object resource) {
		try {
			Object result = toType.newInstance();
			BeanUtils.copyProperties(result, resource);
			return result;
		} catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
			e.printStackTrace();
			return null;
		}
	}
}
