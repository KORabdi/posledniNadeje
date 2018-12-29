package B6B32EAR.Forex;

import B6B32EAR.Forex.config.PersistenceConfig;
import B6B32EAR.Forex.config.RestConfig;
import B6B32EAR.Forex.config.ServiceConfig;
import B6B32EAR.Forex.config.WebMvcConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({PersistenceConfig.class, RestConfig.class, WebMvcConfig.class, ServiceConfig.class})
public class ForexApplication {
        static public BrokerPool brokerpool = new BrokerPool();
	public static void main(String[] args) {
//            ForexApplication.brokerpool.initialize();
		SpringApplication.run(ForexApplication.class, args);
	}
}
