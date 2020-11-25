package f54148.adminication.component;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {

	@Value("${spring.datasource.url:  }")
	public String url;

	@Value("${spring.datasource.username:  }")
	public String username;

	@Value("${spring.datasource.password:  }")
	public String password;

	@Bean
	public DataSource getDataSource() {
		return DataSourceBuilder.create()
				.driverClassName("com.mysql.cj.jdbc.Driver")
				.username(username)
				.password(password)
				.url(url)
				.build();
	}
	
	
	
}
	
	