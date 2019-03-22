package com.hquery.hrpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {"com.hquery.hrpc"}
)
public class HrpcApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrpcApplication.class, args);
	}
}