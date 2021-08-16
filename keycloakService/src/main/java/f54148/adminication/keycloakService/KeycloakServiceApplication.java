package f54148.adminication.keycloakService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class KeycloakServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(KeycloakServiceApplication.class, args);
	}

}
