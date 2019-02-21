package cn.lemon.proxy;

import java.lang.reflect.Method;
import java.util.List;

import cn.lemon.context.ProxyContext;
import cn.lemon.proxy.converter.NormalConverter;
import cn.lemon.server.Request;
import cn.lemon.server.RequestServer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class RequestProxy implements MethodInterceptor {
	private RequestServer server;
	private Object result = null;
	private boolean hasDone = false;
	private Class<?> targetClass;

	public RequestProxy(RequestServer server,Class<?> targetCls) {
		this.targetClass = targetCls;
		this.server = server;
	}

	private Object getResult() {
		synchronized (this) {
			try {
				while (!hasDone) {
					wait();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
			return this.result;
		}
	}

	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		
		
		Request request = new Request();
		int id = ProxyContext.storeProxy(this);
		request.setParams(args).setTarget(targetClass).setId(id).setMethodName(method.getName());
		
		Class<?>[] paramTypes = method.getParameterTypes();
		request.setParamTypes(paramTypes);
		
		server.request(request);
		
		Object result = getResult();
		System.out.println("返回的结果："+result);
		Class<?> toType = method.getReturnType();

		return result;
	}

	public void setDone(Object result) {
		synchronized (this) {
			this.result = result;
			hasDone = true;
			notifyAll();
		}
	}

	public Class<?> getTargetClass() {
		return targetClass;
	}

	public void setTargetClass(Class<?> targetClass) {
		this.targetClass = targetClass;
	}
}
