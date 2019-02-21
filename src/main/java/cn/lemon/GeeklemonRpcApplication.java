package cn.lemon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class GeeklemonRpcApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(GeeklemonRpcApplication.class, args);
		
	}

}
