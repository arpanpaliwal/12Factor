package com.example.sc;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceclientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceclientApplication.class, args);
	}

	@RestController
	class GreetingRestController {
		@Autowired
		private DiscoveryClient discoveryClient;

		@RequestMapping("/service-instances/{applicationName}")
		public List<ServiceInstance> serviceInstancesByApplicationName(@PathVariable String applicationName) {
			return this.discoveryClient.getInstances(applicationName);
		}

		@RequestMapping(method = RequestMethod.GET, value = "/hi/{name}")
		Map<String, String> hi(@PathVariable String name,
				@RequestHeader(value = "X-CNJ-Name", required = false) Optional<String> cn) {
			String resolvedName = cn.orElse(name);
			return Collections.singletonMap("greeting", "Hello, " + resolvedName + "!");
		}

	}

}
