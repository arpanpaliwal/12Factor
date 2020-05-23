package com.example.sc;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

@SpringBootApplication
@EnableDiscoveryClient
public class Serviceclient2Application implements CommandLineRunner {

	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(Serviceclient2Application.class, args);
	}

	@Autowired
	private DiscoveryClient discoveryClient;

	@Autowired
	private RestTemplate loadBalancedrestTemplate;

	@Override
	public void run(String... args) throws Exception {
		/*
		 * String serviceId = "Greeting-Service";
		 * 
		 * List<Server> servers = this.discoveryClient.getInstances(serviceId).stream()
		 * .map(si -> new Server(si.getHost(),
		 * si.getPort())).collect(Collectors.toList());
		 * 
		 * IRule roundRobinRule = new RoundRobinRule();
		 * 
		 * BaseLoadBalancer loadBalancer =
		 * LoadBalancerBuilder.newBuilder().withRule(roundRobinRule)
		 * .buildFixedServerListLoadBalancer(servers); IntStream.range(0, 10).forEach(i
		 * -> {
		 * 
		 * Server server = loadBalancer.chooseServer(); URI uri = URI.create("http://" +
		 * server.getHost() + ":" + server.getPort() + "/");
		 * System.out.println("resolved service " + uri.toString()); });
		 */

		Map<String, String> variables = Collections.singletonMap("name", "Cloud Natives!");
		ResponseEntity<JsonNode> response = this.loadBalancedrestTemplate.getForEntity("//Greeting-Service/hi/{name}",
				JsonNode.class, variables);
		JsonNode body = response.getBody();
		String greeting = body.get("greeting").asText();
		System.out.println("greeting: " + greeting);
	}
}
