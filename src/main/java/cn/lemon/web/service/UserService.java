package cn.lemon.web.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.lemon.annotation.LemonAutowired;

@Service
public class UserService implements InitializingBean{

	@Resource
	private TestService testService;
	
	public String test() {
		return testService.test("请求参数");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println(testService.test("请求参数"));
	}

}
