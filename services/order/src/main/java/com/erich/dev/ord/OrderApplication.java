package com.erich.dev.ord;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class OrderApplication{

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}

}
