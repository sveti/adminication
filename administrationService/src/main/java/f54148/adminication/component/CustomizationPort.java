package f54148.adminication.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CustomizationPort implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

	// get the server port from the administrationService.yml
	@Value("${server.port: }")
	public int serverPort;

	// set the port
	@Override
	public void customize(ConfigurableServletWebServerFactory server) {
		server.setPort(serverPort);
	}
}