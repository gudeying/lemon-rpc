package cn.lemon.server;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class Request implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Class<?> target;
	private Object[] params;
	//method无法序列化传输
//	private Method method;
//	private Class<?>[] paramTypes;
	private String methodName;
	private java.util.List<Class<?>> paramTypes;
	private int id;

	public Class<?> getTarget() {
		return target;
	}

	public Request setTarget(Class<?> target) {
		this.target = target;
		return this;
	}

	public Object[] getParams() {
		return params;
	}

	public Request setParams(Object[] params) {
		this.params = params;
		return this;
	}

	public int getId() {
		return id;
	}

	public Request setId(int id) {
		this.id = id;
		return this;
	}


	public String getMethodName() {
		return methodName;
	}

	public Request setMethodName(String methodName) {
		this.methodName = methodName;
		return this;
	}

	public Class<?>[] getParamTypes() {
		if (paramTypes == null) {
			return null;
		}
		Class<?>[] types = new Class<?>[paramTypes.size()];
		types = paramTypes.toArray(types);
		return types;
	}

	public void setParamTypes(Class<?>[] paramTypes) {
		
		List<Class<?>> types = Arrays.asList(paramTypes);
		this.paramTypes = types;
	}
}
