package pedroLopes.tqs.covid19trackingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Covid19TrackingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(Covid19TrackingServiceApplication.class, args);
	}

}
