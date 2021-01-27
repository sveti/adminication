package f54148.adminication.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomizationPort implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

	@Value("${server.port:}")
	public int serverPort;

	// set the port
	@Override
	public void customize(ConfigurableServletWebServerFactory server) {
		server.setPort(serverPort);
	}

}
