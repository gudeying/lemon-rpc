package cn.lemon;

import java.lang.reflect.InvocationTargetException;

import cn.lemon.web.service.TestService;
import net.sf.cglib.reflect.FastClass;

public class MainTest {
	public static void main(String[] args) {
		FastClass fastClass = FastClass.create(TestService.class);
		try {
			fastClass.newInstance();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
