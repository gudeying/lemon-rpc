package cn.lemon.context;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.hutool.core.lang.ClassScaner;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.lemon.annotation.LemonService;

public class BeanContext {
	private static Map<String, Object> beanMap = new HashMap<String, Object>();
	private static final Logger LOGGER = LoggerFactory.getLogger(BeanContext.class);

	public static void init() {
		initBean();
		injectProxy();

	}

	private static void initBean() {
		try {

			Set<Class<?>> classes = ClassScaner.scanPackageByAnnotation(CommonConstants.BASE_SCAN_PACKAGE,
					LemonService.class);
			if (!classes.isEmpty()) {
				for (Class<?> cls : classes) {
					LemonService lemonService = cls.getAnnotation(LemonService.class);
					if (lemonService != null) {
						String beanName = StrUtil.isNotBlank(lemonService.value()) ? lemonService.value()
								: cls.getName();
						if (beanMap.containsKey(beanName)) {
							LOGGER.warn("�����ͬ��LemonService����ע��");
							continue;
						}
						beanMap.put(beanName, cls.newInstance());
					}
				}
			} else {
				LOGGER.warn("û���ҵ���Ҫ��ʼ����LemonService����������޷���������");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void injectProxy() {

	}
}
