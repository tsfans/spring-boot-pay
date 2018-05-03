package cn.hl.pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import cn.hl.pay.util.unionpay.sdk.SDKConfig;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
	    SDKConfig.getConfig().loadPropertiesFromSrc();
		SpringApplication.run(Application.class, args);
	}
}
