package com.github.epoddubny.testtaskrbkmoney;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;

@SpringBootApplication(exclude = KafkaAutoConfiguration.class)
public class TestTaskRbkmoneyApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestTaskRbkmoneyApplication.class, args);
	}

}
