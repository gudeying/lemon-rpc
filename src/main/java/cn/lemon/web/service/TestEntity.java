package cn.lemon.web.service;

import java.io.Serializable;
import java.lang.reflect.Method;

public class TestEntity  implements Serializable{
	private String name;
	private Method method;
	private Class<?> cls;
	private Object[] objects;
}
