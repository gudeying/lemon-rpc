package cn.lemon;

import java.lang.reflect.Method;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.ApplicationContextTestUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.sym.Name;

import cn.lemon.annotation.LemonService;
import cn.lemon.web.service.UserService;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GeeklemonRpcApplicationTests {
	@Autowired
	private final UserService userService = null;
	@Autowired
	private final ApplicationContext applicationContext = null;

	@Test
	public void contextLoads() {
//		System.out.println(applicationContext == null);


		System.out.println(userService.test());
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
