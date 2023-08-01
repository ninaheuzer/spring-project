package GroupeD.vehiclesFront;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.PutMapping;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class VehiclesFrontApplication {

	public static void main(String[] args) {
		SpringApplication.run(VehiclesFrontApplication.class, args);
	}

}
