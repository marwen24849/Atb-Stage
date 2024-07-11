package fsb.ucar.Gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableMethodSecurity(prePostEnabled = true)
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	DiscoveryClientRouteDefinitionLocator locator(ReactiveDiscoveryClient client, DiscoveryLocatorProperties properties){
		return new DiscoveryClientRouteDefinitionLocator(client,properties);
	}
}
