package com.nhnacademy.bookpubbatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableBatchProcessing
@ConfigurationPropertiesScan
@SpringBootApplication
public class BookpubbatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookpubbatchApplication.class, args);
	}

}
