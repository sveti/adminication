package f54148.adminication.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.stereotype.Component;

//a class to get the custom port for this microservice from the configuration server
@Component
public class CustomizationPort implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {
	
	//get the server port from the databaseService.yml
	@Value("${server.port: 8080}")
	public int serverPort;

	//set the port
	@Override
	public void customize(ConfigurableServletWebServerFactory server) {
		server.setPort(serverPort);
	}
}