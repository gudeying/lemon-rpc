package cn.lemon.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.sun.org.apache.regexp.internal.recompile;

import cn.lemon.proxy.RequestProxy;

public class ProxyContext {
	private static final Map<Integer, RequestProxy> proxyHolder = new ConcurrentHashMap<Integer, RequestProxy>();

	public static int storeProxy(RequestProxy proxy) {
		int proxyId = proxy.hashCode();
		proxyHolder.put(proxyId, proxy);
		return proxyId;
	}

	public static RequestProxy load(Integer id) {
		RequestProxy requestProxy = proxyHolder.get(id);
		proxyHolder.remove(id);
		return requestProxy;
	}
	public static int size() {
		return proxyHolder.size();
	}
}
