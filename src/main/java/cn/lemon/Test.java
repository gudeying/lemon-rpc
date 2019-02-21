package cn.lemon;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.ClassScaner;
import cn.hutool.core.util.StrUtil;
import cn.lemon.annotation.LemonService;
import cn.lemon.context.CommonConstants;
import cn.lemon.proxy.RequestProxy;
import cn.lemon.server.RequestServer;
import cn.lemon.web.service.TestService;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

@Component
@Order(value = Integer.MAX_VALUE)
public class Test implements InitializingBean {

	@Autowired
	private ApplicationContext applicationContext;

	@Value("${lemon.rpc.package}")
	private String scanPackage;
	@Value("${lemon.rpc.url}")
	private String rpcServer;
	@Value("${lemon.rpc.port}")
	private int rpcPort;

	private RequestServer server;

	private static Logger LOGGER = LoggerFactory.getLogger(Test.class);

	private void initRpc() {
		Set<Class<?>> cSet = getRpcClsaaSet();
		if (!CollectionUtil.isEmpty(cSet)) {
			getProxy(cSet);
		} else {
			LOGGER.warn(scanPackage + "中没有找到LemonService，请检查配置的路径");
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initRpc();
		this.server = new RequestServer(rpcServer, rpcPort);
	}

	private String dataInit(Class<?> clazz) {
		String className = clazz.getSimpleName();// 获取类名
		String big = className.substring(0, 1);// 获取首字母（类名首字母大写）
		String small = big.toLowerCase();// 将首字母变为小写
		String smallName = small + className.substring(1);// 获得以小写字母开头的类名
		return smallName;
	}

	private Set<Class<?>> getRpcClsaaSet() {
		try {
			LOGGER.info("扫描配置中的LemonService：" + scanPackage);
			Set<Class<?>> classes = ClassScaner.scanPackageByAnnotation("cn.lemon.web.service",
					cn.lemon.annotation.LemonService.class);
			return classes;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void getProxy(Set<Class<?>> classSet) {
		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext
				.getAutowireCapableBeanFactory();
		for (Class<?> cls : classSet) {
			LOGGER.info("injecting bean:" + cls.getSimpleName());
			Enhancer enhancer = new Enhancer();
			enhancer.setSuperclass(cls);
			if (server==null) {
				server = new RequestServer(rpcServer, rpcPort);
			}
			enhancer.setCallback(new RequestProxy(server,cls));
			Object proxy = enhancer.create();
			beanFactory.registerSingleton(dataInit(cls), cls.cast(proxy));
		}
		LOGGER.info("inject LemonService completed！number：" + classSet.size());
	}
}
